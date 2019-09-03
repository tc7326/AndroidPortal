package info.itloser.androidportal.statusbar;

/**
 * author：zhaoliangwang on 2019/8/23 16:42
 * email：tc7326@126.com
 */
public class FakeDataBean {
    private String title;

    public FakeDataBean(String title, String content) {
        this.title = title;
        this.content = content;
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

    private String content;
}
