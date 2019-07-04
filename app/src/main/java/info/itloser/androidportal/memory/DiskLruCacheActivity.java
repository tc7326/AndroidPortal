package info.itloser.androidportal.memory;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.itloser.androidportal.R;

public class DiskLruCacheActivity extends AppCompatActivity {

    DiskLruCache diskLruCache;
    @BindView(R.id.iv_main)
    ImageView ivMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disk_lru_cache);
        ButterKnife.bind(this);

        try {
            File cacheDir = getDiskCacheDir(this, "bitmap");
            if (cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            diskLruCache = DiskLruCache.open(cacheDir, getAppVersion(this), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * 开个线程将图片写入缓存
         * */
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                    String imageUrl = "https://img-my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
//                    String key = hashKeyForDisk(imageUrl);
//                    DiskLruCache.Editor editor = diskLruCache.edit(key);
//                    if (editor != null) {
//                        OutputStream outputStream = editor.newOutputStream(0);
//                        if (downloadUrlToStream(imageUrl, outputStream)) {
//                            editor.commit();
//                        } else {
//                            editor.abort();
//                        }
//                    }
//                    diskLruCache.flush();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();

        /*
         * 读取缓存
         * */
//        try {
//            String imageUrl = "https://img-my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
//            String key = hashKeyForDisk(imageUrl);
//            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
//            if (snapshot != null) {
//                InputStream is = snapshot.getInputStream(0);
//                Bitmap bitmap = BitmapFactory.decodeStream(is);
//                ivMain.setImageBitmap(bitmap);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        /*
         * 移除缓存
         * */
//        try {
//            String imageUrl = "https://img-my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
//            String key = hashKeyForDisk(imageUrl);
//            diskLruCache.remove(key);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        /*
         * other API
         * */

        //获取app缓存的总大小（如手动清理缓存显示缓存的大小）
//        diskLruCache.size();

        //将内存中的操作记录同步到日志文件
//        diskLruCache.flush();

        //关闭diskLruCache对应open，关闭后不能再写入缓存
//        diskLruCache.close();

        //删除所有缓存（如手动清理缓存）
//        diskLruCache.delete();

    }

    /*
     * 两个工具类（获取两种缓存路径，有sd卡和没sd卡两种）
     * */
    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /*
     * 获取版本号
     * */
    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /*
     * 下载一张图片
     * */
    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /*
     * 将字符串进行MD5编码
     * */
    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
