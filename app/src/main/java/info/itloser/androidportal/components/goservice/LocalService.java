package info.itloser.androidportal.components.goservice;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

public class LocalService extends Service {

    CountDownTimer countDownTimer;

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("dd", "onBind");
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("dd", "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        Log.i("dd", "onCreate");
        super.onCreate();
        //开一个计时器打印日志dd
        countDownTimer = new CountDownTimer(24 * 60 * 60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i("dd", "服务已开启" + (24 * 60 * 60 - millisUntilFinished / 1000) + "S");
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

//    @Override
//    public void onStart(Intent intent, int startId) {
//        Log.i("dd", "onStart");
//        super.onStart(intent, startId);
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("dd", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        Log.i("dd", "bindService");
        return super.bindService(service, conn, flags);
    }

    @Override
    public void onDestroy() {
        Log.i("dd", "onDestroy");
        super.onDestroy();
        countDownTimer.cancel();
    }

    /*
     * 接口
     * */
    public interface MyIBinder {
        void invokeMethodInMyService();
    }

    class MyBinder extends Binder implements MyIBinder {

        public void stopService() {
            stopSelf();
        }


        @Override
        public void invokeMethodInMyService() {
            Log.i("dd", "调用Service中的方法");
        }
    }

}
