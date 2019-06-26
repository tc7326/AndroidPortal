package info.itloser.androidportal.memory;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import info.itloser.androidportal.R;

public class MemoryActivity extends AppCompatActivity {

    private static final String TAG = "MemoryActivity";

    private LruCache<String, Bitmap> memoryCache;

    private ImageView imageView;

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            //计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            //选择宽和高中最小的比率作为inSampleSize的值，保证最终目标图标的宽和高
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        Log.i(TAG, "inSampleSize：" + inSampleSize);
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        //第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//先不分配内存
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);//得到缩放倍数
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        imageView = findViewById(R.id.tv_fuck);

        //获取屏幕dpi，x和y相对有点差距
        float xdpi = getResources().getDisplayMetrics().xdpi;
        float ydpi = getResources().getDisplayMetrics().ydpi;

        Log.i(TAG, "x：" + xdpi + "|y：" + ydpi);

        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);//获取可用内存的最大值，超过此值，即OOM

        //一个app最高可用内存
        Log.d(TAG, "Max memory is " + maxMemory + "KB");
        Log.d(TAG, "Max memory is " + maxMemory / 1024 + "MB");

        int cacheSize = maxMemory / 8;

        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;// 重写此方法来衡量每张图片的大小，默认返回图片数量。
            }
        };

        loadBitmap(R.mipmap.ic_long, imageView);//通过刚才一些列代码加载

    }

    public void addLruCacheBitmap(String key, Bitmap img) {
        if (getLruCacheBitmap(key) == null) {
            memoryCache.put(key, img);
        }
    }

    public Bitmap getLruCacheBitmap(String key) {
        return memoryCache.get(key);
    }

    public void loadBitmap(int resId, ImageView imageView) {
        final String imageKey = String.valueOf(resId);
        Bitmap bitmap = getLruCacheBitmap(imageKey);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.mipmap.ic_launcher);
            BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            task.execute(resId);
        }
    }

    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        private ImageView imageView;

        BitmapWorkerTask(ImageView imageView) {
            this.imageView = imageView;
        }

        //在后台加载图片
        @Override
        protected Bitmap doInBackground(Integer... integers) {
            final Bitmap bitmap = decodeSampledBitmapFromResource(getResources(), integers[0], 100, 100);
            addLruCacheBitmap(String.valueOf(integers[0]), bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }


}
