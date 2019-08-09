package info.itloser.androidportal.rxjavas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import info.itloser.androidportal.R;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class RxJavaActivity extends AppCompatActivity {
    static final String TAG = "RxJavaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        createObserver();

    }


    void createObserver() {

        /*
         * 注册一个观察者
         * */

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "队列已完成");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "队列异常");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "" + s);
            }
        };

        /*
         * 创建一个被观察者
         * */
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("i wanna");
                subscriber.onNext("fuck u");
                subscriber.onCompleted();
            }
        });

        observable.subscribe(observer);


    }


    void createSubscriber() {

        /*
         * 和Observer同级的一个操作
         * 但是新增了两个方法：
         * onStart()
         * unSubscribe()
         * */

        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onStart() {
                super.onStart();
                Log.i(TAG, "队列开始");
            }


            @Override
            public void onCompleted() {
                Log.i(TAG, "队列已完成");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "队列异常");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "" + s);
            }
        };
    }


}
