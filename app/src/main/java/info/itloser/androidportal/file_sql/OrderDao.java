package info.itloser.androidportal.file_sql;

import android.content.Context;

/**
 * author：zhaoliangwang on 2019/7/10 14:41
 * email：tc7326@126.com
 * 在OrderDao处理所有数据库操作
 */
public class OrderDao {
    private Context context;
    private OrderDBHelper orderDBHelper;

    public OrderDao(Context context) {
        this.context = context;
        this.orderDBHelper = new OrderDBHelper(context);
    }

    /*
     * 新增一条数据
     * */
    boolean addOneMsg() {
        //

        return false;
    }


}
