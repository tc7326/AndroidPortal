package info.itloser.androidportal.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

import info.itloser.androidportal.R;

/**
 * author：zhaoliangwang on 2019/2/20 16:28
 * email：tc7326@126.com
 */
public class MiClockView extends View {

    /* 全局画布 */
    private Canvas mCanvas;
    /* 12、3、6、9小时文本画笔 */
    private Paint mTextPaint;
    /* 测量小时文本宽高的矩形 */
    private Rect mTextRect = new Rect();
    /* 小时圆圈画笔 */
    private Paint mCirclePaint;
    /* 小时圆圈线条宽度 */
    private float mCircleStrokeWidth = 4;
    /* 小时圆圈的外接矩形 */
    private RectF mCircleRectF = new RectF();

    /* 亮色，用于分针、秒针、渐变终止色 */
    private int mLightColor;
    /* 暗色，圆弧、刻度线、时针、渐变起始色 */
    private int mDarkColor;
    /* 背景色 */
    private int mBackgroundColor;
    /* 小时文本字体大小 */
    private float mTextSize;

    /* 时钟半径，不包括padding值 */
    private float mRadius;
    /* 刻度线长度 */
    private float mScaleLength;

    /* 时针角度 */
    private float mHourDegree;
    /* 分针角度 */
    private float mMinuteDegree;
    /* 秒针角度 */
    private float mSecondDegree;
    /* 时针画笔 */
    private Paint mHourHandPaint;
    /* 分针画笔 */
    private Paint mMinuteHandPaint;
    /* 秒针画笔 */
    private Paint mSecondHandPaint;
    /* 时针路径 */
    private Path mHourHandPath = new Path();
    /* 分针路径 */
    private Path mMinuteHandPath = new Path();
    /* 秒针路径 */
    private Path mSecondHandPath = new Path();

    /* 加一个默认的padding值，为了防止用camera旋转时钟时造成四周超出view大小 */
    private float mDefaultPadding;
    private float mPaddingLeft;
    private float mPaddingTop;
    private float mPaddingRight;
    private float mPaddingBottom;

    /* 梯度扫描渐变 */
    private SweepGradient mSweepGradient;
    /* 渐变矩阵，作用在SweepGradient */
    private Matrix mGradientMatrix;

    /* 指针的在x轴的位移 */
    private float mCanvasTranslateX;
    /* 指针的在y轴的位移 */
    private float mCanvasTranslateY;
    /* 指针的最大位移 */
    private float mMaxCanvasTranslate;

    /* 刻度圆弧画笔 */
    private Paint mScaleArcPaint;
    /* 刻度圆弧的外接矩形 */
    private RectF mScaleArcRectF = new RectF();
    /* 刻度线画笔 */
    private Paint mScaleLinePaint;

    private int centerX;
    private int centerY;

    /*
     * 带属性的构造
     * */
    public MiClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClockView, 0, 0);
        mBackgroundColor = ta.getColor(R.styleable.ClockView_clock_backgroundColor, Color.BLUE);
        mLightColor = ta.getColor(R.styleable.ClockView_clock_lightColor, Color.RED);
        mDarkColor = ta.getColor(R.styleable.ClockView_clock_darkColor, Color.GREEN);
        mTextSize = ta.getDimension(R.styleable.ClockView_clock_textSize, DensityUtils.sp2px(context, 14));
        ta.recycle();

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mDarkColor);
        //居中绘制文字
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(mTextSize);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(mCircleStrokeWidth);
        mCirclePaint.setColor(mDarkColor);

        mScaleLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScaleLinePaint.setStyle(Paint.Style.STROKE);
        mScaleLinePaint.setColor(mBackgroundColor);

        mScaleArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScaleArcPaint.setStyle(Paint.Style.STROKE);

        mGradientMatrix = new Matrix();

        mSecondHandPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSecondHandPaint.setStyle(Paint.Style.FILL);
        mSecondHandPaint.setColor(mLightColor);

        mHourHandPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHourHandPaint.setStyle(Paint.Style.FILL);
        mHourHandPaint.setColor(mDarkColor);

        mMinuteHandPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMinuteHandPaint.setStyle(Paint.Style.FILL);
        mMinuteHandPaint.setColor(mLightColor);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("dd", "onMeasure");
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i("dd", "onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = Math.min(w - getPaddingLeft() - getPaddingRight(),
                h - getPaddingTop() - getPaddingBottom()) / 2;
        mDefaultPadding = 0.12f * mRadius;
        mPaddingLeft = mDefaultPadding + w / 2 - mRadius + getPaddingLeft();
        mPaddingRight = mDefaultPadding + w / 2 - mRadius + getPaddingRight();
        mPaddingTop = mDefaultPadding + h / 2 - mRadius + getPaddingTop();
        mPaddingBottom = mDefaultPadding + h / 2 - mRadius + getPaddingBottom();

        mScaleLength = 0.12f * mRadius;//根据比例确定刻度线长度
        mScaleLinePaint.setStrokeWidth(0.012f * mRadius);

        mScaleArcPaint.setStrokeWidth(mScaleLength);

        //梯度扫描渐变，以(w/2,h/2)为中心点，两种起止颜色梯度渐变
        //float数组表示，[0,0.75)为起始颜色所占比例，[0.75,1}为起止颜色渐变所占比例
        mSweepGradient = new SweepGradient(w / 2, h / 2,
                new int[]{mDarkColor, mLightColor}, new float[]{0.75f, 1});
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("dd", "onDraw");
        super.onDraw(canvas);
        mCanvas = canvas;
        drawOutSideArc();//画最外圈的弧线和时刻0
        getCurrentTime();//获取当前系统时间1
        drawScaleLine();//画一圈梯度渲染的亮暗色渐变圆弧，重绘时不断旋转，上面盖一圈背景色的刻度线2
        drawHourHand();//时针
        drawMinuteNeedle();//分针
        drawSecondNeedle();//秒针
        invalidate();
    }

    /**
     * 绘制时针
     */
    private void drawHourHand() {
        mCanvas.save();
        mCanvas.translate(mCanvasTranslateX * 1.2f, mCanvasTranslateY * 1.2f);
        mCanvas.rotate(mHourDegree, getWidth() / 2, getHeight() / 2);
        mHourHandPath.reset();
        float offset = mPaddingTop + mTextRect.height() / 2;
        mHourHandPath.moveTo(getWidth() / 2 - 0.018f * mRadius, getHeight() / 2 - 0.03f * mRadius);
        mHourHandPath.lineTo(getWidth() / 2 - 0.009f * mRadius, offset + 0.48f * mRadius);
        mHourHandPath.quadTo(getWidth() / 2, offset + 0.46f * mRadius,
                getWidth() / 2 + 0.009f * mRadius, offset + 0.48f * mRadius);
        mHourHandPath.lineTo(getWidth() / 2 + 0.018f * mRadius, getHeight() / 2 - 0.03f * mRadius);
        mHourHandPath.close();
        mHourHandPaint.setStyle(Paint.Style.FILL);
        mCanvas.drawPath(mHourHandPath, mHourHandPaint);

        //圈
        mCircleRectF.set(getWidth() / 2 - 0.03f * mRadius, getHeight() / 2 - 0.03f * mRadius,
                getWidth() / 2 + 0.03f * mRadius, getHeight() / 2 + 0.03f * mRadius);
        mHourHandPaint.setStyle(Paint.Style.STROKE);
        mHourHandPaint.setStrokeWidth(0.04f * mRadius);
        mCanvas.drawArc(mCircleRectF, 0, 360, false, mHourHandPaint);
        mCanvas.restore();

    }

    /*
     * 分针
     * */
    private void drawMinuteNeedle() {
        mCanvas.save();
        mCanvas.translate(mCanvasTranslateX * 2f, mCanvasTranslateY * 2f);
        mCanvas.rotate(mMinuteDegree, getWidth() / 2, getHeight() / 2);
        mMinuteHandPath.reset();

        float offset = mPaddingTop + mTextRect.height() / 2;
        mMinuteHandPath.moveTo(getWidth() / 2 - 0.01f * mRadius, getHeight() / 2 - 0.03f * mRadius);
        mMinuteHandPath.lineTo(getWidth() / 2 - 0.008f * mRadius, offset + 0.365f * mRadius);
        mMinuteHandPath.quadTo(getWidth() / 2, offset + 0.345f * mRadius,
                getWidth() / 2 + 0.008f * mRadius, offset + 0.365f * mRadius);
        mMinuteHandPath.lineTo(getWidth() / 2 + 0.01f * mRadius, getHeight() / 2 - 0.03f * mRadius);
        mMinuteHandPath.close();
        mMinuteHandPaint.setStyle(Paint.Style.FILL);
        mCanvas.drawPath(mMinuteHandPath, mMinuteHandPaint);

        //针尾的圈
        mCircleRectF.set(getWidth() / 2 - 0.03f * mRadius, getHeight() / 2 - 0.03f * mRadius,
                getWidth() / 2 + 0.03f * mRadius, getHeight() / 2 + 0.03f * mRadius);
        mMinuteHandPaint.setStyle(Paint.Style.STROKE);
        mMinuteHandPaint.setStrokeWidth(0.02f * mRadius);
        mCanvas.drawArc(mCircleRectF, 0, 360, false, mMinuteHandPaint);
        mCanvas.restore();

    }

    /**
     * 秒针
     */
    private void drawSecondNeedle() {
        mCanvas.save();
        mCanvas.rotate(mSecondDegree, getWidth() / 2, getHeight() / 2);
        mSecondHandPath.reset();
        float offset = mPaddingTop + mTextRect.height() / 2;

        mSecondHandPath.moveTo(getWidth() / 2, offset + 0.26f * mRadius);
        mSecondHandPath.lineTo(getWidth() / 2 - 0.05f * mRadius, offset + 0.34f * mRadius);
        mSecondHandPath.lineTo(getWidth() / 2 + 0.05f * mRadius, offset + 0.34f * mRadius);
        mSecondHandPath.close();
        mSecondHandPaint.setColor(mLightColor);
        mCanvas.drawPath(mSecondHandPath, mSecondHandPaint);
        mCanvas.restore();
    }

    /*
     * 画一圈梯度渲染的亮暗色渐变圆弧，重绘时不断旋转，上面盖一圈背景色的刻度线
     * */
    private void drawScaleLine() {
        Log.i("dd", "function 2");
        mCanvas.save();
        mCanvas.translate(mCanvasTranslateX, mCanvasTranslateY);
        mScaleArcRectF.set(
                mPaddingLeft + 1.5f * mScaleLength + mTextRect.height() / 2,
                mPaddingTop + 1.5f * mScaleLength + mTextRect.height() / 2,
                getWidth() - mPaddingRight - mTextRect.height() / 2 - 1.5f * mScaleLength,
                getHeight() - mPaddingBottom - mTextRect.height() / 2 - 1.5f * mScaleLength);
        mGradientMatrix.setRotate(mSecondDegree - 90, getWidth() / 2, getHeight() / 2);//matrix默认会在三点钟方向开始颜色的渐变，为了吻合钟表十二点钟顺时针旋转的方向，把秒针旋转的角度减去90度
        mSweepGradient.setLocalMatrix(mGradientMatrix);
        mScaleArcPaint.setShader(mSweepGradient);

        mCanvas.drawArc(mScaleArcRectF, 0, 360, false, mScaleArcPaint);//draw渐变圈
        for (int i = 0; i < 200; i++) {//画背景色刻度线（不懂为啥这里是200？）
            mCanvas.drawLine(getWidth() / 2, mPaddingTop + mScaleLength + mTextRect.height() / 2,
                    getWidth() / 2, mPaddingTop + 2 * mScaleLength + mTextRect.height() / 2, mScaleLinePaint);
            mCanvas.rotate(1.8f, getWidth() / 2, getHeight() / 2);//旋转画布
        }
        mCanvas.restore();
    }

    /*
     * 获取当前系统时间
     * */
    private void getCurrentTime() {
        Log.i("dd", "function 1");
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

    /*
     * 画最外圈的弧线
     * */
    private void drawOutSideArc() {
        Log.i("dd", "function 0");
        String[] timeList = new String[]{"12", "3", "6", "9"};//从12开始绘制。也就是0点。
        //计算数字的高度。
        mTextPaint.getTextBounds(timeList[1], 0, timeList[1].length(), mTextRect);
        //定位最外圈弧线的圆所在的矩形
        mCircleRectF.set(
                mPaddingLeft + mTextRect.width() / 2 + mCircleStrokeWidth / 2,
                mPaddingTop + mTextRect.height() / 2 + mCircleStrokeWidth / 2,
                getWidth() - mPaddingRight - mTextRect.width() / 2 - mCircleStrokeWidth / 2,
                getHeight() - mPaddingBottom - mTextRect.height() / 2 - mCircleStrokeWidth / 2);

        mCanvas.drawText(timeList[0], getWidth() / 2, mCircleRectF.top + mTextRect.height() / 2, mTextPaint);//文字定位
        mCanvas.drawText(timeList[1], mCircleRectF.right, getHeight() / 2 + mTextRect.height() / 2, mTextPaint);
        mCanvas.drawText(timeList[2], getWidth() / 2, mCircleRectF.bottom + mTextRect.height() / 2, mTextPaint);
        mCanvas.drawText(timeList[3], mCircleRectF.left, getHeight() / 2 + mTextRect.height() / 2, mTextPaint);

        for (int i = 0; i < 4; i++) {//画连接数字的4段弧线
            mCanvas.drawArc(mCircleRectF, 5 + 90 * i, 80, false, mCirclePaint);
        }
    }

}
