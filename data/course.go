package main

import (
	"context"
	"encoding/json"
	"fmt"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"math/rand"
	"time"
)

var courseNames = []string{
	"高等数学", "线性代数", "概率论", "离散数学", "数据结构", "计算机组成原理", "计算机网络", "面向对象程序设计",
	"数据库系统", "操作系统", "软件工程", "需求分析", "大数据系统与大数据分析",
}

var courseNameLength = len(courseNames)

type Course struct {
	Id        int    `json:"id"`
	Name      string `json:"name"`
	Score     int    `json:"score"`
	StudentId int    `json:"student_id" bson:"student_id"`
}

func GenerateCourse(c2 chan int) {
	mongoURI := fmt.Sprintf("mongodb://%s:%s@%s", "admin", "UCASbdms", "121.36.63.143:30002")
	fmt.Println("connection string is:", mongoURI)
	// Set client options and connect
	clientOptions := options.Client().ApplyURI(mongoURI)
	ctx := context.TODO()
	client, _ := mongo.Connect(ctx, clientOptions)

	client.Database("test").Collection("course").Drop(ctx)

	var list []interface{}
	var result []byte
	for i := 0; i < 1000000; i++ {
		rand.Seed(time.Now().UnixNano())
		course := &Course{Id: i, Name: courseNames[rand.Intn(courseNameLength)], Score: rand.Intn(100), StudentId: 1000000 - i}
		str, _ := json.Marshal(course)
		result = append(result, str...)
		result = append(result, byte('\n'))
		list = append(list, course)
	}

	client.Database("test").Collection("course").InsertMany(ctx, list)
	//resultFile, _ := os.OpenFile("dataset/course.json", os.O_RDWR|os.O_CREATE|os.O_TRUNC, 0644)
	//defer resultFile.Close()
	//w := bufio.NewWriter(resultFile)
	//w.Write(result)
	close(c2)
}
