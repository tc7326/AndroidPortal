package info.itloser.androidportal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * author：zhaoliangwang on 2019/4/12 09:57
 * email：tc7326@126.com
 */
public class MySGView extends View {

    private static final String TAG = "SGD";

    private ScaleGestureDetector scaleGestureDetector;

    public MySGView(Context context) {
        super(context);
    }

    public MySGView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "初始化MySGView");
        initSGD();
    }

    private void initSGD() {
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                Log.i(TAG, "focusX = " + detector.getFocusX());       // 缩放中心，x坐标
                Log.i(TAG, "focusY = " + detector.getFocusY());       // 缩放中心，y坐标
                Log.i(TAG, "scale = " + detector.getScaleFactor());   // 缩放因子，比例

                return super.onScale(detector);
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                Log.i(TAG, "onScaleBegin");
                return super.onScaleBegin(detector);
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                Log.i(TAG, "onScaleEnd");
                super.onScaleEnd(detector);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }
}
