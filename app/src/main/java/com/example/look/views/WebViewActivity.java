package com.example.look.views;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.look.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView lookWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        lookWeb = findViewById(R.id.wv_web);
        setWeb();
        initView();
    }

    private void initView() {
        lookWeb.loadUrl("file:///android_res/raw/tokentest.html");
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWeb() {
        //声明WebSettings子类
        WebSettings webSettings = lookWeb.getSettings();

        //各种内容的渲染需要使用WebChromeClient去实现，所以set一个默认的基类 WebChromeClient
        lookWeb.setWebChromeClient(new WebChromeClient());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true);  // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true);//支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true);    //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false);   //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);   //webview缓存默认
        webSettings.setAllowFileAccess(true);//设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);   //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);   //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");  //设置编码格式
        webSettings.setDomStorageEnabled(true);  // 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);    //开启 database storage API 功能
        // webSettings.setAppCacheEnabled(true)//开启 Application Caches 功能
        webSettings.setAllowUniversalAccessFromFileURLs(true);
    }
}