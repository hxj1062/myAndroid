package com.example.look.scan;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.example.look.MyBaseActivity;
import com.example.look.R;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

public class ScanCodeActivity extends MyBaseActivity implements CompoundBarcodeView.TorchListener, ScanCodeManager.CallbackListener {

    private ScanCodeManager capture;
    private CompoundBarcodeView barcodeScannerView;
    private boolean lighting = false; // 灯光

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        buildActionBar();
        initView();
        capture = new ScanCodeManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.setCallbackListener(this);
        capture.decode();
    }

    private void initView() {
        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner);
        barcodeScannerView.setTorchListener(this);
        if (!hasFlash()) {
            findViewById(R.id.barcode_light).setVisibility(View.GONE);
        }
    }


    @Override
    public void scanResult(BarcodeResult result) {
        String scanResultData = result.getText();
        Log.d("扫码结果值=", scanResultData);

        restartScan.sendEmptyMessageDelayed(0, 2000);
    }


    @SuppressLint("HandlerLeak")
    private Handler restartScan = new Handler() {
        public void handleMessage(Message msg) {
            if (!ScanCodeActivity.this.isFinishing()) {
                capture.onResume();
                capture.decode();
            }
        }
    };


    /**
     * desc: 判断手机受否有闪光灯
     */
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    /**
     * desc: 灯光开关
     */
    public void switchFlashlight(View view) {
        if (!lighting) {
            barcodeScannerView.setTorchOn();
            lighting = true;
        } else {
            barcodeScannerView.setTorchOff();
            lighting = false;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public void onTorchOn() {
    }

    @Override
    public void onTorchOff() {
    }
}
