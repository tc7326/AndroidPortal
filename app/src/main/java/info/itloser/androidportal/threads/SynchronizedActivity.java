package info.itloser.androidportal.threads;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.itloser.androidportal.R;

public class SynchronizedActivity extends AppCompatActivity {

    @BindView(R.id.btn_sync)
    Button btnSync;
    @BindView(R.id.btn_sync_0)
    Button btnSync0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronized);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.btn_sync, R.id.btn_sync_0})

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sync:
                OneSync oneSync = new OneSync();
                for (int i = 0; i < 3; i++) {

//                    new OneThread().start();

//                    new TwoThread(oneSync).start();

//                    new ThreeThread().start();

                    new FourThread().start();

                }
                break;
            case R.id.btn_sync_0:

                break;
        }
    }

    private class OneSync {

        synchronized void testOne() {
            Log.i("testOne-start", new Date() + "");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("testOne-end", new Date() + "");
        }

        void testTwo() {
            synchronized (this) {
                Log.i("testOne-start", new Date() + "");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("testOne-end", new Date() + "");
            }
        }

        void testThree() {
            synchronized (OneSync.class) {
                Log.i("testOne-start", new Date() + "");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("testOne-end", new Date() + "");
            }
        }

    }

    /*
     * 静态
     * */
    private static class TwoSync {

        static synchronized void testFour() {
            Log.i("testOne-start", new Date() + "");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("testOne-end", new Date() + "");
        }

    }

    class OneThread extends Thread {
        @Override
        public void run() {
            new OneSync().testOne();
        }
    }

    /*
     * 共用一个对象
     * */
    class TwoThread extends Thread {

        OneSync oneSync;

        TwoThread(OneSync oneSync) {
            this.oneSync = oneSync;
        }

        @Override
        public void run() {
            oneSync.testOne();
        }
    }


    private class ThreeThread extends Thread {
        @Override
        public void run() {
            new OneSync().testThree();
        }
    }

    /*
     * static synchronized方法也相当于全局锁，相当于锁住了代码段
     * */
    private class FourThread extends Thread {
        @Override
        public void run() {
            TwoSync.testFour();
        }
    }


}
