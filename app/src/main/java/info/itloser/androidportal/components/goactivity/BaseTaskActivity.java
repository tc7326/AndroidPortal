package info.itloser.androidportal.components.goactivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import info.itloser.androidportal.R;

public class BaseTaskActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        Log.i("dd", "onStart：" + getClass().getSimpleName() + " TaskId: " + getTaskId() + " hasCode:" + this.hashCode());
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("dd", "onCreate：" + getClass().getSimpleName() + " TaskId: " + getTaskId() + " hasCode:" + this.hashCode());
        dumpTaskAffinity();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("dd", "onNewIntent：" + getClass().getSimpleName() + " TaskId: " + getTaskId() + " hasCode:" + this.hashCode());
        dumpTaskAffinity();
    }

    @Override
    protected void onResume() {
        Log.i("dd", "onResume：" + getClass().getSimpleName() + " TaskId: " + getTaskId() + " hasCode:" + this.hashCode());
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        Log.i("dd", "onCreateOptionsMenu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task0_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_dialog:
                break;
            case R.id.new_standard_activity:
                startActivity(new Intent(getApplicationContext(), GoStandardActivity.class));
                break;
            case R.id.new_singleTop_activity:
                startActivity(new Intent(getApplicationContext(), GoSingleTopActivity.class));
                break;
            case R.id.new_singleTask_activity:
                startActivity(new Intent(getApplicationContext(), GoSingleTaskActivity.class));
                break;
            case R.id.new_singleInstance_activity:
                startActivity(new Intent(getApplicationContext(), GoSingleInstanceActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        Log.i("dd", "onPause：" + getClass().getSimpleName() + " TaskId: " + getTaskId() + " hasCode:" + this.hashCode());
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("dd", "onStop：" + getClass().getSimpleName() + " TaskId: " + getTaskId() + " hasCode:" + this.hashCode());
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("dd", "onDestroy：" + getClass().getSimpleName() + " TaskId: " + getTaskId() + " hasCode:" + this.hashCode());
        super.onDestroy();
    }


    /*
     * 打印activity日志
     * */
    protected void dumpTaskAffinity() {
        try {
            ActivityInfo info = this.getPackageManager()
                    .getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            Log.i("dd", "taskAffinity:" + info.taskAffinity);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
