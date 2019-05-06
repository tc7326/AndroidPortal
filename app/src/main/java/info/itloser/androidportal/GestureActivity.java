package info.itloser.androidportal;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GestureActivity extends BaseActivity {

    @BindView(R.id.tv_gesture)
    ImageView tvGesture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        ButterKnife.bind(this);

        /*
         * 手势
         * */

        //手势监听
        GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onContextClick(MotionEvent e) {
                Log.i("Touch", "外部设备监听，耳机");
                return super.onContextClick(e);
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.i("Touch", "这是一波单击抬起");
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.i("Touch", "这是一波单击");
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.i("Touch", "这是一波双击");
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                switch (e.getActionMasked()) {
                    case MotionEvent.ACTION_UP:
                        Log.i("Touch", "第二次抬起");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        Log.i("Touch", "第二次按下");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("Touch", "第二次按下在移动");
                        break;
                }
                return super.onDoubleTapEvent(e);
            }

            @Override
            public boolean onDown(MotionEvent e) {
                Log.i("Touch", "按下");
                return super.onDown(e);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.i("Touch", "一扔");
                return super.onFling(e1, e2, velocityX, velocityY);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.i("Touch", "长按");
                super.onLongPress(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.i("Touch", "滚动");
                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Log.i("Touch", "按下被识别[触摸反馈]");
                super.onShowPress(e);
            }

        };

        //手势检测
        final GestureDetector detector = new GestureDetector(GestureActivity.this, listener);
//        detector.setIsLongpressEnabled(false);//禁用长按事件
//        detector.isLongpressEnabled();//是否禁用了长按事件


        //
        tvGesture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });

        tvGesture.setOnClickListener(null);

        /*
         * 缩放手势
         * */

        //缩放手势监听
        ScaleGestureDetector.SimpleOnScaleGestureListener listener1 = new ScaleGestureDetector.SimpleOnScaleGestureListener() {

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return super.onScaleBegin(detector);
            }

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                return super.onScale(detector);
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                super.onScaleEnd(detector);
            }
        };

    }
}
