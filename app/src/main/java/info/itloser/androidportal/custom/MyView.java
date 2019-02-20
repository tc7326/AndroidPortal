package info.itloser.androidportal.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import info.itloser.androidportal.R;

/**
 * author：zhaoliangwang on 2019/2/19 15:09
 * email：tc7326@126.com
 */
public class MyView extends View {


    /*
    * 声明：整套逻辑存在严重明显错误，仅做入门了解使用。
    * */

    Paint paint = new Paint();//理解成画图里的油漆桶。最好声明在全局。

    public void setColor(int color) {
        this.color = color;
    }

    int color;//颜色属性


    public MyView(Context context) {
        super(context);
    }

    /*
     * 在此构造里设置属性
     * */
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //第二个参数就是我们在styles.xml文件中的<declare-styleable>标签
        //即属性集合的标签，在R文件中名称为R.styleable+name
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyView);

        //第一个参数为属性集合里面的属性，R文件名称：R.styleable+属性集合名称+下划线+属性名称
        //第二个参数为，如果没有设置这个属性，则设置的默认的值
        color = a.getColor(R.styleable.MyView_color, Color.GREEN);

        //最后记得将TypedArray对象回收
        a.recycle();
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
        Log.i("dd", "onDraw");
        //调用父View的onDraw函数，因为View这个类帮我们实现了一些
        // 基本的而绘制功能，比如绘制背景颜色、背景图片等
        super.onDraw(canvas);
        //获取测量完的宽度
        int r = getMeasuredWidth() / 2;//也可以是getMeasuredHeight()/2,本例中我们已经将宽高设置相等了
        //圆心的横坐标为当前的View的左边起始位置+半径
//        int centerX = getLeft() + r;//错误修改。此时的圆心是相对画布canvas的位置。不需要取距父view的距离。
        int centerX = r;

        //圆心的纵坐标为当前的View的顶部起始位置+半径
//        int centerY = getTop() + r;//错误修改
        int centerY = r;

        //设置油漆桶的颜色（默认为绿色。如果有属性就设置属性值，没属性就默认绿色）
        paint.setColor(color);

        //开始绘制圆。
        canvas.drawCircle(centerX, centerY, r, paint);

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
                mySize = mySize;
                Log.i("dd", "EXACTLY");
                break;
        }
        return mySize;
    }


}
