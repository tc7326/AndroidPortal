package info.itloser.androidportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
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
import butterknife.ButterKnife;
import info.itloser.androidportal.bitmaps.ViewToBitmapActivity;
import info.itloser.androidportal.calendarProviders.CalendarTaskActivity;
import info.itloser.androidportal.components.goactivity.GoSingleInstanceActivity;
import info.itloser.androidportal.file_sql.FileActivity;
import info.itloser.androidportal.file_sql.SerialActivity;
import info.itloser.androidportal.memory.DiskLruCacheActivity;
import info.itloser.androidportal.memory.EasyPhotoWallActivity;
import info.itloser.androidportal.memory.MemoryActivity;
import info.itloser.androidportal.memory.PhotoWallActivity;
import info.itloser.androidportal.qrcode.QRActivity;
import info.itloser.androidportal.retrofits.WanAndroidActivity;
import info.itloser.androidportal.rxjavas.RxJavaActivity;
import info.itloser.androidportal.socket.MyWebSocketService;
import info.itloser.androidportal.statusbar.StatusBarActivity;
import info.itloser.androidportal.threads.HandlerActivity;
import info.itloser.androidportal.threads.SynchronizedActivity;
import info.itloser.androidportal.threads.ThreadRunnableActivity;
import info.itloser.androidportal.threads.ThreadsWaitActivity;
import info.itloser.androidportal.threads.VideoWaitActivity;
import info.itloser.androidportal.viewmodel.VMActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_main_list)
    RecyclerView rvMainList;

    List<MainBean> mainBeans = new ArrayList<>();
    MainAdapter mainAdapter;

    @BindView(R.id.dl_main)
    DrawerLayout dlMain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        StatusBarUtil.setTranslucentForDrawerLayout(this, dlMain, 0);

        mainBeans.add(new MainBean("d", 0xFFF5C71E, GoSingleInstanceActivity.class));
        mainBeans.add(new MainBean("日历", 0xffff4081, RiLiActivity.class));
        mainBeans.add(new MainBean("悬浮框", 0xff789456, null));
        mainBeans.add(new MainBean("友盟分享/登录", 0xffff4081, UmengShareActivity.class));
        mainBeans.add(new MainBean("手势监听", 0xff456123, GestureActivity.class));
        mainBeans.add(new MainBean("图片内存等", 0xff456789, MemoryActivity.class));
        mainBeans.add(new MainBean("照片墙", 0xff789456, PhotoWallActivity.class));
        mainBeans.add(new MainBean("瀑布流照片墙", 0xffff4081, EasyPhotoWallActivity.class));
        mainBeans.add(new MainBean("二维码识别", 0xFFF5C71E, QRActivity.class));
        mainBeans.add(new MainBean("硬盘缓存", 0xff456123, DiskLruCacheActivity.class));
        mainBeans.add(new MainBean("文件和SQL", 0xffff4081, FileActivity.class));
        mainBeans.add(new MainBean("Serial", 0xff123456, SerialActivity.class));
        mainBeans.add(new MainBean("Handler", 0xFFF5C71E, HandlerActivity.class));
        mainBeans.add(new MainBean("Thread-and-Runnable", 0xff789456, ThreadRunnableActivity.class));
        mainBeans.add(new MainBean("日历事件", 0xff456789, CalendarTaskActivity.class));
        mainBeans.add(new MainBean("view保存bitmap", 0xffff4081, ViewToBitmapActivity.class));
        mainBeans.add(new MainBean("Synchronized锁", 0xFFF5C71E, SynchronizedActivity.class));
        mainBeans.add(new MainBean("wait、notify、notifyAll", 0xff789456, ThreadsWaitActivity.class));
        mainBeans.add(new MainBean("CountDownLatch多线程并发", 0xff123456, VideoWaitActivity.class));
        mainBeans.add(new MainBean("Retrofit+OkHttp", 0xFFF5C71E, WanAndroidActivity.class));
        mainBeans.add(new MainBean("RxJava", 0xffff4081, RxJavaActivity.class));
        mainBeans.add(new MainBean("StatusBar", 0xff456789, StatusBarActivity.class));
        mainBeans.add(new MainBean("LiveData+ViewModel", 0xFFF5C71E, VMActivity.class));

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

//        startService(new Intent(this, VVService.class));//websocket测试

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

    @Override
    protected void onResume() {
        super.onResume();
//        TopToast.makeText(this, "TopToast测试", Toast.LENGTH_SHORT).show();
        Log.i("dd", "SnackBar");
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);//隐藏状态栏


    }

}
