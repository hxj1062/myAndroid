package com.example.look.blue;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.example.look.R;

/**
 * desc: 蓝牙权限
 * <p>
 * Created by hxj
 */
public class BtPsUtil {

    public static final int PERMISSION_REQ = 10;
    public static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String BLUETOOTH = Manifest.permission.BLUETOOTH;
    public static final String BT_ADMIN = Manifest.permission.BLUETOOTH_ADMIN;
    public static final String BT_SCAN = Manifest.permission.BLUETOOTH_SCAN;

    /**
     * desc: 请求权限
     */
    public static void requestPermissions(Activity context, int reqCode, String... permissions) {
        ActivityCompat.requestPermissions(context, permissions, reqCode);
    }

    /**
     * desc: 显示缺失权限提示
     */
    public static void showMissingPermissionDialog(final Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("帮助");
        builder.setMessage(R.string.string_help_text);

        // 拒绝
        builder.setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // 设置
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings(context);
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    /**
     * desc: 启动应用的设置
     */
    private static void startAppSettings(Activity context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    public interface BtPsCallBack {
        void allowProcess(String permission);

        void refuseProcess(String permission);
    }
}
