package info.itloser.androidportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import info.itloser.androidportal.components.goactivity.GoSingleInstanceActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_main_list)
    RecyclerView rvMainList;

    List<MainBean> mainBeans = new ArrayList<>();

    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butterknife.ButterKnife.bind(this);

        mainBeans.add(new MainBean("d", 0xff456789, GoSingleInstanceActivity.class));
        mainBeans.add(new MainBean("d", 0xff123456, null));
        mainBeans.add(new MainBean("d", 0xff789456, null));
        mainBeans.add(new MainBean("友盟分享/登录", 0xff456456, UmengShareActivity.class));
        mainBeans.add(new MainBean("手势监听", 0xff456123, GestureActivity.class));

        mainAdapter = new MainAdapter(R.layout.item_main_rv, mainBeans);
        mainAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mainBeans.get(position).activity != null)
                    startActivity(new Intent(MainActivity.this, mainBeans.get(position).activity));
            }
        });

        rvMainList.setLayoutManager(new LinearLayoutManager(this));
        rvMainList.setAdapter(mainAdapter);

    }

    class MainBean {
        String title;
        int color;
        Class activity;

        MainBean(String title, int color, Class activity) {
            this.title = title;
            this.color = color;
            this.activity = activity;
        }


    }

    class MainAdapter extends BaseQuickAdapter<MainBean, BaseViewHolder> {

        MainAdapter(int layoutResId, @Nullable List<MainBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MainBean item) {
            helper.setText(R.id.tv_main_item, item.title);
            helper.setBackgroundColor(R.id.tv_main_item, item.color);
        }
    }

}
