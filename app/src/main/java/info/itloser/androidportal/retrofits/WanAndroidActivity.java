package info.itloser.androidportal.retrofits;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.itloser.androidportal.R;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class WanAndroidActivity extends AppCompatActivity {

    final static String WAN_ANDROID_URL = "https://www.wanandroid.com/";

    HomeListAdapter adapter;
    List<WanFriendBean.DataBean> datas = new ArrayList<>();
    @BindView(R.id.rv_wan_android)
    RecyclerView rvWanAndroid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dou_ban_top250);
        ButterKnife.bind(this);
        adapter = new HomeListAdapter(this, datas);
        rvWanAndroid.setLayoutManager(new LinearLayoutManager(this));
        rvWanAndroid.setAdapter(adapter);

        //just do something
        packageRetrofit();

    }

    /*
     * OkHttpClient配置
     * */
    public void initRetrofit() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.callTimeout(5, TimeUnit.SECONDS);//网络超时
        builder.writeTimeout(5, TimeUnit.SECONDS);//写入超时
        builder.readTimeout(5, TimeUnit.SECONDS);//读取超时

        //一个拦截器工具类
        BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder().addHeaderParam("token", "fuck").addParam("userid", "fuckfuckfuck").build();
        //添加拦截器
        builder.addInterceptor(basicParamsInterceptor);

        new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(WAN_ANDROID_URL)
                .build();

    }

    /*
     * retrofit 方式
     * */
    public void retrofit() {
        new Retrofit.Builder().baseUrl(WAN_ANDROID_URL).addConverterFactory(GsonConverterFactory.create()).build().create(WanService.class).friend().enqueue(new Callback<WanFriendBean>() {
            @Override
            public void onResponse(@NonNull Call<WanFriendBean> call, @NonNull Response<WanFriendBean> response) {
                Log.i("retrofit", "Response");
                assert response.body() != null;
                Log.i("retrofit", Arrays.toString(response.body().getData().toArray()));
            }

            @Override
            public void onFailure(@NonNull Call<WanFriendBean> call, @NonNull Throwable t) {
                Log.i("retrofit", "Failure");

            }
        });
    }

    /*
     * rxjava 方式
     * */
    public void rxjava() {
        new Retrofit.Builder().baseUrl(WAN_ANDROID_URL).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build().create(WanService.class).rxfriend().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<WanFriendBean>() {
            @Override
            public void onCompleted() {
                Log.i("rxjava", "Completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("rxjava", "Error");
            }

            @Override
            public void onNext(WanFriendBean wanFriendBean) {
                Log.i("rxjava", "Next");
                Log.i("rxjava", Arrays.toString(wanFriendBean.getData().toArray()));
            }
        });
    }

    /*
     * 以封装的形式Retrofit
     * */
    public void packageRetrofit() {
        WanLoader wanLoader = new WanLoader();
        wanLoader.getFriend().subscribe(new Action1<List<WanFriendBean.DataBean>>() {
            @Override
            public void call(List<WanFriendBean.DataBean> dataBeans) {
                Log.i("package", "Next");
                Log.i("package", Arrays.toString(dataBeans.toArray()));
                //适配数据
                datas.addAll(dataBeans);
                adapter.notifyDataSetChanged();

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i("package", "Error" + throwable.getMessage());
            }
        });
    }


}