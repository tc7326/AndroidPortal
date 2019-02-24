package info.itloser.androidportal.animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

//        //代码定义、创建、执行动画
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


    }


}
