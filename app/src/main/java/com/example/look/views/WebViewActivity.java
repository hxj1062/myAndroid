package com.example.look.views;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.look.MyBaseActivity;
import com.example.look.R;
import com.example.look.bean.WebData;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class WebViewActivity extends MyBaseActivity {

    private WebView lookWeb;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        buildActionBar();
        lookWeb = findViewById(R.id.wv_web);
        str = readJs("err-upload.js");
        Log.d("io流读取JS文件", str);
        setWeb();
        initView();
    }

    private void initView() {
//        lookWeb.loadUrl("https://ubox.cn");
        lookWeb.addJavascriptInterface(new WebJsBridge(), "ucloud");
        lookWeb.loadUrl("https://adongdev.top");
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWeb() {
        //声明WebSettings子类
        WebSettings webSettings = lookWeb.getSettings();

        //各种内容的渲染需要使用WebChromeClient去实现，所以set一个默认的基类 WebChromeClient
        lookWeb.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    lookWeb.evaluateJavascript("javascript:" + str, new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {

                        }
                    });
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

        });
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

    private String readJs(String fileName) {
        String jsStr = "";
        try {
            InputStream in = getResources().getAssets().open(fileName);
            byte[] arr = new byte[1024];
            ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
            do {
                int numRead = in.read(arr);
                if (numRead <= 0) {
                    break;
                }
                fromFile.write(arr, 0, numRead);
            } while (true);
            jsStr = fromFile.toString();
            in.close();
            fromFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsStr;
    }


    class WebJsBridge {

        @JavascriptInterface
        public void postMessage(String data) {
            WebData manageData = new Gson().fromJson(data, WebData.class);
            if (manageData.getCode() == 50002) {
                Log.d("前端js交互", "webView回调=50002" + manageData.getData());

            } else if (manageData.getCode() == 50003) {
                Log.d("前端js交互", "webView回调=50003" + manageData.getData());

            }
        }
    }
}