package info.itloser.androidportal.file_sql;

import java.io.Serializable;

/**
 * author：zhaoliangwang on 2019/7/4 10:17
 * email：tc7326@126.com
 */
public class MsgBean implements Serializable {

    public MsgBean(Integer id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    private Integer id;
    private String title;
    private String content;

    @Override
    public String toString() {
        return "MsgBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
