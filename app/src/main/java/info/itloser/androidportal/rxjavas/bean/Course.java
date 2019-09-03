package info.itloser.androidportal.rxjavas.bean;

/**
 * author：zhaoliangwang on 2019/9/3 16:51
 * email：tc7326@126.com
 */
public class Course {
    private int id;
    private String name;

    public Course(int id, String name) {
        this.id = id;
        this.name = name;
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
}
