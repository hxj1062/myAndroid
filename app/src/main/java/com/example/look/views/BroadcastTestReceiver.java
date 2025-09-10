package com.example.look.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.look.utils.CommonUtils;

public class BroadcastTestReceiver extends BroadcastReceiver {

    public static final String MY_ACTION = "hello world";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (MY_ACTION.equals(intent.getAction())) {
            Toast.makeText(context, intent.getStringExtra("demo"), Toast.LENGTH_SHORT).show();
        }
    }
}
