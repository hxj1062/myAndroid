package com.example.look;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.look.adpter.OpenInfoListAdapter;
import com.example.look.bean.AccountOpenInfo;
import com.example.look.customview.AccountOpenInfoDialog;
import com.example.look.customview.AppDownDialog;
import com.example.look.customview.CommonDialog;
import com.example.look.customview.LinePlanDialog;
import com.example.look.customview.NoticeDialog;
import com.example.look.customview.SignBoardView;
import com.example.look.utils.CommonUtils;
import com.example.look.utils.DimensionUtil;
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
import java.util.Hashtable;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private LinearLayout login_relative;
    private EditText mEd_maintaincause;
    private EditText ed_tools;
    private DisplayMetrics screeMessage;
    private boolean isTouch = true;
    private int i = 0;
    boolean flagsss = true;
    private AccountOpenInfoDialog openInfoDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        screeMessage = DimensionUtil.getScreeMessage(this);
        login_relative = findViewById(R.id.login_relative666);

        TextView tv_span = findViewById(R.id.tv_span);
        //  String str = String.format(getString(R.string.user_use_name), "huang", "xinjia");

        tv_span.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagsss) {
                    tv_span.setText("充值金额");
                    flagsss = false;
                } else {
                    tv_span.setText("结算金额");
                    flagsss = true;
                }
            }
        });

        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showLinePlanDialog();
//                showAppDownDialog();
//                showSubmitDialog();
//                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
//                startActivity(intent);
                //     String str = readJs("err-upload.js");
                //   Log.d("io流读取JS文件", str);

                showCommonDialog();
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

        //   signName();  // 画板
    }

    //region ***安卓菜单控件体验


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

    }

    TextView tvLine;
    boolean isChange;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_two, menu);
        MenuItem item = menu.findItem(R.id.action_line);
        View abc = item.getActionView();
        tvLine = abc.findViewById(R.id.tv_line);
        abc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChange) {
                    isChange = false;
                    tvLine.setText("孙悟空");
                } else {
                    isChange = true;
                    tvLine.setText("唐三藏");
                }
            }
        });
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_line) {

        }
        return super.onOptionsItemSelected(item);
    }

    //endregion


    private String readJs(String fileName) {
        String jsStr = "";
        try {
            InputStream in = getResources().getAssets().open(fileName);
            byte buff[] = new byte[1024];
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

    private void showDialog6() {
        List<AccountOpenInfo> testData = new ArrayList<>();
        testData.add(new AccountOpenInfo("NOT11111", "黄忠", "96547891214", "农业银行", "435158478123456"));
        testData.add(new AccountOpenInfo("NOT22222", "孙权", "45369876552", "建设银行", "554715689123456"));
        testData.add(new AccountOpenInfo("NOT33333", "张飞", "45879896215", "工商银行", "664148848123456"));
        testData.add(new AccountOpenInfo("NOT44444", "刘备", "74186378548", "中国银行", "767895784123456"));
        testData.add(new AccountOpenInfo("NOT55555", "曹操", "12345614789", "招商银行", "189159456123456"));
        testData.add(new AccountOpenInfo("NOT66666", "关羽", "98745586123", "邮政银行", "254158789123456"));
        testData.add(new AccountOpenInfo("NOT77777", "周瑜", "85214796963", "华夏银行", "335147478123456"));
        OpenInfoListAdapter adapter = new OpenInfoListAdapter(testData, MainActivity.this);
        openInfoDialog = new AccountOpenInfoDialog(adapter);
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
                            CommonUtils.showToast(MainActivity.this, str);
                        }
                    }
                } else {
                    CommonUtils.showToast(MainActivity.this, "取消");
                }
            }
        }).show(MainActivity.this.getSupportFragmentManager(), "dialog");
    }

    /**
     * desc: 银行卡号加星号
     */
    public static StringBuffer maskBankNum(String str) {
        StringBuffer buffer = new StringBuffer(str);
        int length = str.length() - 8;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("*");
        }
        return buffer.replace(4, buffer.length() - 4, sb.toString());
    }

    /**
     * desc: 手机号加星号
     */
    public static String maskPhoneNum(String phoneNumber) {

        char[] digits = phoneNumber.toCharArray();
        for (int i = 3; i < 7; i++) {
            digits[i] = '*';
        }
        return new String(digits);
    }

    /**
     * desc: 手写板签名
     */
    public void signName() {
        SignBoardView draw_sign = findViewById(R.id.draw_sign);
        draw_sign.start();
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

    public void showEditPsw() {
        Dialog dialog = new Dialog(this, R.style.NoticeDialog);
        View view = View.inflate(this, R.layout.edit_psw_view, null);
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
                    CommonUtils.showToast(MainActivity.this, "输入内容不能为空");
                    return;
                }
                if (!edtTxt.equals("uboxol")) {
                    CommonUtils.showToast(MainActivity.this, "输入密码错误");
                    return;
                }
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
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
                    CommonUtils.showToast(MainActivity.this, "输入内容不能为空");
                    return;
                }
                if (!content.equals("6666")) {
                    CommonUtils.showToast(MainActivity.this, "输入密码错误");
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

    public static void signContact(Context cont) {
        AlertDialog.Builder builder = new AlertDialog.Builder(cont, R.style.CustomAlertDialog);
        AlertDialog dialog = builder.setTitle("存在未签约合同，请先签署\n后再下单，谢谢！").setPositiveButton("跳转签属", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create();
        dialog.show();
        Button logOut = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        logOut.setTextSize(18);
        logOut.setTextColor(Color.parseColor("#F55C33"));

    }

    private void showNoticeDialog() {
        NoticeDialog noticeDialog = new NoticeDialog(this, R.style.QrCodeDialog);
        noticeDialog.setsTitle("测试测试12")
                .setsImg("")
                .setsContent("夏天的脚步已经临近“快乐水”。")
                .lookDetails(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonUtils.showToast(MainActivity.this, "弹窗");
                    }
                }).show();
    }


    private void showAppDownDialog() {
        AppDownDialog appDownDialog = new AppDownDialog(this, R.style.QrCodeDialog);
        appDownDialog.show();
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


    public void showLinePlanDialog() {
        LinePlanDialog noticeDialog = new LinePlanDialog(this, R.style.QrCodeDialog);
        noticeDialog.changeCustom(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonUtils.showToast(MainActivity.this, "选用自定义");
                    }
                }).changeSystem(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonUtils.showToast(MainActivity.this, "选用系统");
                    }
                }).
                goToReplenish(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonUtils.showToast(MainActivity.this, "返回修改补货顺序");
                    }
                }).show();
    }

    boolean isCanClick = true;

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
                    CommonUtils.showToast(MainActivity.this, "输入内容不能为空");
                    return;
                }
                if (!content.equals("6666")) {
                    CommonUtils.showToast(MainActivity.this, "输入密码错误");
                    return;
                }
                mPop_change.dismiss();
            }
        });
    }


    // 弹出选择测试正式环境的pop
    @SuppressLint("SetTextI18n")
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


    // 版本号比较  例如: 1.1.1和1.1.2
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

}





