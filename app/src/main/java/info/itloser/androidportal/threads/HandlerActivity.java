package info.itloser.androidportal.threads;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import info.itloser.androidportal.R;

public class HandlerActivity extends AppCompatActivity {

    private final String TAG = "HandlerActivity";

    private boolean isDownloading;
    private int nowProgress;

    public final int MSG_DOWN_START = 1;
    public final int MSG_DOWN_LOADING = 2;
    public final int MSG_DOWN_SUCCESS = 3;
    public final int MSG_DOWN_FAIL = 4;

    private TextView tvTip;
    private Button btnStart;

    /*
     * 老方法可能会出现内存泄露，请使用此方法或者写一个单独的静态类。推荐当前方法。
     * */
    private Handler testHandler = new Handler(new Handler.Callback() {
        @SuppressLint("SetTextI18n")
        @Override
        public boolean handleMessage(Message msg) {
            Log.d(TAG, "handlemessage what=" + msg.what);
            switch (msg.what) {
                case MSG_DOWN_START:
                    tvTip.setText("download start");
                    break;
                case MSG_DOWN_LOADING:
                    tvTip.setText("download start");
                    break;
                case MSG_DOWN_SUCCESS:
                    tvTip.setText("download success");
                    break;
                case MSG_DOWN_FAIL:
                    tvTip.setText("download fial");
                    break;
            }
            return false;
        }
    });

    /*
     * 定时任务handler
     * */
    private Handler timeHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            timeHandler.postDelayed(this, 1000);
            Log.i(TAG, "fuck-Runnable-1000");
            //操作你的任务
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        tvTip = findViewById(R.id.tv_tip);
        btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDownloading) {

//                    new MyThread().start();
//                    new MyPostThread().start();
//                    new MyObtainThread().start();

                    runnable.run();//启动定时任务，一秒打印一次。

                }
            }
        });
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            isDownloading = true;
            Log.d(TAG, "MyThread start run");

            testHandler.sendEmptyMessage(MSG_DOWN_START);//给handler发消息（只携带what）

            try {
                Thread.sleep(3000);            //让线程休眠3s
                nowProgress++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = new Message();//发送一条消息，可以带参数或者obj
            message.what = MSG_DOWN_SUCCESS;
            //msg.arg1 = 111;
            //msg.arg2 = 222;
            //msg.obj = obj;    //可以设置arg1、arg2、obj等参数，传递这些数据
            testHandler.sendMessage(message);
            isDownloading = false;
            Log.d(TAG, "MyThread stop run");
//            super.run();
        }
    }

    class MyPostThread extends Thread {
        @Override
        public void run() {
            isDownloading = true;
            Log.d(TAG, "run threadid=" + Thread.currentThread().getId() + ",name=" + Thread.currentThread().getName());

            try {
                Thread.sleep(3000);//让线程休眠3s
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            testHandler.post(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    Log.d(TAG, "Runnable threadid=" + Thread.currentThread().getId() + ",name=" + Thread.currentThread().getName());
                    tvTip.setText("download success");
                }
            });
            isDownloading = false;
        }
    }

    class MyObtainThread extends Thread {
        @Override
        public void run() {
            isDownloading = true;
            testHandler.obtainMessage(MSG_DOWN_START).sendToTarget();
            try {
                Thread.sleep(3000);//让线程睡眠3s。
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            testHandler.obtainMessage(MSG_DOWN_SUCCESS).sendToTarget();
            isDownloading = false;
        }
    }



}
