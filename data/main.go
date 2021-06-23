package main

import (
	"bufio"
	"context"
	"encoding/json"
	"fmt"
	"math"
	"os"
	"runtime"
	"strconv"

	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

var singleRoutineAmount = 10000

const MAX_DEPTH = 5
const TOTAL_BIG = 500
const DATA_TYPE = "child"

func Generate(start int, c chan *People, dataType string) {
	if dataType == "line" {
		for i := 0; i < singleRoutineAmount; i++ {
			p := NewPeople(start + i)
			c <- p
		}
	} else {
		for i := 0; i < singleRoutineAmount/MAX_DEPTH; i++ {
			var father = NewPeople(start + i*MAX_DEPTH)
			var child = father
			for j := 1; j < MAX_DEPTH; j++ {
				child = AddChild(child, start+j+i*MAX_DEPTH)
			}
			c <- father
		}
	}
}

func main() {

	runtimeAmount := runtime.GOMAXPROCS(runtime.NumCPU())
	c := make(chan *People, 1000)
	var total = TOTAL_BIG * 10000

	//c2 := make(chan int)
	//go GenerateCourse(c2)

	var list []interface{}
	singleRoutineAmount = int(math.Round(float64(total) / float64(runtimeAmount)))
	for i := 0; i < runtimeAmount; i++ {
		go Generate((i+1)*singleRoutineAmount, c, DATA_TYPE)
	}

	if DATA_TYPE == "line" {
		for p := range c {
			list = append(list, *p)
			if len(list) >= total {
				close(c)
				break
			}
		}
	} else if DATA_TYPE == "child" {
		for p := range c {
			list = append(list, p)
			if len(list) == int(math.Round(float64(total)/MAX_DEPTH)) {
				close(c)
				break
			}
		}
	}

	fmt.Println("Generate finished: " + strconv.Itoa(len(list)))

	mongoURI := fmt.Sprintf("mongodb://%s:%s@%s", "admin", "UCASbdms", "121.36.63.143:30002")
	fmt.Println("connection string is:", mongoURI)

	// Set client options and connect
	clientOptions := options.Client().ApplyURI(mongoURI)
	ctx := context.TODO()
	client, _ := mongo.Connect(ctx, clientOptions)

	var collectionName string = fmt.Sprintf("people_%s_%dw", DATA_TYPE, TOTAL_BIG)
	if DATA_TYPE == "child" {
		collectionName = fmt.Sprintf("people_%s_%dw_%d", DATA_TYPE, TOTAL_BIG, MAX_DEPTH)
	}
	client.Database("test").Collection(collectionName).Drop(ctx)
	client.Database("test").Collection(collectionName).InsertMany(ctx, list)

	var result []byte
	for _, p := range list {
		str, _ := json.Marshal(p)
		result = append(result, str...)
		result = append(result, byte('\n'))
	}

	var fileName string = fmt.Sprintf("dataset/%s_%dw.json", DATA_TYPE, TOTAL_BIG)
	if DATA_TYPE == "child" {
		fileName = fmt.Sprintf("dataset/%s_%dk_%d.json", DATA_TYPE, TOTAL_BIG, MAX_DEPTH)

	}
	resultFile, _ := os.OpenFile(fileName, os.O_RDWR|os.O_CREATE|os.O_TRUNC, 0644)
	defer resultFile.Close()
	w := bufio.NewWriter(resultFile)
	w.Write(result)

	//for _ = range c2 {
	//
	//}
}
