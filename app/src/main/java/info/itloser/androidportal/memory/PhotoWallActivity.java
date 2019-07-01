package info.itloser.androidportal.memory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.itloser.androidportal.R;

public class PhotoWallActivity extends AppCompatActivity {

    PhotoVallAdapter adapter;
    @BindView(R.id.gv_main)
    GridView gvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_wall);
        ButterKnife.bind(this);

        adapter = new PhotoVallAdapter(this, 0, Imgs.imageThumbUrls, gvMain);
        gvMain.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.cancelAllTask();
    }
}

