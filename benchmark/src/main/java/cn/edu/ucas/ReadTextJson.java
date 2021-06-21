package cn.edu.ucas;

import org.apache.spark.sql.*;

import java.util.Date;

public class ReadTextJson {
    public static void main(String[] args) throws AnalysisException {
        SparkSession session = SparkSession
                .builder()
                .appName("ReadTextJson")
                .getOrCreate();

        Dataset<Row> course = session.read().option("multiline", "false").json("/tmp/spark/course.json");
        course.persist();

        Dataset<Row> data1 = session.read().option("multiline", "false").json("/tmp/spark/line_100w.json");
        Benchmark.apOperations(data1, course, session);

        Dataset<Row> data2 = session.read().option("multiline", "false").json("/tmp/spark/line_500w.json");
        Benchmark.apOperations(data2, course, session);

        Dataset<Row> data3 = session.read().option("multiline", "false").json("/tmp/spark/child_100w_5.json");
        Benchmark.apOperations(data3, course, session);

        Dataset<Row> data4 = session.read().option("multiline", "false").json("/tmp/spark/child_100w_10.json");
        Benchmark.apOperations(data4, course, session);

        course.unpersist();
    }
}
