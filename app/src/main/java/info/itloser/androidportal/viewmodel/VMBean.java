package info.itloser.androidportal.viewmodel;

/**
 * author：zhaoliangwang on 2019/9/17 14:14
 * email：tc7326@126.com
 */
public class VMBean {

    private String title;//文字
    private int color;//背景色
    private int width;//宽度

    public VMBean(String title, int color, int width) {
        this.title = title;
        this.color = color;
        this.width = width;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
