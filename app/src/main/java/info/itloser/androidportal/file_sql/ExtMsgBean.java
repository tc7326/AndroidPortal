package info.itloser.androidportal.file_sql;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * author：zhaoliangwang on 2019/7/4 10:17
 * email：tc7326@126.com
 */
public class ExtMsgBean implements Externalizable {
    //实现反序列化接口，和MsgBean序列化接口对比


    public ExtMsgBean() {
    }

    public ExtMsgBean(Integer id, String title, String content) {
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

    /*
     * 这两个抽象方法必须实现，否则反序列化后拿到的值是没有的。(就是说可以自己设定怎么写入怎么读取)
     * */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(id);
        out.writeObject(title);
        out.writeObject(content);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = in.readInt();
        title = (String) in.readObject();
        content = (String) in.readObject();
    }
}
