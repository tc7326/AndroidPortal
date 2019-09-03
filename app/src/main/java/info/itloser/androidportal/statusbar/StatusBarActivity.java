package info.itloser.androidportal.statusbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.itloser.androidportal.R;

public class StatusBarActivity extends AppCompatActivity {

    @BindView(R.id.rv_main)
    RecyclerView rvMain;

    List<FakeDataBean> fakeDataBeans;

    @BindView(R.id.tb_top)
    Toolbar tbTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar);
        ButterKnife.bind(this);

        StatusBarUtil.setTranslucentForImageView(this, 0, tbTop);

        //初始化数据
        fakeDataBeans = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            fakeDataBeans.add(new FakeDataBean("Title" + i, "Content" + i));
        }

        rvMain.setLayoutManager(new LinearLayoutManager(this));
        rvMain.setAdapter(new FakeDataAdapter(R.layout.item_fake_data, fakeDataBeans));

    }
}
