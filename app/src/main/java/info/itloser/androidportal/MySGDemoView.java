package info.itloser.androidportal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * author：zhaoliangwang on 2019/4/12 09:57
 * email：tc7326@126.com
 */
public class MySGDemoView extends View {


    GestureDetector gestureDetector;
    ScaleGestureDetector scaleGestureDetector;

    Matrix canvasMatrix = new Matrix();

    Matrix invertMatrix = new Matrix();

    Matrix userMatrix = new Matrix();

    Bitmap bitmap;

    float baseScale;
    float baseTranslateX;
    float baseTranslateY;

    Paint paint;

    public MySGDemoView(Context context) {
        super(context);
    }

    public MySGDemoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_test_one);
        initGesture(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (bitmap.getWidth() * 1.0f / bitmap.getHeight() > w * 1.0f / h) {
            baseScale = w * 1.0f / bitmap.getWidth();
            baseTranslateX = 0;
            baseTranslateY = (h - bitmap.getHeight() * baseScale) / 2;
        } else {
            baseScale = h * 1.0f / bitmap.getHeight() * 1.0f;
            baseTranslateX = (w - bitmap.getWidth() * baseScale) / 2;
            baseTranslateY = 0;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
        canvas.translate(baseTranslateX, baseTranslateY);
        canvas.scale(baseScale, baseScale);
        canvas.save();
        canvas.concat(userMatrix);
        canvasMatrix = canvas.getMatrix();

        canvasMatrix.invert(invertMatrix);

        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.restore();
    }

    private void initGesture(Context context) {

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                //滚动时
                float scale = getMatrixValue(MSCALE_X, canvasMatrix);
                userMatrix.preTranslate(-distanceX / scale, -distanceY / scale);
                invalidate();
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (!userMatrix.isIdentity()) {
                    userMatrix.reset();
                } else {
                    float[] points = mapPoint(e.getX(), e.getY(), invertMatrix);
                    userMatrix.postScale(MAX_SCALE, MAX_SCALE, points[0], points[1]);
                }
                fixTranslate();
                invalidate();
                return true;
            }

        });

        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor();
                float fx = detector.getFocusX();
                float fy = detector.getFocusY();
                float[] points = mapPoint(fx, fy, invertMatrix);
                scaleFactor = getRealScaleFactor(scaleFactor);
                userMatrix.preScale(scaleFactor, scaleFactor, points[0], points[1]);
                fixTranslate();
                invalidate();
                return true;
            }
        });


    }

    // 修正缩放
    private void fixTranslate() {
        Matrix viewMatrix = getMatrix();
        viewMatrix.preTranslate(baseTranslateX, baseTranslateY);
        viewMatrix.preScale(baseScale, baseScale);
        viewMatrix.preConcat(userMatrix);

        Matrix invert = new Matrix();
        viewMatrix.invert(invert);
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);

        float userScale = getMatrixValue(MSCALE_X, userMatrix);
        float scale = getMatrixValue(MSCALE_X, viewMatrix);

        float[] center = mapPoint(bitmap.getWidth() / 2f, bitmap.getHeight() / 2f, viewMatrix);
        float distanceX = center[0] - getWidth() / 2f;
        float distanceY = center[1] - getHeight() / 2f;
        float[] wh = mapVectors(bitmap.getWidth(), bitmap.getHeight(), viewMatrix);

        if (userScale <= 1.0f) {
            userMatrix.preTranslate(-distanceX / scale, -distanceY / scale);
        } else {
            float[] leftTop = mapPoint(0, 0, viewMatrix);
            float[] rightBottom = mapPoint(bitmap.getWidth(), bitmap.getHeight(), viewMatrix);

            //如果宽度小于总宽度，则水平居中
            if (wh[0] < getWidth()) {
                userMatrix.preTranslate(distanceX / scale, 0);
            } else {
                if (leftTop[0] > 0) {
                    userMatrix.preTranslate(-leftTop[0] / scale, 0);
                } else if (rightBottom[0] < getWidth()) {
                    userMatrix.preTranslate((getWidth() - rightBottom[0]) / scale, 0);
                }
            }

            //如果高度小于总高度，则垂直居中
            if (wh[1] < getHeight()) {
                userMatrix.preTranslate(0, -distanceY / scale);
            } else {
                if (leftTop[1] > 0) {
                    userMatrix.preTranslate(0, -leftTop[1] / scale);
                } else if (rightBottom[1] < getHeight()) {
                    userMatrix.preTranslate(0, (getHeight() - rightBottom[1]) / scale);
                }
            }

            invalidate();

        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        scaleGestureDetector.onTouchEvent(event);
        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            fixTranslate();
        }
        return true;
    }


    //--- Tools ------------------------------------------------------------------------------------

    //--- 将坐标转换为画布坐标 ---
    private float[] mapPoint(float x, float y, Matrix matrix) {
        float[] temp = new float[2];
        temp[0] = x;
        temp[1] = y;
        matrix.mapPoints(temp);
        return temp;
    }

    private float[] mapVectors(float x, float y, Matrix matrix) {
        float[] temp = new float[2];
        temp[0] = x;
        temp[1] = y;
        matrix.mapPoints(temp);
        return temp;
    }

    private float[] matrixValues = new float[9];
    private static final int MSCALE_X = 0, MSKEW_X = 1, MTRANS_X = 2;
    private static final int MSKEW_Y = 3, MSCALE_Y = 4, MTRANS_Y = 5;
    private static final int MPERSP_0 = 6, MPERSP_1 = 7, MPERSP_2 = 8;

    @IntDef({MSCALE_X, MSKEW_X, MTRANS_X, MSKEW_Y, MSCALE_Y, MTRANS_Y, MPERSP_0, MPERSP_1, MPERSP_2})
    @Retention(RetentionPolicy.SOURCE)
    private @interface MatrixName {
    }

    private float getMatrixValue(@MatrixName int name, Matrix matrix) {
        matrix.getValues(matrixValues);
        return matrixValues[name];
    }

    private static final float MAX_SCALE = 4.0f;    //最大缩放比例
    private static final float MIN_SCALE = 0.1f;    // 最小缩放比例

    private float getRealScaleFactor(float currentScaleFactor) {
        float realScale = 1.0f;
        float userScale = getMatrixValue(MSCALE_X, userMatrix);//当前用户的缩放比例
        float theoryScale = userScale * currentScaleFactor;

        //如果用户在执行放大操作并且理论缩放死数据大于最大缩放比例
        if (currentScaleFactor > 1.0f && theoryScale > MAX_SCALE) {
            realScale = MAX_SCALE / userScale;
        } else if (currentScaleFactor < 1.0f && theoryScale < MIN_SCALE) {
            realScale = MIN_SCALE / userScale;
        } else {
            realScale = currentScaleFactor;
        }
        return realScale;
    }


}
