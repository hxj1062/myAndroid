package com.example.look;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;
import com.example.look.customview.CommonDialog;
import com.example.look.customview.NoticeDialog;
import com.example.look.utils.DimensionUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {


    private LinearLayout login_relative;
    private EditText mEd_maintaincause;
    private EditText ed_tools;
    private DisplayMetrics screeMessage;
    private boolean isTouch = true;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screeMessage = DimensionUtil.getScreeMessage(this);
        login_relative = findViewById(R.id.login_relative666);

        TextView tv_span = findViewById(R.id.tv_span);
        String str = String.format(getString(R.string.user_use_name), "huang", "xinjia");
        tv_span.setText(str);

        findViewById(R.id.btn_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTipsDialog();
            }
        });

        ImageView iv_dialog_start = findViewById(R.id.iv_env_change);
        iv_dialog_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTouch) {
                    isTouch = false;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            i = 0;
                            isTouch = true;
                        }
                    }, 3000);
                }
                i++;
                if (i >= 5) {
                    i = 0;
                    showEnvDialog();
                }
            }
        });
    }


    /**
     * desc: 自定义弹窗,修改大小
     */
    public void showTipsDialog() {
        Dialog dialog = new Dialog(this, R.style.CommonDialog111);
        dialog.setContentView(R.layout.dialog_tips);
        TextView tvButton = (TextView) dialog.findViewById(R.id.dialog_btn_tips);
        tvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        Point point = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(point);
        params.height = (int) (point.y * 0.42);
        params.width = (int) (point.x * 0.82);
        window.setAttributes(params);
        dialog.show();
    }

    /**
     * desc: 转账入口
     */
    @SuppressLint("MissingInflatedId")
    private void showToolsDialog() {
        View envView = LayoutInflater.from(MainActivity.this)
                .inflate(R.layout.dialog_to_tool, null);
        PopupWindow mPop_change = new PopupWindow(envView, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        mPop_change.setFocusable(true);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        if (Integer.parseInt(Build.VERSION.SDK) >= 21) {
            mPop_change.setBackgroundDrawable(MainActivity.this.getDrawable(R.color.color_3C000000));
        }
        mPop_change.showAtLocation(login_relative, Gravity.CENTER, 0, 0);

        RelativeLayout mRl_popwindow = envView.findViewById(R.id.rl_tools_window);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mRl_popwindow.getLayoutParams();
        lp.width = 500 * screeMessage.widthPixels / 750;
        //  lp.height = 16 + screeMessage.widthPixels / 3;
        lp.height = 380;
        TextView mTv_cancel = envView.findViewById(R.id.tv_tool_cancel);
        TextView mTv_sure = envView.findViewById(R.id.tv_tool_sure);
        ed_tools = envView.findViewById(R.id.edt_tools);
        mTv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop_change.dismiss();
            }
        });
        mTv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = ed_tools.getText().toString();
                if (content.isEmpty()) {
                    Toast.makeText(MainActivity.this, "输入内容不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!content.equals("6666")) {
                    Toast.makeText(MainActivity.this, "输入密码错误", Toast.LENGTH_LONG).show();
                    return;
                }
                mPop_change.dismiss();
            }
        });
    }


    public void showTipsDialog666() {
        // 创建对话框对象
        Dialog dialog = new Dialog(this, R.style.CommonDialog111);
        // 设置标题
        dialog.setTitle("提示信息");
        // 给对话框填充布局
        dialog.setContentView(R.layout.dialog_tishi);
        Button tvButton1 = dialog.findViewById(R.id.dialog_btn_cancle);
        Button tvButton2 = dialog.findViewById(R.id.dialog_btn_confirm);
        tvButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // 获得当前activity所在的window对象
        Window window = dialog.getWindow();
        // 获得代表当前window属性的对象
        WindowManager.LayoutParams params = window.getAttributes();
        Point point = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        // 将window的宽高信息保存在point中
        display.getSize(point);
        // 将设置后的大小赋值给window的宽高
        params.width = (int) (point.x * 0.9);
        params.height = (int) (point.y * 0.25);
        // 方式一：设置属性
        window.setAttributes(params);
        // 方式二：当window属性改变的时候也会调用此方法，同样可以实现
        // dialog.onWindowAttributesChanged(params);
        dialog.show();
    }

    private void showNoticeDialog() {
        NoticeDialog noticeDialog = new NoticeDialog(this, R.style.QrCodeDialog);
        noticeDialog.setsTitle("测试测试12")
                .setsImg("")
                .setsContent("夏天的脚步已经临近“快乐水”。")
                .lookDetails(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "弹窗", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    private void showCommonDialog() {
        CommonDialog commonDialog = new CommonDialog(this);
        commonDialog.setOnItemRightListener(new CommonDialog.OnItemRightListener() {
            @Override
            public void onRightClick() {
                commonDialog.dismiss();
            }
        }).show();
    }

    /**
     * desc: 富文本
     */
    private void testSpan() {
        String str = "使用完整服务\n本应用需要获取个人信息才可使用完整服务名单前仅可浏览部分内容";
        TextView textView = findViewById(R.id.tv_span);
        SpannableString span = new SpannableString(str);
//        span.setSpan(new ForegroundColorSpan(Color.parseColor("#DC143C")), 6,
//                str.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        span.setSpan(new AbsoluteSizeSpan(14, true), 6, str.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(span);
    }

    private void showQrcodeDialog() {
        Dialog dialog = new Dialog(this, R.style.QrCodeDialog);
        View view = View.inflate(getApplication(), R.layout.dialog_qrcode, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        ImageView dialogCancel = view.findViewById(R.id.iv_dialog_cancel);
        ImageView iv_dialog_qrcode = view.findViewById(R.id.iv_dialog_qrcode);
        Bitmap bitmap = createQRCodeBitmap("content", 600, 600, "UTF-8", "H", "1", Color.BLACK, Color.WHITE);
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        iv_dialog_qrcode.setBackground(drawable);
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    /**
     * desc: 生成bitmap
     */
    public static Bitmap createQRCodeBitmap(String content, int width, int height,
                                            String character_set, String error_correction_level,
                                            String margin, int color_black, int color_white) {
        // 字符串内容判空
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        // 宽和高>=0
        if (width < 0 || height < 0) {
            return null;
        }
        QRCodeWriter writer = new QRCodeWriter();
        try {
            /** 1.设置二维码相关配置 */
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            // 字符转码格式设置
            if (!TextUtils.isEmpty(character_set)) {
                hints.put(EncodeHintType.CHARACTER_SET, character_set);
            }
            // 容错率设置
            if (!TextUtils.isEmpty(error_correction_level)) {
                hints.put(EncodeHintType.ERROR_CORRECTION, error_correction_level);
            }
            // 空白边距设置
            if (!TextUtils.isEmpty(margin)) {
                hints.put(EncodeHintType.MARGIN, margin);
            }
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    //bitMatrix.get(x,y)方法返回true是黑色色块，false是白色色块
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = color_black;//黑色色块像素设置
                    } else {
                        pixels[y * width + x] = color_white;// 白色色块像素设置
                    }
                }
            }
            /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,并返回Bitmap对象 */
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }


    // 弹出选择测试正式环境的pop
    @SuppressLint("SetTextI18n")
    public void showEnvDialog() {
        View envView = LayoutInflater.from(MainActivity.this)
                .inflate(R.layout.dialog_environment_state, null);
        PopupWindow mPop_change = new PopupWindow(envView, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        mPop_change.setFocusable(true);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        if (Integer.parseInt(Build.VERSION.SDK) >= 21) {
            mPop_change.setBackgroundDrawable(MainActivity.this.getDrawable(R.color.color_3C000000));
        }
        mPop_change.showAtLocation(login_relative, Gravity.CENTER, 0, 0);
        TextView appVersion = envView.findViewById(R.id.tv_app_version);
        appVersion.setText(getString(R.string.app_version) + "getVersion()");
        envView.findViewById(R.id.rb_formal_env); //正式
        envView.findViewById(R.id.rb_testing_env); //测试
        envView.findViewById(R.id.rl_pre_env); //预发布
        mEd_maintaincause = envView.findViewById(R.id.ed_maintaincause);
        RelativeLayout mRl_popwindowinside = envView.findViewById(R.id.rl_login_change_inside);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mRl_popwindowinside
                .getLayoutParams();
        lp.width = 500 * screeMessage.widthPixels / 750;
        lp.height = screeMessage.widthPixels * 2 / 3;
        TextView mTv_cancel = envView.findViewById(R.id.tv_version_cancel);
        TextView mTv_sure = envView.findViewById(R.id.tv_version_sure);
        mTv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop_change.dismiss();
            }
        });
        mTv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mEd_maintaincause.getText().toString();
                if (content.length() == 0) {
                    Toast.makeText(MainActivity.this, "输入内容不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!content.equals("6666")) {
                    Toast.makeText(MainActivity.this, "输入密码错误", Toast.LENGTH_LONG).show();
                    return;
                }
                mPop_change.dismiss();
            }
        });
    }


}

