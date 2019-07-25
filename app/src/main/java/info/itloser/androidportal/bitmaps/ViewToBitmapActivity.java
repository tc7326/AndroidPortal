package info.itloser.androidportal.bitmaps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.itloser.androidportal.R;

public class ViewToBitmapActivity extends AppCompatActivity {

    @BindView(R.id.tv_0)
    TextView tv0;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.cl_main)
    ConstraintLayout clMain;

    Context context;

    //笔记：
    //当VIEW.GONE起来时，是拿不到Bitmap的。
    //当VIEW.INVISIBLE时，可以拿到Bitmap。
    //默认是不带透明度通道的。具体还不会

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_to_bitmap);
        ButterKnife.bind(this);
        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();


        //保存图片
        clMain.setDrawingCacheEnabled(true);
        clMain.buildDrawingCache();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Bitmap bmp = clMain.getDrawingCache(); // 获取图片
                long nowTime = new Date().getTime();

                savePicture(bmp, nowTime + ".png");// 保存图片
                clMain.destroyDrawingCache(); // 保存过后释放资源
            }
        }, 1000);

    }

    public void savePicture(Bitmap bm, String fileName) {
        Log.i("xing", "savePicture: ------------------------");
        if (null == bm) {
            Log.i("xing", "savePicture: ------------------图片为空------");
            return;
        }
        //建立指定文件夹
        File foder = new File(Environment.getExternalStorageDirectory(), "zzp_sale");
        if (!foder.exists()) {
            foder.mkdirs();
        }
        File myCaptureFile = new File(foder, fileName);
        try {
            if (!myCaptureFile.exists()) {
                myCaptureFile.createNewFile();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            //压缩保存到本地
            bm.compress(Bitmap.CompressFormat.JPEG, 90, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    myCaptureFile.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + myCaptureFile.getPath())));

        Toast.makeText(context, "保存成功!", Toast.LENGTH_SHORT).show();

    }
}
