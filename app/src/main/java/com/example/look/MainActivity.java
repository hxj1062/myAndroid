package com.example.look;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.look.adpter.FranchiseUserAdapter;
import com.example.look.adpter.OpenInfoListAdapter;
import com.example.look.adpter.ToolAdapter;
import com.example.look.bean.AccountOpenInfo;
import com.example.look.bean.FranchiseBean;
import com.example.look.blue.BluetoothTaskActivity;
import com.example.look.customview.AccountOpenInfoDialog;
import com.example.look.customview.AppDownDialog;
import com.example.look.customview.CommonDialog;
import com.example.look.customview.EditGoodsDialog;
import com.example.look.customview.FranchiseUserDialog;
import com.example.look.customview.LinePlanDialog;
import com.example.look.customview.NoticeDialog;
import com.example.look.customview.PopupWindowDialog;
import com.example.look.customview.UrlDialog;
import com.example.look.realm.WebPersonActivity;
import com.example.look.scan.ScanCodeActivity;
import com.example.look.utils.DimensionUtil;
import com.example.look.views.CountDownActivity;
import com.example.look.views.Cycle01ActivityMy;
import com.example.look.views.EvenBus01ActivityMy;
import com.example.look.views.FloatingBtnUtil;
import com.example.look.views.GoodsListActivityMy;
import com.example.look.views.SettingActivity;
import com.example.look.views.SignBoardActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class MainActivity extends MyBaseActivity {

    private static final int OVERLAY_PERMISSION_REQUEST_CODE = 1;
    private LinearLayout login_relative;
    private DisplayMetrics screeMessage;
    private ToolAdapter toolAdapter;
    private boolean isTouch = true;
    boolean isCanClick = true;
    private int i = 0;
    boolean txtChange = true;
    private WindowManager windowManager;
    private ImageButton floatingButton;
    protected FloatingBtnUtil floatingBtnUtil;
    public static final Map<Integer, String> toolsMap = Collections.unmodifiableMap(
            new HashMap<Integer, String>() {{
                put(0, "环境弹窗");
                put(1, "底部弹窗");
                put(2, "io流读Js文件");
                put(3, "H5地址弹窗");
                put(4, "账户选择");
                put(5, "路线对比弹窗");
                put(6, "通用toast");
                put(7, "弹窗倒计时");
                put(8, "app下架");
                put(9, "活动通知");
                put(10, "友咖提示");
                put(11, "转账入口验证");
                put(12, "友客云提示");
                put(13, "系统弹窗");
                put(14, "生命周期体验");
                put(15, "EvenBus示例");
                put(16, "加盟账号");
                put(17, "倒计时实现");
                put(18, "二维码弹窗");
                put(19, "画板");
                put(20, "列表addView");
                put(21, "商品弹窗");
                put(22, "扫码测试");
                put(23, "文案显示");
                put(24, "蓝牙示例");
                put(25, "实名认证");
            }}
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvToolList = findViewById(R.id.rv_tool_list);
        login_relative = findViewById(R.id.ll_zhe);
        screeMessage = DimensionUtil.getScreeMessage(this);
        toolAdapter = new ToolAdapter(toolsMap);
        rvToolList.setLayoutManager(new GridLayoutManager(this, 5));
        rvToolList.setAdapter(toolAdapter);
        toolAdapter.onToolClickListener = new ToolAdapter.OnToolClickListener() {
            @Override
            public void onToolClick(int tag) {
                showEvent(tag);
            }
        };

        floatingBtnUtil = new FloatingBtnUtil(this);
        floatingBtnUtil.setOnClickListener(new FloatingBtnUtil.OnClickListener() {
            @Override
            public void onClick() {
                showToast("悬浮按钮点击", 1);
            }
        });
    }

    private void showEvent(int tag) {
        switch (tag) {
            case 0:
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
                if (i >= 1) { // 点击次数
                    i = 0;
                    showEnvDialog();
                }
                break;
            case 1:
                PopupWindowDialog.showDrawPlateDialog(MainActivity.this, findViewById(R.id.ll_zhe), new PopupWindowDialog.OnDrawPlateListener() {
                    @Override
                    public void onNextReplay() {
                        showToast("打开弹窗2", 0);
                    }

                    @Override
                    public void onGoodsLoss() {
                        showToast("打开弹窗2", 0);
                    }
                });
                break;
            case 2:
                String str = readJs("err-upload.js");
                Log.d("io流读取JS文件", str);
                break;
            case 3:
                showUrlDialog("https://h5.dev.uboxol.com/abreast-node-approval-dev/#/review/index?emp_no=${uid}&username=${ldPreferences.loginAccount}&emp_role=${ldPreferences.empRole}");
                break;
            case 4:
                showAccountDialog();
                break;
            case 5:
                showLinePlanDialog();
                break;
            case 6:
                showToast("测试一下通用toast", 1);
                break;
            case 7:
                showSubmitDialog();
                break;
            case 8:
                showAppDownDialog();
                break;
            case 9:
                showNoticeDialog();
                break;
            case 10:
                showTipsDialog1();
                break;
            case 11:
                showToolsDialog();
                break;
            case 12:
                showTipsDialog();
                break;
            case 13:
                systemDialog();
                break;
            case 14:
                Intent lifeIntent = new Intent(MainActivity.this, Cycle01ActivityMy.class);
                startActivity(lifeIntent);
                break;
            case 15:
                Intent busIntent = new Intent(MainActivity.this, EvenBus01ActivityMy.class);
                startActivity(busIntent);
                break;
            case 16:
                showFranchiseDialog();
                break;
            case 17:
                Intent countIntent = new Intent(MainActivity.this, CountDownActivity.class);
                startActivity(countIntent);
                break;
            case 18:
                showQrcodeDialog();
                break;
            case 19:
                startActivity(new Intent(MainActivity.this, SignBoardActivity.class));
                break;
            case 20:
                startActivity(new Intent(MainActivity.this, GoodsListActivityMy.class));
                break;
            case 21:
                showGoodsDialog();
                break;
            case 22:
                startActivity(new Intent(MainActivity.this, ScanCodeActivity.class));
                break;
            case 23:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
            case 24:
                startActivity(new Intent(MainActivity.this, BluetoothTaskActivity.class));
                break;
            case 25:
                startActivity(new Intent(MainActivity.this, WebPersonActivity.class));
                break;
            default:
                showToast("事件", 1);
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkOverlayPermission();
    }

    @Override
    protected void onPause() {
        super.onPause();
        floatingBtnUtil.hide();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                showPermissionExplanationDialog();
            } else {
                // 已有权限，执行悬浮窗相关操作
                if (floatingBtnUtil != null) {
                    floatingBtnUtil.show();
                }
            }
        } else {
            // Android 6.0 以下无需此权限
            if (floatingBtnUtil != null) {
                floatingBtnUtil.show();
            }
        }
    }

    private void showPermissionExplanationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("悬浮窗权限申请")
                .setMessage("我们需要获取悬浮窗权限，以启用悬浮AI客服小助手功能。")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openOverlayPermissionSettings();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void openOverlayPermissionSettings() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE);
    }

    private int dpToPx(Context context, int dp) {
        float density;
        density = context.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }


    //region ***实现内容

    /**
     * desc: 按钮文字来回切换
     */
//    void txtChange() {
//        TextView tv_span = findViewById(R.id.tv_span);
//        String str = String.format(getString(R.string.user_use_name), "huang", "xinjia");
//        tv_span.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (txtChange) {
//                    tv_span.setText("充值金额");
//                    txtChange = false;
//                } else {
//                    tv_span.setText("结算金额");
//                    txtChange = true;
//                }
//            }
//        });
//    }
    private void showGoodsDialog() {

        EditGoodsDialog openInfoDialog = new EditGoodsDialog();
        openInfoDialog.setCallBack(new EditGoodsDialog.EdtGoodsInfoCallBack() {
            @Override
            public void call(int result) {
                if (result == 1) {
                    showToast("确认", 1);
                } else {
                    showToast("取消", 1);
                }
            }
        }).show(MainActivity.this.getSupportFragmentManager(), "dialog");
    }


    /**
     * desc: 版本号比较  例如: 1.1.1和1.1.2
     */
    public static boolean isNeedUpdate(String currentVersion, String anotherVersion) {
        String[] v1 = currentVersion.split("\\.");
        String[] v2 = anotherVersion.split("\\.");
        int len1 = v1.length;
        int len2 = v2.length;
        int lim = Math.min(len1, len2);
        int k = 0;
        while (k < lim) {
            String c1 = v1[k];
            String c2 = v2[k];
            if (Integer.parseInt(c1) != Integer.parseInt(c2)) {
                return Integer.parseInt(c1) - Integer.parseInt(c2) < 0;
            }
            k++;
        }
        return len1 - len2 < 0;
    }

    /**
     * desc: 弹出选择测试正式环境的pop
     */
    public void showEnvDialog111() {

        Dialog dialog = new Dialog(this, R.style.NoticeDialog);
        View view = View.inflate(this, R.layout.dialog_ceshi666888, null);
        dialog.setContentView(view);
        TextView appVersion = view.findViewById(R.id.tv_app_version);
        appVersion.setText(getString(R.string.app_version) + "getVersion()");

        RadioGroup switchEnv = view.findViewById(R.id.rg_switch_env);
        switchEnv.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_formal_env:
                        break;
                    case R.id.rb_testing_env:
                        break;
                    case R.id.rb_pre_env:
                        break;
                }
            }
        });
        view.findViewById(R.id.rb_formal_env); //正式
        view.findViewById(R.id.rb_testing_env); //测试
        view.findViewById(R.id.rb_pre_env); //预发布
        TextView mTv_cancel = view.findViewById(R.id.tv_state_cancel);
        TextView mTv_sure = view.findViewById(R.id.tv_state_sure);
        mTv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mTv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    // 弹出选择测试正式环境的pop
    @SuppressLint("SetTextI18n")
    public void showEnvDialog() {
        View envView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_environment_state, null);
        PopupWindow mPop_change = new PopupWindow(envView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
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
        EditText mEd_maintaincause = envView.findViewById(R.id.ed_maintaincause);
        RelativeLayout mRl_popwindowinside = envView.findViewById(R.id.rl_login_change_inside);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mRl_popwindowinside.getLayoutParams();
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
                    showToast("输入内容不能为空", 1);
                    return;
                }
                if (!content.equals("6666")) {
                    showToast("输入密码错误", 1);
                    return;
                }
                mPop_change.dismiss();
            }
        });
    }

//    /**
//     * desc: 创建Toast
//     *
//     * @param content 文案
//     * @param tag     0-大约2秒 1-大约3.5秒
//     */
//    public void showToast(String content, int tag) {
//        int duration;
//        if (tag == 0) {
//            duration = Toast.LENGTH_SHORT; // 大约2秒
//        } else {
//            duration = Toast.LENGTH_LONG; // 大约3.5秒
//        }
//        Toast toast = new Toast(this);
//        TextView textView = new TextView(this);
//        textView.setText(content);
//        textView.setBackground(getResources().getDrawable(R.drawable.tosat_bg));
//        textView.setTextColor(getResources().getColor(R.color.color_FFFFFF));
//        textView.setPadding(18, 18, 18, 18);
//        toast.setView(textView);
//        toast.setDuration(duration);
//        toast.show();
//    }

    /**
     * desc: 二维码弹窗
     */
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
    public static Bitmap createQRCodeBitmap(String content, int width, int height, String character_set, String error_correction_level, String margin, int color_black, int color_white) {
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


    /**
     * desc: 富文本
     */
    private void testSpan() {
        String str = "使用完整服务\n本应用需要获取个人信息才可使用完整服务名单前仅可浏览部分内容";
        TextView textView = findViewById(R.id.tv_span);
        SpannableString span = new SpannableString(str);
        span.setSpan(new ForegroundColorSpan(Color.parseColor("#DC143C")), 6, str.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        span.setSpan(new AbsoluteSizeSpan(14, true), 6, str.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(span);
    }

    private void showCommonDialog() {
        CommonDialog commonDialog = new CommonDialog(this);
        commonDialog.setRightBtnValue("确定").setLeftBtnValue("取消").setOnItemRightListener(new CommonDialog.OnItemRightListener() {
            @Override
            public void onRightClick() {
                if (isCanClick) {
                    isCanClick = false;
                    Log.d("今天妇女节", "延时两秒打印");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isCanClick = true;
                        }
                    }, 2000);
                }
                //  commonDialog.dismiss();
            }
        }).setOnItemLeftListener(new CommonDialog.OnItemLeftListener() {
            @Override
            public void onLeftClick() {

            }
        }).show();
    }


    public void showLinePlanDialog() {
        LinePlanDialog noticeDialog = new LinePlanDialog(this, R.style.QrCodeDialog);
        noticeDialog.changeCustom(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("选用自定义", 1);
            }
        }).changeSystem(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("选用系统", 1);
            }
        }).goToReplenish(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("返回修改补货顺序", 1);
            }
        }).show();
    }

    /**
     * desc: 倒计时弹窗
     */
    public void showSubmitDialog() {
        Dialog dialog = new Dialog(this, R.style.SubmitDialog);
        View view = View.inflate(this, R.layout.dialog_submit, null);
        dialog.setContentView(view);

        TextView tvHandel = view.findViewById(R.id.tv_to_handle);
        //CountDownTimer timer = new CountDownTimer(3000，1000)中，*第一个参数表示总时间，第二个参数表示间隔时间。
        // 意思就是每隔一秒会回调一次方法onTick，然后1秒之后会回调onFinish方法。
        CountDownTimer timer = new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                tvHandel.setText("我知道了(" + millisUntilFinished / 1000 + "s)");
            }

            @Override
            public void onFinish() {
                //   dialog.dismiss();
            }
        };
        //调用CountDownTimer对象的 start()方法开始倒计时，也不涉及到线程处理
        timer.start();
        tvHandel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * desc: App下架通知
     */
    private void showAppDownDialog() {
        AppDownDialog appDownDialog = new AppDownDialog(this, R.style.QrCodeDialog);
        appDownDialog.show();
    }

    /**
     * desc: 通知弹窗
     */
    private void showNoticeDialog() {
        NoticeDialog noticeDialog = new NoticeDialog(this, R.style.QrCodeDialog);
        noticeDialog.setsTitle("测试测试12").setsImg("").setsContent("夏天的脚步已经临近“快乐水”。").lookDetails(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("弹窗", 1);
            }
        }).show();
    }

    /**
     * desc: 系统弹窗
     */
    public static void signContact(Context cont) {
        AlertDialog.Builder builder = new AlertDialog.Builder(cont, R.style.CustomAlertDialog);
        AlertDialog dialog = builder.setMessage("测距欧斯阿娇诞节偶分接收到i放假哦撒低级哦").create();
        dialog.show();
        Button logOut = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        logOut.setTextSize(18);
        logOut.setTextColor(Color.parseColor("#F55C33"));

    }

    /**
     * desc: 友咖提示
     */
    public void showTipsDialog1() {
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

    /**
     * desc: 转账入口
     */
    @SuppressLint("MissingInflatedId")
    private void showToolsDialog() {
        View envView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_to_tool, null);
        PopupWindow mPop_change = new PopupWindow(envView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
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
        EditText ed_tools = envView.findViewById(R.id.edt_tools);
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
                    showToast("输入内容不能为空", 1);
                    return;
                }
                if (!content.equals("6666")) {
                    showToast("输入密码错误", 1);
                    return;
                }
                mPop_change.dismiss();
            }
        });
    }

    public void showEditPsw() {
        Dialog dialog = new Dialog(this, R.style.NoticeDialog);
        View view = View.inflate(this, R.layout.dialog_edit_psw_view, null);
        dialog.setContentView(view);
        EditText inputPsw = (EditText) view.findViewById(R.id.edt_maintain_cause);
        TextView tvcancel = (TextView) view.findViewById(R.id.tv_state_cancel);
        TextView tvconfirm = (TextView) view.findViewById(R.id.tv_state_sure);
        tvcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edtTxt = inputPsw.getText().toString();
                if (edtTxt.isEmpty()) {
                    showToast("输入内容不能为空", 1);
                    return;
                }
                if (!edtTxt.equals("uboxol")) {
                    showToast("输入密码错误", 1);
                    return;
                }
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * desc: 友客云提示
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

    public void systemDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        AlertDialog dialog = builder.setTitle("系统弹窗").setMessage("定位不可用，请打开定位权限!").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("跳转签属", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create();
        dialog.setCancelable(false);
        dialog.show();
        Button logOut = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        logOut.setTextSize(18);
        logOut.setTextColor(Color.parseColor("#3090FF"));
        Button logCancel = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        logCancel.setTextSize(18);
        logCancel.setTextColor(Color.parseColor("#3090FF"));
        try {
            // 获取mAlert对象
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(dialog);

            // 获取mTitleView并设置大小颜色
            Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
            mTitle.setAccessible(true);
            TextView mTitleView = (TextView) mTitle.get(mAlertController);
            mTitleView.setTextSize(28);
            mTitleView.setTextColor(Color.BLACK);

            // 获取mMessageView并设置大小颜色
            Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
            mMessage.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(mAlertController);
            mMessageView.setTextSize(23);
            mMessageView.setTextColor(Color.BLACK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showAccountDialog() {
        List<AccountOpenInfo> testData = new ArrayList<>();
        testData.add(new AccountOpenInfo("NOT11111", "黄忠", "96547891214", "农业银行", "435158478123456"));
        testData.add(new AccountOpenInfo("NOT22222", "孙权", "45369876552", "建设银行", "554715689123456"));
        testData.add(new AccountOpenInfo("NOT33333", "张飞", "45879896215", "工商银行", "664148848123456"));
        testData.add(new AccountOpenInfo("NOT44444", "刘备", "74186378548", "中国银行", "767895784123456"));
        testData.add(new AccountOpenInfo("NOT55555", "曹操", "12345614789", "招商银行", "189159456123456"));
        testData.add(new AccountOpenInfo("NOT66666", "关羽", "98745586123", "邮政银行", "254158789123456"));
        testData.add(new AccountOpenInfo("NOT77777", "周瑜", "85214796963", "华夏银行", "335147478123456"));
        OpenInfoListAdapter adapter = new OpenInfoListAdapter(testData, MainActivity.this);
        AccountOpenInfoDialog openInfoDialog = new AccountOpenInfoDialog(adapter);
        openInfoDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < adapter.getAccountData().size(); i++) {
                    adapter.getAccountData().get(i).selectState = false;
                }
                boolean selected = adapter.getAccountData().get(position).selectState;
                adapter.getAccountData().get(position).selectState = !selected;
                adapter.notifyDataSetChanged();
            }
        });
        openInfoDialog.setCallBack(new AccountOpenInfoDialog.AccountOpenInfoCallBack() {
            @Override
            public void call(int result) {
                if (result == 1) {
                    for (int i = 0; i < adapter.getAccountData().size(); i++) {
                        if (adapter.getAccountData().get(i).selectState) {
                            String aaa = adapter.getAccountData().get(i).getOpenNum();
                            String bbb = adapter.getAccountData().get(i).getOpenName();
                            String ccc = adapter.getAccountData().get(i).getOpenPhone();
                            String ddd = adapter.getAccountData().get(i).getOpenBank();
                            String str = aaa + "\n" + bbb + "\n" + ccc + "\n" + ddd;
                            showToast(str, 1);
                        }
                    }
                } else {
                    showToast("取消", 1);
                }
            }
        }).show(MainActivity.this.getSupportFragmentManager(), "dialog");
    }

    private void showFranchiseDialog() {

        List<FranchiseBean> testData = new ArrayList<>();
        testData.add(new FranchiseBean("是共享仓0", "NJ01234", 0));
        testData.add(new FranchiseBean("加盟客户0", "NJ56789", 1));
        testData.add(new FranchiseBean("个体运营0", "NJ13572", 1));
        testData.add(new FranchiseBean("个体运营2", "NJ13572", 1));
        testData.add(new FranchiseBean("个体运营3", "NJ13572", 1));
        testData.add(new FranchiseBean("个体运营4", "NJ13572", 1));
        testData.add(new FranchiseBean("个体运营5", "NJ13572", 1));
        FranchiseUserAdapter adapter = new FranchiseUserAdapter(testData, MainActivity.this);
        FranchiseUserDialog openInfoDialog = new FranchiseUserDialog(adapter);
        openInfoDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < adapter.getAccountData().size(); i++) {
                    adapter.getAccountData().get(i).selectState = false;
                }
                boolean selected = adapter.getAccountData().get(position).selectState;
                adapter.getAccountData().get(position).selectState = !selected;
                adapter.notifyDataSetChanged();
            }
        });
        openInfoDialog.setCallBack(new FranchiseUserDialog.AccountOpenInfoCallBack() {
            @Override
            public void call(int result) {
                if (result == 1) {
                    for (int i = 0; i < adapter.getAccountData().size(); i++) {
                        if (adapter.getAccountData().get(i).selectState) {
                            String aaa = adapter.getAccountData().get(i).name;
                            String bbb = adapter.getAccountData().get(i).num;
                            String str = "加盟名称" + aaa + "==加盟客编" + bbb;
                            showToast(str, 1);
                        }
                    }
                } else {
                    showToast("取消", 1);
                }
            }
        }).show(MainActivity.this.getSupportFragmentManager(), "dialog");
    }

    /**
     * desc: h5地址弹窗展示
     */
    private void showUrlDialog(String str) {
        UrlDialog noticeDialog = new UrlDialog(this, str);
        noticeDialog.setOnCopyClickListener(new UrlDialog.OnCopyClickListener() {
            @Override
            public void onCopyClick() {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", str);
                clipboard.setPrimaryClip(clip);
            }
        }).show();
    }

    /**
     * desc: 配置文件读取Js
     */
    private String readJs(String fileName) {
        String jsStr = "";
        try {
            InputStream in = getResources().getAssets().open(fileName);
            byte[] buff = new byte[1024];
            ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
            do {
                int numRead = in.read(buff);
                if (numRead <= 0) {
                    break;
                }
                fromFile.write(buff, 0, numRead);
            } while (true);
            jsStr = fromFile.toString();
            in.close();
            fromFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsStr;
    }

    //endregion
}