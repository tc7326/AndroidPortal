package info.itloser.androidportal.components.gobroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("DD", "接收到广播");
        //接收到广播启动服务或者什么东西。
    }
}
