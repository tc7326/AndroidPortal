package info.itloser.androidportal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UmengShareActivity extends AppCompatActivity {

    Activity context;
    UMShareListener umShareListener;
    @BindView(R.id.btn_share)
    Button btnShare;
    @BindView(R.id.iv_long)
    ImageView ivLong;

    public static Context sContext;//内存泄漏测试用



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umeng_share);
        ButterKnife.bind(this);

        context = UmengShareActivity.this;

        sContext = this;//内存泄漏测试用

        Glide.with(this).load(R.mipmap.ic_long).into(ivLong);


        //分享成功/失败回调监听
        umShareListener = new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {
                Toast.makeText(context, "开始分享", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(context, "分享取消", Toast.LENGTH_SHORT).show();
            }
        };


        findViewById(R.id.btn_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final UMImage image = new UMImage(context, R.mipmap.ic_launcher);//分享图标


                new ShareAction(context)
                        .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA)
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                new ShareAction(context).setPlatform(share_media).withMedia(image).setCallback(umShareListener).share();
                            }
                        }).open();
            }
        });


    }
}
