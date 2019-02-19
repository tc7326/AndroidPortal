package info.itloser.androidportal.components.gobroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Objects;

import info.itloser.androidportal.R;

public class BroadcastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        setTitle("BroadcastReceiverActivity");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //静态注册在xml中

        //动态注册三个广播，演示有序广播
        IntentFilter lowFilter = new IntentFilter("ordered_broadcast");
        IntentFilter midFilter = new IntentFilter("ordered_broadcast");
        IntentFilter highFilter = new IntentFilter("ordered_broadcast");
        //调用IntentFilter的setPriority（int priority）方法设置优先级，参数值可以是-1000~1000，值越大，优先级越高。同样的在静态注册中，通过设置intent-filter标签的priority属性来设置优先级。
        lowFilter.setPriority(10);
        midFilter.setPriority(50);
        highFilter.setPriority(100);
        lowerRe lowReceiver = new lowerRe();
        middleRe midReceiver = new middleRe();
        highRe highReceiver = new highRe();
        registerReceiver(lowReceiver, lowFilter);
        registerReceiver(midReceiver, midFilter);
        registerReceiver(highReceiver, highFilter);

        //动态注册本地广播
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);//需要用到manager
        IntentFilter localFilter = new IntentFilter("local_broadcast");
        localRe localReceiver = new localRe();
        localBroadcastManager.registerReceiver(localReceiver, localFilter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.broadcast_activity_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send_broadcast:
                //发送一条普通广播
                sendBroadcast(new Intent("my_broadcast"));
                break;
            case R.id.send_ordered_broadcast:
                //有序广播
                sendOrderedBroadcast(new Intent("ordered_broadcast"), null);
                break;
            case R.id.send_local_broadcast:
                //发送一条本地广播，即应用内广播
                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);//需要用到manager
                Intent localIntent = new Intent("local_broadcast");
                localBroadcastManager.sendBroadcast(localIntent);
                break;
            case R.id.send_permission_broadcast:
                sendBroadcast(new Intent("my_broadcast"), "info.itloser.androidportal.AUTO_PERMISSION");//参数二   权限，在xml中声明
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //静态权限注册在xml中

    /*
     * 动态带权限注册
     * */
//    registerReceiver(receiver, filter, "itloser.info.mymap2.UN_NEED_PERMISSION",null);

    /*
     * 三个不同的BR，有序广播
     * */
    public class highRe extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("highRe--->等级", "100");
            Bundle bundle = new Bundle();
            bundle.putString("名字", "恶狗吠尧");
            bundle.putInt("年龄", 21);
//          发送给下一个BR
            setResultExtras(bundle);
//          截止广播
            abortBroadcast();
        }
    }

    public class lowerRe extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("lowerRe--->等级", "10");
            String name = getResultExtras(false).getString("名字");
            int age = getResultExtras(false).getInt("年龄");
            Log.i("lowerRe--->信息", "他的 名字 是 " + name + ", 他的 年龄 是 " + age);
        }
    }

    public class middleRe extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("middleRe--->等级", "50");
            Bundle bundle = getResultExtras(false);
            String name = bundle.getString("名字");
            int age = bundle.getInt("年龄");
            Log.i("middleRe--->信息", "他的 名字 是 " + name + ",他的 年龄 是 " + age);
            bundle.putString("名字", "傻屌");
            setResultExtras(bundle);
        }
    }

    /*
     * 本地广播：应用内广播，只能被当前应用接收，并且只能动态注册。高安全性。
     * */
    public class localRe extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("localRe", "这是一条本地广播");
        }
    }


}
