package info.itloser.androidportal.goactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import info.itloser.androidportal.R;

public class GoTask1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("task_1", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_task1);
        setTitle("Task1Activity");
    }

    @Override
    protected void onStart() {
        Log.i("task_1", "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i("task_1", "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("task_1", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("task_1", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("task_1", "onDestroy");
        Log.i("task_1", "----------");
        super.onDestroy();
    }
}
