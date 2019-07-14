package info.itloser.androidportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import info.itloser.androidportal.components.goactivity.GoSingleInstanceActivity;
import info.itloser.androidportal.file_sql.FileActivity;
import info.itloser.androidportal.file_sql.SerialActivity;
import info.itloser.androidportal.memory.DiskLruCacheActivity;
import info.itloser.androidportal.memory.EasyPhotoWallActivity;
import info.itloser.androidportal.memory.MemoryActivity;
import info.itloser.androidportal.memory.PhotoWallActivity;
import info.itloser.androidportal.qrcode.QRActivity;
import info.itloser.androidportal.socket.MyWebSocketService;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_main_list)
    RecyclerView rvMainList;

    List<MainBean> mainBeans = new ArrayList<>();

    MainAdapter mainAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butterknife.ButterKnife.bind(this);

        mainBeans.add(new MainBean("d", 0xff456789, GoSingleInstanceActivity.class));
        mainBeans.add(new MainBean("日历", 0xff123456, RiLiActivity.class));
        mainBeans.add(new MainBean("悬浮框", 0xff789456, null));
        mainBeans.add(new MainBean("友盟分享/登录", 0xff456456, UmengShareActivity.class));
        mainBeans.add(new MainBean("手势监听", 0xff456123, GestureActivity.class));
        mainBeans.add(new MainBean("图片内存等", 0xff456789, MemoryActivity.class));
        mainBeans.add(new MainBean("照片墙", 0xff789456, PhotoWallActivity.class));
        mainBeans.add(new MainBean("瀑布流照片墙", 0xff456123, EasyPhotoWallActivity.class));
        mainBeans.add(new MainBean("二维码识别", 0xff789456, QRActivity.class));
        mainBeans.add(new MainBean("硬盘缓存", 0xff456123, DiskLruCacheActivity.class));
        mainBeans.add(new MainBean("文件和SQL", 0xff123456, FileActivity.class));
        mainBeans.add(new MainBean("Serial", 0xff123456, SerialActivity.class));
        mainBeans.add(new MainBean("Handler", 0xff456789, HandlerActivity.class));
        mainBeans.add(new MainBean("Thread-and-Runnable", 0xff789456, ThreadRunnableActivity.class));

        mainAdapter = new MainAdapter(R.layout.item_main_rv, mainBeans);
        mainAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mainBeans.get(position).activity != null)
                    startActivity(new Intent(MainActivity.this, mainBeans.get(position).activity));

                if (position == 2) {
//                    FloatWindow.get().show();
                    Log.i("dd", "启动service");
//                    startService(new Intent(MainActivity.this, FloatViewService.class));
                    startService(new Intent(MainActivity.this, MyWebSocketService.class));
                }

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
