package com.example.look.views;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.look.R;


public class BroadcastTestActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String MY_ACTION = "hello world";
    private BroadcastTestReceiver receiverDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        findViewId();
        registerBroadCast();
    }

    private void findViewId() {
        findViewById(R.id.btn_zhuce).setOnClickListener(this); // 注册发送广播
        findViewById(R.id.btn_cancel).setOnClickListener(this); // 注销广播
        findViewById(R.id.btn_back).setOnClickListener(this); // 返回
    }

    private void registerBroadCast() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MY_ACTION);

        receiverDemo = new BroadcastTestReceiver();
        registerReceiver(receiverDemo, intentFilter);
    }


    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.btn_zhuce:
                    Intent intent = new Intent(MY_ACTION);
                    intent.putExtra("demo", "广播测试");
                    sendBroadcast(intent);
                    break;
                case R.id.btn_cancel:
                    break;
                case R.id.btn_back:
                    finish();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiverDemo != null) {
            unregisterReceiver(receiverDemo);
        }
    }
}
