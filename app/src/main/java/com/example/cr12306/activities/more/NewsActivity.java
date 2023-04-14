package com.example.cr12306.activities.more;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cr12306.R;

public class NewsActivity extends AppCompatActivity {

    public ImageButton back_news;
    public ImageButton renew_news, goBack_news, goForward_news;
    public WebView webView_news;

    public Intent intent_news = new Intent();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        initViews();

        webView_news.loadUrl("http://www.china-railway.com.cn/");
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webView_news.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
                view.loadUrl(url);
                //返回true
                return true;
            }
        });


    }

    private void initViews() {
        back_news = findViewById(R.id.back_news);
        webView_news = findViewById(R.id.webView_news);
        renew_news = findViewById(R.id.renew_news);
        goForward_news = findViewById(R.id.go_forward_news);
        goBack_news = findViewById(R.id.go_back_news);

        back_news.setOnClickListener(v -> {
            intent_news.setClass(NewsActivity.this, SettingsActivity.class);
            startActivity(intent_news);
            NewsActivity.this.finish();
        });
        renew_news.setOnClickListener(v -> {
            webView_news.reload();
        });
        goBack_news.setOnClickListener(v -> webView_news.goBack());
        goForward_news.setOnClickListener(v -> webView_news.goForward());
    }


}
