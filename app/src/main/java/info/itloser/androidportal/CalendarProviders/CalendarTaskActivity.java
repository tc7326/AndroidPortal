package info.itloser.androidportal.CalendarProviders;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Reminders;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.itloser.androidportal.R;

public class CalendarTaskActivity extends AppCompatActivity {

    Context mContext;
    @BindView(R.id.btn_sys_cal)
    Button btnSysCal;
    @BindView(R.id.btn_add)
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_task);
        ButterKnife.bind(this);

        mContext = this;
        //系统日历添加事件

    }

    @OnClick({R.id.btn_sys_cal, R.id.btn_add, R.id.btn_sys_cal_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sys_cal:
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(2019, 7 - 1, 16, 16, 0);//月份减1不能忘了
                Calendar endTime = Calendar.getInstance();
                endTime.set(2019, 7 - 1, 20, 12, 19);
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE, "备忘标题测试")
                        .putExtra(CalendarContract.Events.DESCRIPTION, "备忘详细描述测试")
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, "备忘地址测试")
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                startActivity(intent);
                break;
            case R.id.btn_sys_cal_query:
                // 时间戳表示时间
                Calendar beginTime0 = Calendar.getInstance();
                beginTime0.set(2019, 7 - 1, 17, 16, 0);//月份减1不能忘了
                long startMillis = beginTime0.getTime().getTime();
                Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                builder.appendPath("time");
                ContentUris.appendId(builder, startMillis);
                Intent intent0 = new Intent(Intent.ACTION_VIEW)
                        .setData(builder.build());
                startActivity(intent0);
                break;

            case R.id.btn_add:

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        addEvent();
                    }
                }).start();

                break;
        }
    }

    private void addEvent() {


        long calID = checkCalendarAccount(CalendarTaskActivity.this);
        if (calID < 0) {
            Toast.makeText(mContext, "未检测到日历账户，写入失败！", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i("dd拿到系统日历id：", calID + "");
        long startMillis;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2019, 7 - 1, 24, 18, 10);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2019, 7 - 1, 24, 20, 10);
        endMillis = endTime.getTimeInMillis();

        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(Events.DTSTART, startMillis);
        values.put(Events.DTEND, endMillis);
        values.put(Events.TITLE, "VV测试标题");
        values.put(Events.DESCRIPTION, "VV测试内容");
        values.put(Events.CALENDAR_ID, calID);
        values.put(Events.EVENT_TIMEZONE, "Asia/Beijing");//时区，必填
        @SuppressLint("MissingPermission") Uri uri = cr.insert(Events.CONTENT_URI, values);//插入

        /*
         * 拿到刚才添加的事件的id
         * */
        assert uri != null;
        long eventID = Long.parseLong(uri.getLastPathSegment());
        Log.i("插入的事件的id", eventID + "");

        /*
         * 根据拿到的事件id 添加提醒
         * */
        ContentResolver crr = getContentResolver();
        ContentValues valuess = new ContentValues();
        valuess.put(Reminders.MINUTES, 9999999);//提前15分钟提醒
        valuess.put(Reminders.EVENT_ID, eventID);//刚才插入的事件的id
        valuess.put(Reminders.METHOD, Reminders.METHOD_ALERT);//提醒方式
        @SuppressLint("MissingPermission") Uri urii = crr.insert(Reminders.CONTENT_URI, valuess);//添加提醒

    }

    private static int checkCalendarAccount(Context context) {
        try (@SuppressLint("MissingPermission") Cursor userCursor = context.getContentResolver().query(CalendarContract.Calendars.CONTENT_URI, null, null, null, null)) {
            if (userCursor == null)//查询返回空值
                return -1;
            int count = userCursor.getCount();
            if (count > 0) {//存在现有账户，取第一个账户的id返回
                userCursor.moveToFirst();
                return userCursor.getInt(userCursor.getColumnIndex(CalendarContract.Calendars._ID));
            } else {
                return -1;
            }
        }
    }


}
