package info.itloser.androidportal.components.goactivity;

import android.os.Bundle;
import android.widget.TextView;

import info.itloser.androidportal.R;

public class GoSingleTaskActivity extends BaseTaskActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_standard);
        setTitle("Activity by SingleTask");
        ((TextView)findViewById(R.id.tv_mode)).setText(R.string.new_activity_by_singleTask);
    }

}
