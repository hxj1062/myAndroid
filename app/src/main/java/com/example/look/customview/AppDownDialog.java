package com.example.look.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.look.R;

import java.util.Objects;

/**
 * desc: App下架弹窗
 * <p>
 */
public class AppDownDialog extends Dialog {

    private WebView wvPage;

    public AppDownDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_app_down);
        wvPage = findViewById(R.id.wv_page);
        initWebView();
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = Objects.requireNonNull(getWindow()).getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);
        ImageView ivNoticeCancel = findViewById(R.id.iv_down_cancel);
        ivNoticeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        wvPage.loadUrl("file:///android_asset/web/app_down.html");
    }

    private void initWebView() {
        WebSettings webSettings = wvPage.getSettings();
        webSettings.setJavaScriptEnabled(true);     //支持JS
        webSettings.setDomStorageEnabled(true);       //启用dom内存，防止js加载失败
        webSettings.setAllowFileAccess(true);       //允许访问文件
        webSettings.setSupportZoom(true);       //支持缩放
        webSettings.setLoadWithOverviewMode(true);      //是否启动概述模式浏览界面，当页面宽度超过WebView显示宽度时，缩小页面适应WebView。默认false
        webSettings.setGeolocationEnabled(false);       //是否允许定位
        webSettings.setLoadsImagesAutomatically(true);      //是否加载图片
//      webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);     //设置缓存模式
//      webSettings.setDefaultTextEncodingName("UTF-8");        //设置页面的编码格式，默认UTF-8
    }
}
