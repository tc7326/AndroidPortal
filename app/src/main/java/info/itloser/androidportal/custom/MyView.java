package info.itloser.androidportal.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * author：zhaoliangwang on 2019/2/19 15:09
 * email：tc7326@126.com
 */
public class MyView extends View {


    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("dd", "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(100, widthMeasureSpec);
        Log.i("dd", "==============================");

        int height = getMySize(100, heightMeasureSpec);


        Log.i("dd", "宽" + width + "高：" + height);

        if (width < height) {
            height = width;
        } else {
            width = height;
        }
        Log.i("dd", "宽" + width + "高：" + height);
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: //如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                Log.i("dd", "UNSPECIFIED");
                break;
            case MeasureSpec.AT_MOST: //如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                Log.i("dd", "AT_MOST");
                break;
            case MeasureSpec.EXACTLY: //如果是固定的大小，那就不要去改变它
                mySize = size;
                Log.i("dd", "EXACTLY");
                break;
        }
        return mySize;
    }


}
