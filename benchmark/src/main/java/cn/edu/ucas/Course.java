package cn.edu.ucas;

import org.apache.spark.sql.Row;

public class Course {
    private int id;
    private int score;
    private String name;
    private int studentid;

    public Course() {

    }

    public Course(int id, int score, String name, int studentid) {
        this.id = id;
        this.score = score;
        this.name = name;
        this.studentid = studentid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudentid() {
        return studentid;
    }

    public void setStudentid(int studentid) {
        this.studentid = studentid;
    }

}
