package info.itloser.androidportal.threads;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import info.itloser.androidportal.R;

public class ThreadRunnableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_runnable);

        //串行执行
        new MyAsyncTask("task0").execute();
        new MyAsyncTask("task1").execute();
        new MyAsyncTask("task2").execute();

        //并发执行
        new MyAsyncTask("task3").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        new MyAsyncTask("task4").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        new MyAsyncTask("task5").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");


    }

    class MyRunnable implements Runnable {
        @Override
        public void run() {

        }
    }

    /*
     * JDK1.5后添加，可以返回值和抛出异常。
     * */
    class MyCallable implements Callable<Object> {
        @Override
        public Object call() throws Exception {

            return null;
        }
    }

    /*
     *
     * */
    class MyFuture implements Future<Object> {

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public Object get() throws InterruptedException, ExecutionException {
            return null;
        }

        @Override
        public Object get(long timeout, @NonNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    }

    /*
     * 必须static否则有内存泄露的风险
     * */
    static class MyAsyncTask extends AsyncTask {

        private String name;

        MyAsyncTask(String name) {
            this.name = name;
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Log.i(name, dateFormat.format(new Date()));
            return null;
        }
    }


}
