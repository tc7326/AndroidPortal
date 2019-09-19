package info.itloser.androidportal.viewmodel.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * author：zhaoliangwang on 2019/9/18 14:57
 * email：tc7326@126.com
 */

@Dao
public interface NewsDao {

    @Query("SELECT * FROM NEWS")
    List<News> queryAll();

    @Query("SELECT 1 FROM NEWS WHERE NEWS_ID = :id")
    News queryById(long id);

    @Insert
    void insert(News... news);

    @Update
    void update(News... news);

    @Delete
    void delete(News... news);


}
