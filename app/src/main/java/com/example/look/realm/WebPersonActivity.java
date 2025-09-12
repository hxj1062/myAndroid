package com.example.look.realm;


import static com.example.look.realm.WBH5FaceVerifySDK.VIDEO_REQUEST;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.look.R;

/**
 * desc: 掉起实名认证
 * <p>
 * Created by hxj
 */
public class WebPersonActivity extends Activity {
    private static final String TAG = "WebPersonActivity";
    private WebView mWebView;
    private static final int PERMISSION_QUEST_TRTC_CAMERA_VERIFY = 12;//trtc模式的权限申请
    private static final int PERMISSION_QUEST_OLD_CAMERA_VERIFY = 11;//录制模式的权限申请
    private H5FaceWebChromeClient webViewClient;
    private boolean belowApi21;// android 5.0以下系统

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_person);
        initWebView();
        LinearLayout leftBackLl = (LinearLayout) findViewById(R.id.main_left_button);
        leftBackLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    WebPersonActivity.this.finish();
                }

            }
        });
    }


    private void initWebView() {
        mWebView = findViewById(R.id.main_webview);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = view.getUrl();
                Log.d(TAG, "wbtest shouldOverrideUrlLoading():" + url);
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(TAG, "wbtest onPageStarted():" + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "wbtest onPageFinished():" + url);
            }


            @Override
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
                Log.e(TAG, "webview访问网址ssl证书无效！询问客户");
                handler.proceed();
                final AlertDialog.Builder builder = new AlertDialog.Builder(WebPersonActivity.this);
                builder.setMessage("当前页面证书不可信，是否继续访问?");
                builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.proceed();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.cancel();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        webViewClient = new H5FaceWebChromeClient(WebPersonActivity.this);
        mWebView.setWebChromeClient(webViewClient);
        WBH5FaceVerifySDK.getInstance().setWebViewSettings(mWebView, getApplicationContext());
        mWebView.loadUrl("file:///android_asset/web/identify_face.html");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult --------" + requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIDEO_REQUEST) {//收到录制模式调用系统相机录制完成视频的结果
            Log.d(TAG, "onActivityResult recordVideo");
            if (WBH5FaceVerifySDK.getInstance().receiveH5FaceVerifyResult(requestCode, resultCode, data)) {
                return;
            }
        } else if (requestCode == PERMISSION_QUEST_TRTC_CAMERA_VERIFY) {
            Log.d(TAG, "onActivityResult camera");
            requestCameraPermission(true, belowApi21);
        } else if (requestCode == PERMISSION_QUEST_OLD_CAMERA_VERIFY) {
            Log.d(TAG, "onActivityResult cameraAndSome");
            requestCameraPermission(false, belowApi21);
        }


    }

    /**
     * 针对trtc录制模式，申请相机权限
     */
    public void requestCameraPermission(boolean trtc, boolean belowApi21) {
        this.belowApi21 = belowApi21;
        if (checkSdkPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "checkSelfPermission false");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {  //23+的情况
                if (this.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    //用户之前拒绝过，这里返回true
                    Log.d(TAG, "shouldShowRequestPermissionRationale true");
                    if (trtc) {
                        this.requestPermissions(
                                new String[]{Manifest.permission.CAMERA},
                                PERMISSION_QUEST_TRTC_CAMERA_VERIFY);
                    } else {
                        this.requestPermissions(
                                new String[]{Manifest.permission.CAMERA},
                                PERMISSION_QUEST_OLD_CAMERA_VERIFY);
                    }
                } else {
                    Log.d(TAG, "shouldShowRequestPermissionRationale false");
                    if (trtc) {
                        this.requestPermissions(
                                new String[]{Manifest.permission.CAMERA},
                                PERMISSION_QUEST_TRTC_CAMERA_VERIFY);
                    } else {
                        this.requestPermissions(
                                new String[]{Manifest.permission.CAMERA},
                                PERMISSION_QUEST_OLD_CAMERA_VERIFY);
                    }

                }
            } else {
                if (trtc) {
                    //23以下没法系统弹窗动态申请权限，只能用户跳转设置页面，自己打开
                    openAppDetail(PERMISSION_QUEST_TRTC_CAMERA_VERIFY);
                } else {
                    //23以下没法系统弹窗动态申请权限，只能用户跳转设置页面，自己打开
                    openAppDetail(PERMISSION_QUEST_OLD_CAMERA_VERIFY);
                }
            }

        } else {
            Log.d(TAG, "checkSelfPermission true");
            if (trtc) {
                webViewClient.enterTrtcFaceVerify();
            } else {
                webViewClient.enterOldModeFaceVerify(belowApi21);
            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_QUEST_TRTC_CAMERA_VERIFY: // trtc 模式，新模式。
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "onRequestPermissionsResult grant");
                        webViewClient.enterTrtcFaceVerify();
                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED
                            && this.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) == false) {
                        Log.d(TAG, "onRequestPermissionsResult deny");
                        openAppDetail(PERMISSION_QUEST_TRTC_CAMERA_VERIFY);
                    } else {
                        Log.d(TAG, "拒绝权限并且之前没有点击不再提醒");
                        //权限被拒绝
                        askPermissionError();
                    }
                }
                break;
            case PERMISSION_QUEST_OLD_CAMERA_VERIFY://录制模式，老模式。
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "PERMISSION_QUEST_CAMERA_RECORD_VERIFY GRANTED ");
                        webViewClient.enterOldModeFaceVerify(belowApi21);
                    } else {
                        if (this.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) == false) {
                            Toast.makeText(WebPersonActivity.this, "请前往设置->应用->权限中打开相机权限，否则功能无法正常运行", Toast.LENGTH_LONG).show();
                            //权限被拒绝
                            openAppDetail(PERMISSION_QUEST_OLD_CAMERA_VERIFY);
                        } else {
                            Log.d(TAG, "onRequestPermissionsResult  camera deny");
                            askPermissionError();
                        }
                    }
                }
                break;
        }
    }

    private void openAppDetail(int requestCode) {
        showWarningDialog(requestCode);
    }


    private void enterSettingActivity(int requestCode) {
        //部分插件化框架中用Activity.getPackageName拿到的不一定是宿主的包名，所以改用applicationContext获取
        String packageName = getApplicationContext().getPackageName();
        Uri uri = Uri.fromParts("package", packageName, null);
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
        ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent, 0);
        if (resolveInfo != null) {
            startActivityForResult(intent, requestCode);
        }
    }


    private void askPermissionError() {
        Toast.makeText(WebPersonActivity.this, "用户拒绝了权限,5秒后按钮可再点击", Toast.LENGTH_SHORT).show();
        WBH5FaceVerifySDK.getInstance().resetReceive();
    }


    AlertDialog dialog;

    private void showWarningDialog(final int requestCode) {
        dialog = new AlertDialog.Builder(this)
                .setTitle("权限申请提示")
                .setMessage("请前往设置->应用->权限中打开相关权限，否则功能无法正常运行！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // 一般情况下如果用户不授权的话，功能是无法运行的，做退出处理,合作方自己根据自身产品决定是退出还是停留
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        dialog = null;
                        enterSettingActivity(requestCode);

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        dialog = null;
                        WBH5FaceVerifySDK.getInstance().resetReceive();

                    }
                }).setCancelable(false).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        }
    }

    private int checkSdkPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionResult = this.checkSelfPermission(permission);
            Log.d(TAG, "checkSdkPermission >=23 " + permissionResult + " permission=" + permission);
            return permissionResult;
        } else {
            int permissionResult = getPackageManager().checkPermission(permission, getPackageName());
            Log.d(TAG, "checkSdkPermission <23 =" + permissionResult + " permission=" + permission);
            return permissionResult;
        }
    }
}

