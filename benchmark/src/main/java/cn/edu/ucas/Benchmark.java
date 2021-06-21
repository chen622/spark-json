package cn.edu.ucas;

import org.apache.spark.sql.*;

import java.util.Date;

public class Benchmark {
    static void apOperations(Dataset<Row> peopleData, Dataset<Row> courseData, SparkSession session) throws AnalysisException {
        double start, end;

        start = Double.parseDouble(String.valueOf(new Date().getTime()));
        peopleData.persist();
        System.out.println("Data set length: " + peopleData.count());
        peopleData.createOrReplaceTempView("test");
        end = Double.parseDouble(String.valueOf(new Date().getTime()));
        System.out.println("It takes " + (end - start) + " ms to read data into memory.");
        System.out.println("*****************************************************************************************************************");

        //selection1 test
        System.out.println("This is Selection test. ");
        start = Double.parseDouble(String.valueOf(new Date().getTime()));
        Dataset<Row> selectionRes = session.sql("select * from test where gender='male' and age>18 and age<60");
        System.out.println("Select res length: " + selectionRes.count());
        end = Double.parseDouble(String.valueOf(new Date().getTime()));
        System.out.println("Selection operation takes " + (end - start) + " ms. ");
        System.out.println("*****************************************************************************************************************");
        selectionRes.unpersist();

        //projection test1
        System.out.println("This is Projection test.");
        start = Double.parseDouble(String.valueOf(new Date().getTime()));
        Dataset<Row> projectionRes = session.sql("select id,name,friend.name from test");
        System.out.println("Project res length: " + projectionRes.count());
        end = Double.parseDouble(String.valueOf(new Date().getTime()));
        System.out.println("Projection operation takes " + (end - start) + " ms. ");
        System.out.println("*****************************************************************************************************************");
        projectionRes.unpersist();

        //aggregation test
        System.out.println("This is Aggregation test.");
        start = Double.parseDouble(String.valueOf(new Date().getTime()));
        Dataset<Row> aggRes = peopleData.select("id", "age", "gender").groupBy("gender").agg(functions.count("gender").as("count_gender"), functions.avg("age").as("avg_age"));
        System.out.println("Aggregation res length: " + aggRes.count());
        end = Double.parseDouble(String.valueOf(new Date().getTime()));
        System.out.println("Aggregation operation takes " + (end - start) + " ms. ");
        aggRes.unpersist();


        //join test
        System.out.println("This is Join test.");
        start = Double.parseDouble(String.valueOf(new Date().getTime()));
        Dataset<Row> joinRes = peopleData.join(courseData,peopleData.col("id").equalTo(courseData.col("student_id")), "left_outer");
        joinRes.show();
        System.out.println("Join res length: " + joinRes.count());
        end = Double.parseDouble(String.valueOf(new Date().getTime()));
        System.out.println("Join operation takes " + (end - start) + " ms. ");
        joinRes.unpersist();

        peopleData.unpersist();
    }
}
