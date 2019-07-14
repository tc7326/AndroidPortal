package info.itloser.androidportal.file_sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * author：zhaoliangwang on 2019/7/9 11:06
 * email：tc7326@126.com
 */
public class OrderDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "myTest.db";
    private static final String TABLE_NAME = "Orders";

    public OrderDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public OrderDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创库
        String sql = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME +
                " (Id INTEGER PRIMARY KEY, CustomName TEXT, OrderPrice INTEGER, Country TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //升级库
        String sql = "DROP TABLE IF EXISTS " +
                TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }




}
