package info.itloser.androidportal.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

/**
 * author：zhaoliangwang on 2019/2/20 16:28
 * email：tc7326@126.com
 */
public class MiClockView extends View {
    private Canvas mCanvas;//全局画布

    private float mHourDegree;//时针角度
    private float mMinuteDegree;//分针角度
    private float mSecondDegree;//秒针角度


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
        setMeasuredDimension(measureDimension(widthMeasureSpec), measureDimension(heightMeasureSpec));
    }

    /*
     * 计算长宽的方法
     * */
    public int measureDimension(int measureSpec) {
        int defaultSize = 400;//当用户不设置宽高时的默认值。400dp
        int model = MeasureSpec.getMode(measureSpec);//拿到view的测量模式
        int size = MeasureSpec.getSize(measureSpec);//拿到view的测量数据
        switch (model) {
            case MeasureSpec.EXACTLY://固定值。则表示用户在xml中设置了固定值。返回用户设置的值即可。
                return size;
            case MeasureSpec.AT_MOST://尽可能大。表示用户在xml中设置为wrap_content。所以取最小值返回。
                return Math.min(size, defaultSize);//这里size是父容器给的剩余空间。若size大于defaultSize（表示有空余）则用defaultSize。若size小于default（表示：如果使用default则可能无法完全展示。）则使用size。
            case MeasureSpec.UNSPECIFIED://未指定。就默认值。
                return defaultSize;
            default:
                return defaultSize;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getCurrentTime();//获取当前系统时间


    }

    /*
     * 获取当前系统时间
     * */
    private void getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        float MILLISECOND = calendar.get(Calendar.MILLISECOND);
        float SECOND = calendar.get(Calendar.SECOND) + MILLISECOND / 1000;
        float MINUTE = calendar.get(Calendar.MINUTE) + SECOND / 60;
        float HOUR = calendar.get(Calendar.HOUR) + MINUTE / 60;//HOUR_OF_DAY是24小时的。
        //这里为什么要上级相加？因为在算指针度数的时候，各个指针是互不影响的。不相加就会出现数据误差。
        //举例说5:30你算度数的时候必须拿5.5小时来算。否则指针就无法正常工作。出现一次跳一小时的情况。
        mHourDegree = HOUR / 12 * 360;//这里就等于5.5占12比例再*360就是现在分钟所在的度数
        mMinuteDegree = MINUTE / 60 * 360;
        mSecondDegree = SECOND / 60 * 360;
    }





































}
