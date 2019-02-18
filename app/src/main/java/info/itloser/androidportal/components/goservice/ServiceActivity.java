package info.itloser.androidportal.components.goservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Objects;

import info.itloser.androidportal.R;

public class ServiceActivity extends AppCompatActivity {

    LocalService.MyBinder myBinder;
    ServiceConnection serviceConnection;

    //定义一个全局变量用来标记是否bindService
    private boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        setTitle("ServiceActivity");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        serviceConnection = new MyServiceConnection();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.service_activity_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.start_service:
                startService(new Intent(ServiceActivity.this, LocalService.class));
                break;
            case R.id.bind_service:
                isConnected = bindService(new Intent(ServiceActivity.this, LocalService.class), serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                if (isConnected) {
                    unbindService(serviceConnection);
                    isConnected = false;
                }
                break;
            case R.id.use_Service:
                myBinder.invokeMethodInMyService();
                break;
            case R.id.stop_service:
                stopService(new Intent(ServiceActivity.this, LocalService.class));
                break;
            case R.id.intent_service:
                startService(new Intent(ServiceActivity.this, MyIntentService.class));
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (isConnected) {
            unbindService(serviceConnection);
            isConnected = false;
        }
        super.onDestroy();
    }

    class MyServiceConnection implements ServiceConnection {

        //这里的第二个参数IBinder就是Service中的onBind方法返回的
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("dd", "onServiceConnected");
            myBinder = (LocalService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("dd", "onServiceDisconnected");
        }
    }

}
