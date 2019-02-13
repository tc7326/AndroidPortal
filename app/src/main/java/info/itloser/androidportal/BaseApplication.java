package info.itloser.androidportal;

import android.app.Application;
import android.util.Log;

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
