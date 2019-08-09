package info.itloser.androidportal.retrofits;

/**
 * author：zhaoliangwang on 2019/8/1 15:24
 * email：tc7326@126.com
 * message：网络请求结果 基类
 */
public class BaseResponse<T> {
    int errorCode;//状态码，0 - 正常
    String errorMsg;//错误信息
    T data;//获取到的数据泛型类

    public boolean isSuccess() {
        return errorCode == 0;
    }
}
