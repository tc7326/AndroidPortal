package info.itloser.androidportal.retrofits;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * author：zhaoliangwang on 2019/7/30 20:54
 * email：tc7326@126.com
 */
public interface WanService {

    //retrofit方式
    @GET("friend/json")
    Call<WanFriendBean> friend();

    // rxjava方式
    @GET("friend/json")
    Observable<WanFriendBean> rxfriend();
}