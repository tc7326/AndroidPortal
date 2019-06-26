package info.itloser.androidportal.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.itloser.androidportal.R;

public class RecyclerViewAnimationActivity extends AppCompatActivity {

    @BindView(R.id.rv_main)
    RecyclerView rvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_animation);
        ButterKnife.bind(this);
    }
}
