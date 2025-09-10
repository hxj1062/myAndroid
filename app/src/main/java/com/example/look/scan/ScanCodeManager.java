package com.example.look.scan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Window;

import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.google.zxing.client.android.InactivityTimer;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CameraPreview;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ScanCodeManager {
    private static final String TAG = ScanCodeManager.class.getSimpleName();
    private Activity activity;
    private CompoundBarcodeView barcodeView;
    private int orientationLock = -1;
    private static final String SAVED_ORIENTATION_LOCK = "SAVED_ORIENTATION_LOCK";
    private boolean destroyed = false;
    private static final long DELAY_BEEP = 150L;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private Handler handler;
    private CallbackListener listener;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(final BarcodeResult result) {
            ScanCodeManager.this.barcodeView.pause();
            ScanCodeManager.this.beepManager.playBeepSoundAndVibrate();
            if (listener != null) listener.scanResult(result);
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    public void setCallbackListener(CallbackListener listener) {
        this.listener = listener;
    }

    public interface CallbackListener {
        /**
         * desc: 扫码结果回调
         */
        void scanResult(BarcodeResult result);
    }

    private final CameraPreview.StateListener stateListener = new CameraPreview.StateListener() {
        @Override
        public void previewSized() {
        }

        @Override
        public void previewStarted() {
        }

        @Override
        public void previewStopped() {
        }

        @Override
        public void cameraError(Exception error) {
            ScanCodeManager.this.displayFrameworkBugMessageAndExit();
        }

        @Override
        public void cameraClosed() {

        }
    };

    public ScanCodeManager(Activity activity, CompoundBarcodeView barcodeView) {
        this.activity = activity;
        this.barcodeView = barcodeView;
        barcodeView.getBarcodeView().addStateListener(this.stateListener);
        this.handler = new Handler();
        this.inactivityTimer = new InactivityTimer(activity, new Runnable() {
            public void run() {
                Log.d(ScanCodeManager.TAG, "Finishing due to inactivity");
                ScanCodeManager.this.finish();
            }
        });
        this.beepManager = new BeepManager(activity);
    }

    public void initializeFromIntent(Intent intent, Bundle savedInstanceState) {
        Window window = this.activity.getWindow();
        window.addFlags(128);
        if (savedInstanceState != null) {
            this.orientationLock = savedInstanceState.getInt("SAVED_ORIENTATION_LOCK", -1);
        }

        if (intent != null) {
            if (this.orientationLock == -1) {
                boolean orientationLocked = intent.getBooleanExtra("SCAN_ORIENTATION_LOCKED", true);
                if (orientationLocked) {
                    this.lockOrientation();
                }
            }

            if ("com.google.zxing.client.android.SCAN".equals(intent.getAction())) {
                this.barcodeView.initializeFromIntent(intent);
            }

            if (!intent.getBooleanExtra("BEEP_ENABLED", true)) {
                this.beepManager.setBeepEnabled(false);
//                this.beepManager.updatePrefs();
            }
        }

    }

    protected void lockOrientation() {
        if (this.orientationLock == -1) {
            Display display = this.activity.getWindowManager().getDefaultDisplay();
            int rotation = display.getRotation();
            int baseOrientation = this.activity.getResources().getConfiguration().orientation;
            byte orientation = 0;
            if (baseOrientation == 2) {
                if (rotation != 0 && rotation != 1) {
                    orientation = 8;
                } else {
                    orientation = 0;
                }
            } else if (baseOrientation == 1) {
                if (rotation != 0 && rotation != 3) {
                    orientation = 9;
                } else {
                    orientation = 1;
                }
            }

            this.orientationLock = orientation;
        }

        this.activity.setRequestedOrientation(this.orientationLock);
    }

    public void decode() {
        this.barcodeView.decodeSingle(this.callback);
    }

    public void onResume() {
        this.barcodeView.resume();
//        this.beepManager.updatePrefs();
        this.inactivityTimer.start();
    }

    public void onPause() {
        this.barcodeView.pause();
        this.inactivityTimer.cancel();
//        this.beepManager.close();
    }

    public void onDestroy() {
        this.destroyed = true;
        this.inactivityTimer.cancel();
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("SAVED_ORIENTATION_LOCK", this.orientationLock);
    }

    public static Intent resultIntent(BarcodeResult rawResult) {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.addFlags(524288);
        intent.putExtra("SCAN_RESULT", rawResult.toString());
        intent.putExtra("SCAN_RESULT_FORMAT", rawResult.getBarcodeFormat().toString());
        byte[] rawBytes = rawResult.getRawBytes();
        if (rawBytes != null && rawBytes.length > 0) {
            intent.putExtra("SCAN_RESULT_BYTES", rawBytes);
        }

        Map metadata = rawResult.getResultMetadata();
        if (metadata != null) {
            if (metadata.containsKey(ResultMetadataType.UPC_EAN_EXTENSION)) {
                intent.putExtra("SCAN_RESULT_UPC_EAN_EXTENSION", metadata.get(ResultMetadataType.UPC_EAN_EXTENSION).toString());
            }

            Number orientation = (Number) metadata.get(ResultMetadataType.ORIENTATION);
            if (orientation != null) {
                intent.putExtra("SCAN_RESULT_ORIENTATION", orientation.intValue());
            }

            String ecLevel = (String) metadata.get(ResultMetadataType.ERROR_CORRECTION_LEVEL);
            if (ecLevel != null) {
                intent.putExtra("SCAN_RESULT_ERROR_CORRECTION_LEVEL", ecLevel);
            }

            Iterable byteSegments = (Iterable) metadata.get(ResultMetadataType.BYTE_SEGMENTS);
            if (byteSegments != null) {
                int i = 0;

                for (Iterator i$ = byteSegments.iterator(); i$.hasNext(); ++i) {
                    byte[] byteSegment = (byte[]) i$.next();
                    intent.putExtra("SCAN_RESULT_BYTE_SEGMENTS_" + i, byteSegment);
                }
            }
        }

        return intent;
    }

    private void finish() {
        this.activity.finish();
    }

    protected void returnResult(BarcodeResult rawResult) {
        Intent intent = resultIntent(rawResult);
        this.activity.setResult(-1, intent);
        this.finish();
    }

    protected void displayFrameworkBugMessageAndExit() {
        if (!this.activity.isFinishing() && !this.destroyed) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
            builder.setTitle(this.activity.getString(com.google.zxing.client.android.R.string.zxing_app_name));
            builder.setMessage(this.activity.getString(com.google.zxing.client.android.R.string.zxing_msg_camera_framework_bug));
            builder.setPositiveButton(com.google.zxing.client.android.R.string.zxing_button_ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ScanCodeManager.this.finish();
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    ScanCodeManager.this.finish();
                }
            });
            builder.show();
        }
    }
}
