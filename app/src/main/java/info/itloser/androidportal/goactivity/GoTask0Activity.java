package info.itloser.androidportal.goactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import info.itloser.androidportal.R;

public class GoTask0Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("task_0", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_task0);
        setTitle("Task0Activity");
    }

    @Override
    protected void onStart() {
        Log.i("task_0", "onStart");
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i("task_0", "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        Log.i("task_0", "onResume");
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("task_0", "onCreateOptionsMenu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task0_activity_menu, menu);
        return true;
    }

    @Override
    protected void onPause() {
        Log.i("task_0", "onPause");
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i("task_0", "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        Log.i("task_0", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("task_0", "onDestroy");
        Log.i("task_0", "----------");
        //屏幕监听反注册
        super.onDestroy();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_dialog:
                break;
            case R.id.new_standard_activity:

                break;
            case R.id.new_singleTop_activity:
                break;
            case R.id.new_singleTask_activity:
                break;
            case R.id.new_singleInstance_activity:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
