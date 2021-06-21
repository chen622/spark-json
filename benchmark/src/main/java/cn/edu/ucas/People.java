package cn.edu.ucas;

public class People {
    private int id;
    private People people;
    private String gender;
    private String name;
    private int age;

    public People(){

    }

    public People(int id, People people, String gender, String name, int age) {
        this.id = id;
        this.people = people;
        this.gender = gender;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
