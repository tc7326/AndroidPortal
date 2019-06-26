package info.itloser.androidportal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RiLiActivity extends AppCompatActivity implements CalendarView.OnCalendarRangeSelectListener {

    @BindView(R.id.cv_calendar_main)
    CalendarView cvCalendarMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ri_li);
        ButterKnife.bind(this);

        cvCalendarMain.setOnCalendarRangeSelectListener(this);


    }

    @Override
    public void onCalendarSelectOutOfRange(Calendar calendar) {

    }

    @Override
    public void onSelectOutOfRange(Calendar calendar, boolean isOutOfMinRange) {

    }

    @Override
    public void onCalendarRangeSelect(Calendar calendar, boolean isEnd) {
        if (isEnd) {
            Toast.makeText(this, "结束：" + calendar.getYear() + "年|" + calendar.getMonth() + "月|" + calendar.getDay() + "日", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "开始：" + calendar.getYear() + "年|" + calendar.getMonth() + "月|" + calendar.getDay() + "日", Toast.LENGTH_SHORT).show();

    }
}
