package cn.edu.ucas;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.*;

import javax.xml.crypto.Data;
import java.util.Date;

public class ReadParquet {
    public static void main(String[] args) throws AnalysisException, InterruptedException {
        SparkSession session = SparkSession
                .builder()
                .appName("ReadParquet")
                .getOrCreate();
        String path = "hdfs://192.168.0.72:9000/data/";

        Dataset<Row> course = session.read().option("multiline", "false").parquet(path + "course.parquet");
        course = course.persist();
        System.out.println(course.count());

        Dataset<Row> data1 = session.read().option("multiline", "false").parquet(path + "line_100w.parquet");
        Benchmark.apOperations(data1, course, session);

        Dataset<Row> data2 = session.read().option("multiline", "false").parquet(path + "line_500w.parquet");
        Benchmark.apOperations(data2, course, session);

        Dataset<Row> data3 = session.read().option("multiline", "false").parquet(path + "child_100w_5.parquet");
        Benchmark.apOperations(data3, course, session);

        Dataset<Row> data4 = session.read().option("multiline", "false").parquet(path + "child_500w_5.parquet");
        Benchmark.apOperations(data4, course, session);

        course.unpersist();
        System.out.println("Finish all jobs!!!");
        System.out.println("*****************************************************************************************************************");
    }
}
