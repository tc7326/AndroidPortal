package info.itloser.androidportal;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import info.itloser.androidportal.listener.ScreenListener;

/**
 * author：zhaoliangwang on 2019/2/13 11:27
 * email：tc7326@126.com
 */
public class BaseApplication extends Application implements ScreenListener.ScreenStateListener {

    @Override
    public void onCreate() {
        super.onCreate();
        //屏幕监听
        new ScreenListener(getApplicationContext()).begin(this);

        //初始化
        UMConfigure.init(this, "你的应用在友盟上的APPKEY", "umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
        //友盟相关平台配置
        PlatformConfig.setWeixin("123456", "123456");//微信APPID和AppSecret
        PlatformConfig.setQQZone("123456", "123456");//QQAPPID和AppSecret
        PlatformConfig.setSinaWeibo("123456", "123456", null);//微博APPID和AppSecret
        //添加debug库
        UMConfigure.setLogEnabled(true);

        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.page_icon_float_window);

//        FloatWindow
//                .with(getApplicationContext())
//                .setView(imageView)
//                .setWidth(Screen.WIDTH, 0.3f)         //设置控件宽高
//                .setHeight(Screen.WIDTH, 0.3f)
//                .setX(Screen.WIDTH, 0.35f)             //设置控件初始位置
//                .setY(Screen.HEIGHT, 0.8f)
//                .setMoveType(MoveType.ACTIVE)
//                .setDesktopShow(true)                        //桌面显示
//                .build();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "哈哈哈", Toast.LENGTH_SHORT).show();
            }
        });



    }

    /*
     * 屏幕相关的三个监听的回调
     * */

    //屏幕开始
    @Override
    public void onScreenOn() {
        Log.i("system_0", "onScreenOn");
    }

    //屏幕关闭
    @Override
    public void onScreenOff() {
        Log.i("system_0", "onScreenOff");
    }

    //用户解锁
    @Override
    public void onUserPresent() {
        Log.i("system_0", "onUserPresent");
    }

}
