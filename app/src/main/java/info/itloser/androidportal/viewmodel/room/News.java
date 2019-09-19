package info.itloser.androidportal.viewmodel.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * author：zhaoliangwang on 2019/9/18 14:22
 * email：tc7326@126.com
 */

@Entity(tableName = "NEWS")
class News {

    @PrimaryKey
    @ColumnInfo(name = "NEWS_ID")
    long newsId;//文章id

    @ColumnInfo(name = "NEWS_TITLE")
    String newsTitle;//文章标题

    @ColumnInfo(name = "NEWS_URL")
    String newsUrl;//文章地址

    @ColumnInfo(name = "NEWS_STARS")
    long newsStars;//文章收藏数


}