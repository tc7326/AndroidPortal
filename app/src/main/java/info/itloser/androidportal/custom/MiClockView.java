package info.itloser.androidportal.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * author：zhaoliangwang on 2019/2/20 16:28
 * email：tc7326@126.com
 */
public class MiClockView extends View {

    /*
     * 带属性的构造
     * */
    public MiClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("owen", "onMeasure");
        //设置View的长宽
        setMeasuredDimension(1, 1);
    }

    /*
    * 计算长宽的方法
    * */
    public void measureDimension(){

    }



}
