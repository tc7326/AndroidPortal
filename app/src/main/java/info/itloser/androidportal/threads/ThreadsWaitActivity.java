package info.itloser.androidportal.threads;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import info.itloser.androidportal.R;

/*
 * 相关知识点：CountDownLatch
 * */
public class ThreadsWaitActivity extends AppCompatActivity {

    final static String TAG = "ThreadsWaitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads_wait);

        //开始测试
        Producer producer = new Producer();
        producer.start();
        new Consumer("Consumer1", producer).start();
        new Consumer("Consumer2", producer).start();
        new Consumer("Consumer3", producer).start();

    }

    //消息bean
    class Msg {
    }

    //生产者
    class Producer extends Thread {

        List<Msg> msgList = new ArrayList<>();

        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(3000);
                    Msg msg = new Msg();
                    synchronized (msgList) {
                        msgList.add(msg);
                        msgList.notify();//唤醒线程
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public Msg waitMsg() {
            synchronized (msgList) {
                if (msgList.size() == 0) {
                    try {
                        msgList.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return msgList.remove(0);
            }
        }

    }

    //消费者
    class Consumer extends Thread {

        Producer producer;

        Consumer(String name, Producer producer) {
            super(name);
            this.producer = producer;
        }

        @Override
        public void run() {
            while (true) {
                producer.waitMsg();
                Log.i(TAG, getName() + "");
            }
        }
    }

}
