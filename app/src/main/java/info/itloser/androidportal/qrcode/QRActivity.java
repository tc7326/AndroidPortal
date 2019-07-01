package info.itloser.androidportal.qrcode;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.itloser.androidportal.R;

public class QRActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.btn_go)
    Button btnGo;
    @BindView(R.id.cb_light)
    CheckBox cbLight;
    @BindView(R.id.qr_main)
    QRCodeReaderView qrMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        ButterKnife.bind(this);
        initQRCodeReaderView();
    }


    private void initQRCodeReaderView() {
        qrMain.setAutofocusInterval(2000L);
        qrMain.setOnQRCodeReadListener(this);
        qrMain.setBackCamera();
        cbLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                qrMain.setTorchEnabled(isChecked);
            }
        });
        qrMain.startCamera();
    }

    @OnClick({R.id.btn_back, R.id.btn_go})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_go:
                //开启相册
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri originalUri = data.getData();
            Log.i("dd", originalUri + "");
            ContentResolver resolver = getContentResolver();
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                recogQRcode(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (qrMain != null) {
            qrMain.startCamera();
        }
    }//栈顶开启识别

    @Override
    protected void onPause() {
        super.onPause();

        if (qrMain != null) {
            qrMain.stopCamera();
        }
    }//退栈停止识别

    /*
     * 识别成功回调
     * */
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        qrMain.stopCamera();
        if (text.length() > 7) {
            if (text.substring(0, 8).equals("https://") || text.substring(0, 7).equals("http://")) {
                Intent intent = new Intent();
                intent.setData(Uri.parse(text));//Url 就是你要打开的网址
                intent.setAction(Intent.ACTION_VIEW);
                this.startActivity(intent); //启动浏览器
                return;
            }
            //新开个activity
            startActivity(new Intent(QRActivity.this, TextActivity.class).putExtra("TEXT", text));
            return;
        }
        startActivity(new Intent(QRActivity.this, TextActivity.class).putExtra("TEXT", text));
    }

    //识别bitmap是否有二维码
    public void recogQRcode(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] data = new int[width * height];
        bitmap.getPixels(data, 0, width, 0, 0, width, height);    //得到像素
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);   //RGBLuminanceSource对象
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        Result re = null;
        try {
            //得到结果
            re = reader.decode(bitmap1);
        } catch (NotFoundException | ChecksumException | FormatException e) {
            e.printStackTrace();
        }
        //Toast出内容
        assert re != null;
        String text = re.getText();
//        Toast.makeText(QRActivity.this, text, Toast.LENGTH_SHORT).show();

        //利用正则表达式判断内容是否是URL，是的话则打开网页
//        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
//                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";//设置正则表达式
        if (text.length() > 7) {
            if (text.substring(0, 8).equals("https://") || text.substring(0, 7).equals("http://")) {
                Intent intent = new Intent();
                intent.setData(Uri.parse(text));//Url 就是你要打开的网址
                intent.setAction(Intent.ACTION_VIEW);
                this.startActivity(intent); //启动浏览器
                return;
            }
            //新开个activity
            startActivity(new Intent(QRActivity.this, TextActivity.class).putExtra("TEXT", text));
            return;
        }
        startActivity(new Intent(QRActivity.this, TextActivity.class).putExtra("TEXT", text));

    }

}
