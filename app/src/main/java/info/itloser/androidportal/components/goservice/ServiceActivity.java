package info.itloser.androidportal.components.goservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import info.itloser.androidportal.R;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        setTitle("ServiceActivity");
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
//                bindService(new Intent(ServiceActivity.this, LocalService.class), null);
                break;
            case R.id.stop_service:
                stopService(new Intent(ServiceActivity.this, LocalService.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
