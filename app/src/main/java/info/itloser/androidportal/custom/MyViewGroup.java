package info.itloser.androidportal.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * author：zhaoliangwang on 2019/2/20 14:04
 * email：tc7326@126.com
 */
public class MyViewGroup extends ViewGroup {

    /*
     * 声明：整套逻辑存在严重明显错误，仅做入门了解使用。
     * */


    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("dd", "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //将所有的子View进行测量，这会触发每个子View的onMeasure函数
        //注意要与measureChild区分，measureChild是对单个view进行测量
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();
        Log.i("dd", childCount + "个子View");

        if (childCount == 0) {//如果没有子View,当前ViewGroup没有存在的意义，不用占用空间
            setMeasuredDimension(0, 0);
        } else {
            //如果宽高都是包裹内容
            if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
                //我们将高度设置为所有子View的高度相加，宽度设为子View中最大的宽度
                int height = getTotleHeight();
                int width = getMaxChildWidth();
                setMeasuredDimension(width, height);
                Log.i("dd", "AT_MOST");
            } else if (heightMode == MeasureSpec.AT_MOST) {//如果只有高度是包裹内容
                //宽度设置为ViewGroup自己的测量宽度，高度设置为所有子View的高度总和
                setMeasuredDimension(widthSize, getTotleHeight());
            } else if (widthMode == MeasureSpec.AT_MOST) {//如果只有宽度是包裹内容
                //宽度设置为子View中宽度最大的值，高度设置为ViewGroup自己的测量值
                setMeasuredDimension(getMaxChildWidth(), heightSize);

            }
        }


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
// onLayout方法中子View调用的方法layout(t,l,r,b)四个参数的值是以ViewGroup的左上角的点为坐标原点(y轴向下),而不应该用onLayout方法参数里的值

// ViewGroup的onLayout四个参数是其父View传过来的，表示当前View相对于父View的相对位置，
// 该控件周围可能还有其他控件或者边距什么的。
// 因此，子View的layout的参数应该是子View相对于当前ViewGroup的位置，最起码要处理当前View的边距
        Log.i("dd", "onLayout");
        int count = getChildCount();
        int curHeight = t;
        //将子View逐个摆放
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int height = child.getMeasuredHeight();
            int width = child.getMeasuredWidth();
            //摆放子View，参数分别是子View矩形区域的左、上、右、下边（d纠错。见当onLayout方法前几行注释。）未改正。
            child.layout(l, curHeight, l + width, curHeight + height);
            curHeight += height;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("dd", "onDraw");
        super.onDraw(canvas);
    }

    /***
     * 获取子View中宽度最大的值
     */
    private int getMaxChildWidth() {
        int childCount = getChildCount();
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getMeasuredWidth() > maxWidth)
                maxWidth = childView.getMeasuredWidth();

        }

        return maxWidth;
    }

    /***
     * 将所有子View的高度相加
     **/
    private int getTotleHeight() {
        int childCount = getChildCount();
        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            height += childView.getMeasuredHeight();

        }

        return height;
    }


}
