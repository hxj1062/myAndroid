package com.example.look.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.look.MyBaseActivity;
import com.example.look.R;

public class UITreadActivity extends MyBaseActivity implements View.OnClickListener {

    TextView textView;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.obj != null) {
                textView.setText(msg.obj.toString());
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_i_tread);
        buildActionBar();
        findViewById(R.id.btn_download).setOnClickListener(this);
        textView = findViewById(R.id.tv_txt1);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_download) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message ms = handler.obtainMessage();
                    ms.obj = "下载完成";
                    handler.sendMessage(ms);
                }
            }).start();
        }
    }
}