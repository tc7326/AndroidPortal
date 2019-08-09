package info.itloser.androidportal.retrofits;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author：zhaoliangwang on 2019/7/31 19:23
 * email：tc7326@126.com
 */
class RetrofitServiceManager {

    private static final int DEFAULT_TIME_OUT = 10;//网络请求超时时间
    private static final int DEFAULT_READ_TIME_OUT = 10;//读操作超时时间

    private Retrofit retrofit;

    private RetrofitServiceManager() {

        //创建OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS).readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);

        //添加公共参数拦截器
        BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder().addHeaderParam("token", "aag456as41d6a1fa4g6af4asf4g9").build();
        builder.addInterceptor(basicParamsInterceptor);

        //创建Retrofit
        retrofit = new Retrofit.Builder().baseUrl(ApiConfig.BASE_URL).client(builder.build()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();

    }

    //获取单例RetrofitServiceManager
    static RetrofitServiceManager getInstance() {
        return SingletonHolder.SERVICE_MANAGER;
    }

    static class SingletonHolder {
        static final RetrofitServiceManager SERVICE_MANAGER = new RetrofitServiceManager();

    }

    //获取对应的Service
    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }

}
