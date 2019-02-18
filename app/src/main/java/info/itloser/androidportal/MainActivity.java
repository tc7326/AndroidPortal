package info.itloser.androidportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import info.itloser.androidportal.components.goactivity.GoStandardActivity;
import info.itloser.androidportal.components.goservice.ServiceActivity;
import info.itloser.androidportal.custom.MyCustomPopupWindow;

public class MainActivity extends AppCompatActivity {

    //数据集合
    private List<String> strings = new ArrayList<String>();
    MyCustomPopupWindow myCustomPopupWindow;

    ConstraintLayout clMain;
    Button btnW;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_activity:
                startActivity(new Intent(MainActivity.this, GoStandardActivity.class));
                break;
            case R.id.go_service:
                startActivity(new Intent(MainActivity.this, ServiceActivity.class));
                break;
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        strings.add("光年之外");
        strings.add("倒数");
        strings.add("睡皇后");
        strings.add("我的秘密");
        strings.add("岩石里的花");
        strings.add("再见");

        clMain = findViewById(R.id.cl_main);
        btnW = findViewById(R.id.btn_w);
        btnW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCustomPopupWindow.show(clMain);
            }
        });

        /*
         * this      上下文
         * title     标题
         * strings   集合
         * positon   默认选中
         * */
        myCustomPopupWindow = new MyCustomPopupWindow(this, "这是标题", strings, 2);
        myCustomPopupWindow.setOnMyCustomPopWindowSaveListener(new MyCustomPopupWindow.OnMyCustomPopWindowSaveListener() {
            @Override
            public void getItem(int i) {
                Toast.makeText(MainActivity.this, strings.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
