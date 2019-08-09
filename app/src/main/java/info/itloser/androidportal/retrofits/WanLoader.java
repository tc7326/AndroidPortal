package info.itloser.androidportal.retrofits;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * author：zhaoliangwang on 2019/8/1 10:56
 * email：tc7326@126.com
 */
public class WanLoader extends ObjectLoader {
    private WanService wanService;

    public WanLoader() {
        this.wanService = RetrofitServiceManager.getInstance().create(WanService.class);
    }

    public Observable<List<WanFriendBean.DataBean>> getFriend() {
        return observe(wanService.rxfriend()).map(new Func1<WanFriendBean, List<WanFriendBean.DataBean>>() {
            @Override
            public List<WanFriendBean.DataBean> call(WanFriendBean wanFriendBean) {
                return wanFriendBean.getData();
            }
        });
    }

}
