package info.itloser.androidportal.memory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import info.itloser.androidportal.R;

/**
 * author：zhaoliangwang on 2019/6/27 14:37
 * email：tc7326@126.com
 */
public class PhotoVallAdapter extends ArrayAdapter<String> implements OnScrollListener {

    private int firstVisibleItem, visibleItemCount;//当前屏幕第一张可见图片的下标，一屏幕最多可显示多少张。

    private Set<BitmapWorkTask> taskCollection;//正在下载和等待下载的任务

    private LruCache<String, Bitmap> memoryCache;//核心类，用于缓存所有下载好的图片，在程序内达到设定值时会将最少最近使用的图片移除掉

    private boolean isFirstEnter = true;

    private GridView photoWall;//gv

    public PhotoVallAdapter(@NonNull Context context, int resource, String[] objects, GridView photoWall) {
        super(context, resource, objects);
        this.photoWall = photoWall;
        taskCollection = new HashSet<BitmapWorkTask>();
        //获取app最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        //设置缓存大小为最大的1/8
        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        photoWall.setOnScrollListener(this);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final String url = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_photo_wall, null);
        } else {
            view = convertView;
        }
        view.setTag(url);//设置Tag保证异步加载图片时顺序不乱
        setImageView(url, (ImageView) view);
        return view;
    }

    /*
     * 把bitmap设置到iv上
     * */
    private void setImageView(String imgUrl, ImageView iv) {
        Bitmap bitmap = getBitmapFromMemoryCache(imgUrl);
        if (bitmap != null) {
            iv.setImageBitmap(bitmap);
        } else {
//            iv.setImageResource(R.drawable.error);
        }
    }

    /*
     * 将图片存入缓存中
     * */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    /*
     * 从缓存中获取一张图片
     * */
    public Bitmap getBitmapFromMemoryCache(String key) {
        return memoryCache.get(key);
    }

    /*
     * 图片异步下载类
     * */
    class BitmapWorkTask extends AsyncTask<String, Void, Bitmap> {

        private String imageUrl;

        @Override
        protected Bitmap doInBackground(String... strings) {
            imageUrl = strings[0];
            //在后台开始下载图片
            Bitmap bitmap = downloadBitmap(strings[0]);
            if (bitmap != null) {
                //图片下载完成后缓存到LrcCache中
                addBitmapToMemoryCache(strings[0], bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //根据Tag找到相应的iv并显示图片
            ImageView imageView = photoWall.findViewWithTag(imageUrl);
            if (imageUrl != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            taskCollection.remove(this);
        }

        /*
         * 图片下载后，直接转成bitmap对象
         * */
        private Bitmap downloadBitmap(String imgUrl) {
            Bitmap bitmap = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(imgUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5 * 1000);
                connection.setReadTimeout(10 * 1000);
                bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return bitmap;
        }


    }

    /*
     * 加载内存中所有课件的bitmap对象，如果没在缓存中，就用异步任务去下载。
     * */
    private void loadBitmaps(int firstVisibleItem, int visibleItemCount) {
        for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++) {
            String imgUrl = Imgs.imageThumbUrls[i];
            Bitmap bitmap = getBitmapFromMemoryCache(imgUrl);
            if (bitmap == null) {
                BitmapWorkTask task = new BitmapWorkTask();
                taskCollection.add(task);
                task.execute(imgUrl);
            } else {
                ImageView imageView = photoWall.findViewWithTag(imgUrl);
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    /*
     * 取消所有正在下载或等待下载的任务
     * */
    public void cancelAllTask() {
        if (taskCollection != null) {
            for (BitmapWorkTask task : taskCollection) {
                task.cancel(false);
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //仅当gv静止时才下载图片，gv滑动时取消下载
        if (scrollState == SCROLL_STATE_IDLE) {
            loadBitmaps(firstVisibleItem, visibleItemCount);
        } else {
            cancelAllTask();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        this.visibleItemCount = visibleItemCount;

        //首次进入调用一次下载
        if (isFirstEnter && visibleItemCount > 0) {
            loadBitmaps(firstVisibleItem, visibleItemCount);
            isFirstEnter = false;
        }

    }

}