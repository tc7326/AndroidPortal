package info.itloser.androidportal.statusbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.itloser.androidportal.R;
import info.itloser.androidportal.custom.TopToast;

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

        TopToast.makeText(this, "dasdasdasdas", Toast.LENGTH_SHORT).show();

        //初始化数据
        fakeDataBeans = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            fakeDataBeans.add(new FakeDataBean("Title" + i, "Content" + i));
        }

        rvMain.setLayoutManager(new LinearLayoutManager(this));
        rvMain.setAdapter(new FakeDataAdapter(R.layout.item_fake_data, fakeDataBeans));


    }

    @Override
    protected void onResume() {
        super.onResume();



        /*
         * 关闭电量优化，和StatusBar无关
         * */
//        Log.i("dd", getPackageName());
//        try {
//            Intent intent = null;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                //需要在Manifest声明电池优化的权限
//                intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//                intent.setData(Uri.parse("package:" + getPackageName()));
//            }
//            startActivity(intent);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


    }
}
