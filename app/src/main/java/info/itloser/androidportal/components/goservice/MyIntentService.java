package info.itloser.androidportal.components.goservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {

    private boolean isRunning = true;
    private int count = 0;
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("dd", "服务启动");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Log.i("dd", "线程启动" + count);
            Thread.sleep(1_000);
            Log.i("dd", "服务运行中...");

            isRunning = true;
            count = 0;
            while (isRunning) {
                count++;
                if (count >= 100) {
                    isRunning = false;
                }
                Thread.sleep(50);
                Log.i("dd", "线程运行中..." + count);
            }
            Log.i("dd", "线程结束" + count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        Log.i("dd", "onDestroy");
        super.onDestroy();
    }


}
