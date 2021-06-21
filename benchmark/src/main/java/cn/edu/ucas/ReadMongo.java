package cn.edu.ucas;

import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.ReadConfig;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class ReadMongo {
    public static void main(String[] args) throws AnalysisException {
        SparkSession session = SparkSession
                .builder()
                .appName("ReadMongo")
                .config("spark.mongodb.input.uri", "mongodb://mongodb/test")
                .getOrCreate();
        JavaSparkContext jsc = new JavaSparkContext(session.sparkContext());
        String path = "mongodb://admin:UCASbdms@mongodb";

        Map<String, String> readOptions = new HashMap<String, String>();
        readOptions.put("collection", "course");
        ReadConfig readConfig = ReadConfig.create(jsc).withOptions(readOptions);
        JavaMongoRDD<Document> secAllRdd = MongoSpark.load(jsc, readConfig);
        Dataset<Row> course = secAllRdd.toDS(Row.class);
        course.persist();

        readOptions.put("collection", "people_line_100w");
        Dataset<Row> data1 = MongoSpark.load(jsc, ReadConfig.create(jsc).withOptions(readOptions)).toDS(Row.class);
        Benchmark.apOperations(data1, course, session);

        readOptions.put("collection", "people_line_500w");
        Dataset<Row> data2 = MongoSpark.load(jsc, ReadConfig.create(jsc).withOptions(readOptions)).toDS(Row.class);
        Benchmark.apOperations(data2, course, session);

        readOptions.put("collection", "people_child_100w_5");
        Dataset<Row> data3 = MongoSpark.load(jsc, ReadConfig.create(jsc).withOptions(readOptions)).toDS(Row.class);
        Benchmark.apOperations(data3, course, session);

        readOptions.put("collection", "people_child_100w_10");
        Dataset<Row> data4 = MongoSpark.load(jsc, ReadConfig.create(jsc).withOptions(readOptions)).toDS(Row.class);
        Benchmark.apOperations(data4, course, session);

        course.unpersist();
    }
}
