package info.itloser.androidportal.retrofits;

/**
 * author：zhaoliangwang on 2019/8/1 19:52
 * email：tc7326@126.com
 */
public class Fault extends RuntimeException {

    private static final long serialVersionUID = -845502700043160164L;
    private int errorCode;

    public int getErrorCode() {
        return errorCode;
    }

    Fault(int errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }


}
