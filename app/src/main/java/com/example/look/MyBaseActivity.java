package com.example.look;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

/**
 * desc: 基础Activity
 * <p>
 * Created by hxj
 */
public class MyBaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void buildActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * desc: Snackbar是一种轻量级的通知组件，通常用于在应用程序的底部显示短期的、
     * 非持久性的消息或用户反馈。Snackbar可以从底部滑入屏幕，显示一条消息
     */
    protected void showSnack(View view, CharSequence text) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
    }

    /**
     * desc: 创建Toast
     *
     * @param content 文案
     * @param tag     0-大约2秒 1-大约3.5秒
     */
    public void showToast(String content, int tag) {
        int duration;
        if (tag == 0) {
            duration = Toast.LENGTH_SHORT; // 大约2秒
        } else {
            duration = Toast.LENGTH_LONG; // 大约3.5秒
        }
        Toast toast = new Toast(this);
        TextView textView = new TextView(this);
        textView.setText(content);
        textView.setBackground(getResources().getDrawable(R.drawable.shape_toast_bg));
        textView.setTextColor(getResources().getColor(R.color.color_FFFFFF));
        textView.setPadding(18, 18, 18, 18);
        toast.setView(textView);
        toast.setDuration(duration);
        toast.show();
    }


    // 含有全部的权限
    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED;
    }


    private long time = 0;

    protected void exitApp() {
        int millis = 2000;
        if (System.currentTimeMillis() - time > millis) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            time = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}

