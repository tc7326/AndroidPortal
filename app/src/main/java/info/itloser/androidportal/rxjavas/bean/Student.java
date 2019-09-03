package info.itloser.androidportal.rxjavas.bean;

import java.util.List;

/**
 * author：zhaoliangwang on 2019/9/3 16:50
 * email：tc7326@126.com
 */
public class Student {
    private int id;
    private String name;
    private List<Course> courses;

    public Student(int id, String name, List<Course> courses) {
        this.id = id;
        this.name = name;
        this.courses = courses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
