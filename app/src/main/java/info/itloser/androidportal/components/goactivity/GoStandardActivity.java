package info.itloser.androidportal.components.goactivity;

import android.os.Bundle;

import info.itloser.androidportal.R;

public class GoStandardActivity extends BaseTaskActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_standard);
        setTitle("Activity by Standard");
    }

}
