package info.itloser.androidportal.threads;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import info.itloser.androidportal.R;

/*
 * 相关知识点：CountDownLatch
 * */
public class VideoWaitActivity extends AppCompatActivity {

    final static String TAG = "VideoWaitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_wait);

        //开启视频会议
        VideoConference videoConference = new VideoConference(10);
        new Thread(videoConference).start();

        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new Participant("P-" + i, videoConference));
        }
        for (int i = 0; i < 10; i++) {
            threads[i].start();
        }

    }

    /*
     * 视频会议线程类
     * */
    class VideoConference implements Runnable {

        final CountDownLatch controller;

        VideoConference(int number) {
            controller = new CountDownLatch(number);
        }

        void arrive(String name) {
            Log.i(TAG, name + "加入了会议！");
            controller.countDown();
            Log.i(TAG, "剩余参会者人数：" + controller.getCount());
        }

        @Override
        public void run() {
            Log.i(TAG, "会议准备，总参会人数：" + controller.getCount());

            try {
                controller.await();

                Log.i(TAG, "所有参会人员已经到齐！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    /*
     * 参会者线程类
     * */
    class Participant implements Runnable {
        String name;
        VideoConference videoConference;

        Participant(String name, VideoConference videoConference) {
            this.name = name;
            this.videoConference = videoConference;
        }

        @Override
        public void run() {
            long duration = (long) (Math.random() * 10 + 5);

            try {
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            videoConference.arrive(name);

        }
    }

}