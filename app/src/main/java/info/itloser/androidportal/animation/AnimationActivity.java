package info.itloser.androidportal.animation;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import info.itloser.androidportal.R;

public class AnimationActivity extends AppCompatActivity {

    ListView listView;
    List<String> strings = new ArrayList<>();
    ImageView imageView0;
    boolean isDrawableAnimation;
    AnimationDrawable animationDrawable;

    Button btnAdd;
    Button btnRem;
    LinearLayout llFuck;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        listView = findViewById(R.id.lv_show);

        strings.add("数据一");
        strings.add("数据er");
        strings.add("数据s");
        strings.add("数据4");
        strings.add("数据五");
        strings.add("数据6");

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings));

        /*
         * 代码动态设置View的动画
         * */
        Animation loadAnimation = AnimationUtils.loadAnimation(AnimationActivity.this,
                R.anim.animation_all);
        loadAnimation.setFillAfter(true);
        findViewById(R.id.iv_show).startAnimation(loadAnimation);

        /*
         * 代码动态设置listview子view的入场动画
         * */
        LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.animation_all));
        lac.setDelay(0.5f);//延迟
        lac.setOrder(LayoutAnimationController.ORDER_RANDOM);//设置每个item出场顺序
        listView.setLayoutAnimation(lac);
        listView.startLayoutAnimation(); //可以通过该方法控制动画在何时播放。


        /*
         * 帧动画
         * */
        imageView0 = findViewById(R.id.iv_show_0);
        imageView0.setImageResource(R.drawable.drawable_animation);//获取资源文件

//        //代码定义、创建、执行帧动画
//        AnimationDrawable animationDrawable = new AnimationDrawable();
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.first_pic), 1000);
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.second_pic), 1000);
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.third_pic), 1000);
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.fourth_pic), 1000);
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.fifth_pic), 1000);
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.sixth_pic), 1000);
//        animationDrawable.setOneShot(true);
//        image.setImageDrawable(animationDrawable);
//        animationDrawable.start();

        animationDrawable = (AnimationDrawable) imageView0.getDrawable();
        animationDrawable.start();

        //帧动画单击停止，单击再开始。
        imageView0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("dd", "暂停");
                //如果oneshot为false，必要时要停止动画
                if (isDrawableAnimation) {
                    animationDrawable.start();
                    isDrawableAnimation = false;
                } else {
                    animationDrawable.stop();
                    isDrawableAnimation = true;
                }
            }
        });

        /*
         * 分割线
         * */

        final ImageView ivShow1 = findViewById(R.id.iv_show_1);

        /*
         * 属性动画
         * */

        /*
         * ObjectAnimator    通过ObjectAnimator实现属性动画
         * */


        /*
         * ObjectAnimator   常用，直接应用在View上
         * */

        //透明度
//        ObjectAnimator.ofFloat(ivShow1, "alpha", 1, 0, 1).setDuration(5000).start();

        //缩放动画
//        AnimatorSet set = new AnimatorSet();
//        ivShow1.setPivotX(ivShow1.getWidth() / 2);
//        ivShow1.setPivotY(ivShow1.getHeight() / 2);
//        set.playTogether(ObjectAnimator.ofFloat(ivShow1, "scaleX", 1, 0,1).setDuration(5000), ObjectAnimator.ofFloat(ivShow1, "scaleY", 1, 0,1).setDuration(5000));
//        set.start();

//        平移动画
//        AnimatorSet set0 = new AnimatorSet();
//        set0.playTogether(ObjectAnimator.ofFloat(ivShow1, "translationX", 1, 1000,100).setDuration(5000), ObjectAnimator.ofFloat(ivShow1, "translationY", 1, 0).setDuration(5000));
//        set0.start();

        //旋转动画
        ivShow1.setPivotX(ivShow1.getWidth() / 2);
        ivShow1.setPivotY(ivShow1.getHeight() / 2);
        ObjectAnimator.ofFloat(ivShow1, "rotation", 0, 400).setDuration(5000).start();

        /*
         * ValueAnimator    通过ValueAnimator实现属性动画（负责属性动画的值的计算--------本质就是一个数值生成器）
         * 只需提供初始和结束值，就会自动生成数值。
         * */
//        ValueAnimator v = ValueAnimator.ofFloat(0f, 1f, 0f);
//        v.setDuration(1000);
//        v.start();

//        ValueAnimator v0 = ValueAnimator.ofInt(128, 1, 128);
//        v0.setDuration(5000);
//        v0.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int currentValue = (int) animation.getAnimatedValue();
//                Log.i("dd", "current value is " + currentValue);
//                ViewGroup.LayoutParams params = ivShow1.getLayoutParams();
//                params.height = (int) animation.getAnimatedValue();
//                ivShow1.setLayoutParams(params);
//                ivShow1.setLayoutParams(params);
//            }
//        });
//        v0.start();

        btnAdd = findViewById(R.id.btn_add);
        btnRem = findViewById(R.id.btn_rem);
        llFuck = findViewById(R.id.ll_fuck);

        //进场动画
        @SuppressLint("ObjectAnimatorBinding") ObjectAnimator appearingAnimator = ObjectAnimator
                .ofPropertyValuesHolder(
                        (Object) null,
                        PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
                        PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f),
                        PropertyValuesHolder.ofFloat("alpha", 0, 1.0f));
        //退场动画
        @SuppressLint("ObjectAnimatorBinding") ObjectAnimator disappearingAnimator = ObjectAnimator
                .ofPropertyValuesHolder(
                        (Object) null,
                        PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.0f),
                        PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.0f),
                        PropertyValuesHolder.ofFloat("alpha", 1.0f, 0));

        LayoutTransition transition = new LayoutTransition();

        //子View进场时兄弟View
        transition.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
        transition.setDuration(LayoutTransition.CHANGE_APPEARING, transition.getDuration(LayoutTransition.CHANGE_APPEARING));
        transition.setStartDelay(LayoutTransition.CHANGE_APPEARING, 0);

        //子View进场
        transition.setAnimator(LayoutTransition.APPEARING, appearingAnimator);
        transition.setDuration(LayoutTransition.APPEARING, transition.getDuration(LayoutTransition.APPEARING));
        transition.setStartDelay(LayoutTransition.APPEARING, transition.getDuration(LayoutTransition.CHANGE_APPEARING));

        //子View退场
        transition.setAnimator(LayoutTransition.DISAPPEARING, disappearingAnimator);
        transition.setDuration(LayoutTransition.DISAPPEARING, transition.getDuration(LayoutTransition.DISAPPEARING));
        transition.setStartDelay(LayoutTransition.DISAPPEARING, 0);

        //子View退场时兄弟View
        transition.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);
        transition.setDuration(LayoutTransition.CHANGE_DISAPPEARING, transition.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
        transition.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, transition.getDuration(LayoutTransition.DISAPPEARING));

        llFuck.setLayoutTransition(transition);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = new ImageView(AnimationActivity.this);
                imageView.setImageResource(R.mipmap.ic_launcher);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 200);
                llFuck.addView(imageView, 0, layoutParams);
            }
        });

        btnRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = llFuck.getChildCount();
                if (count > 0) {
                    llFuck.removeViewAt(0);
                }
            }
        });


    }


}
