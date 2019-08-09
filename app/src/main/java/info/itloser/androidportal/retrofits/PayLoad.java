package info.itloser.androidportal.retrofits;

import android.util.Log;

import rx.functions.Func1;

/**
 * author：zhaoliangwang on 2019/8/1 16:07
 * email：tc7326@126.com
 */
public class PayLoad<T> implements Func1<BaseResponse<T>, T> {

    @Override
    public T call(BaseResponse<T> tBaseResponse) {
        if (!tBaseResponse.isSuccess()) {
            //请求失败，抛给上层
            Log.i("dd", "请求失败");
            throw new Fault(tBaseResponse.errorCode, tBaseResponse.errorMsg);
        }
        return tBaseResponse.data;
    }
}
