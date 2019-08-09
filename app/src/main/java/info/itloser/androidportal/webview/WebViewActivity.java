package info.itloser.androidportal.webview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.itloser.androidportal.R;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.tv_close)
    TextView tvClose;
    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_forward)
    TextView tvForward;
    @BindView(R.id.wv_main)
    WebView wvMain;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        wvMain.setWebViewClient(new WebViewClient());//防止跳转浏览器
        wvMain.getSettings().setUseWideViewPort(true); // 关键点
        wvMain.getSettings().setAllowFileAccess(true); // 允许访问文件
        wvMain.getSettings().setSupportZoom(true); // 支持缩放
        wvMain.getSettings().setLoadWithOverviewMode(true);
        wvMain.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
        wvMain.getSettings().setJavaScriptEnabled(true);//启用js
        wvMain.loadUrl(getIntent().getStringExtra("URL"));


    }

    @OnClick({R.id.tv_close, R.id.tv_refresh, R.id.tv_back, R.id.tv_forward})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_close:
                wvMain.destroy();
                finish();
                break;
            case R.id.tv_refresh:
                break;
            case R.id.tv_back:
                break;
            case R.id.tv_forward:
                break;
        }
    }
}
