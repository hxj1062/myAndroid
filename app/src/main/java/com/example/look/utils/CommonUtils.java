package com.example.look.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.look.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CommonUtils {


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
     * @param context    上下文
     * @param title      弹窗标题
     * @param selectTime 选择的时间,HH:mm
     * @param listener   {@link OnConfirmListener}
     */
    public static void hourMinuteDialog(Context context, String title, String selectTime, OnConfirmListener listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View mView = inflater.inflate(R.layout.dialog_hour_min, null);
        Dialog mDialog = new Dialog(context, R.style.LoadingDialog);

        TextView dialog_title = mView.findViewById(R.id.tv_dialog_title);
        WheelPicker wpHour = mView.findViewById(R.id.wp_hour);  // 时
        WheelPicker wpMinute = mView.findViewById(R.id.wp_minute);  // 分
        WheelPicker wpSeconds = mView.findViewById(R.id.wp_seconds);  // 秒
        Button cancel = mView.findViewById(R.id.btn_cl);  // 取消
        Button sure = mView.findViewById(R.id.btn_sure);  // 确定

        Calendar cal = Calendar.getInstance();
        int selectHour;
        int selectMinute;
        int selectSeconds;
        String hourTwoDigit = "";
        String minuteTwoDigit = "";
        String secondsTwoDigit = "";
        if (TextUtils.isEmpty(selectTime)) {
            selectHour = cal.get(Calendar.HOUR_OF_DAY);
            selectMinute = cal.get(Calendar.MINUTE);
            selectSeconds = cal.get(Calendar.SECOND);
        } else {
            String[] split = selectTime.split(":");
            selectHour = Integer.parseInt(split[0]);
            selectMinute = Integer.parseInt(split[1]);
            selectSeconds = Integer.parseInt(split[2]);
        }
        dialog_title.setText(title);
        String hourFormat = context.getString(R.string.hour);
        String minuteFormat = context.getString(R.string.minute);
        String secondsFormat = context.getString(R.string.seconds);
        if (selectHour < 10) {
            hourTwoDigit = "0" + selectHour;
            initWheelPicker(wpHour, 0, 23, hourFormat, hourTwoDigit + hourFormat, true);
        } else {
            initWheelPicker(wpHour, 0, 23, hourFormat, selectHour + hourFormat, true);
        }

        if (selectMinute < 10) {
            minuteTwoDigit = "0" + selectMinute;
            initWheelPicker(wpMinute, 0, 59, minuteFormat, minuteTwoDigit + minuteFormat, true);
        } else {
            initWheelPicker(wpMinute, 0, 59, minuteFormat, selectMinute + minuteFormat, true);
        }
        if (selectSeconds < 10) {
            secondsTwoDigit = "0" + selectSeconds;
            initWheelPicker(wpSeconds, 0, 59, secondsFormat, secondsTwoDigit + secondsFormat, true);
        } else {
            initWheelPicker(wpSeconds, 0, 59, secondsFormat, selectSeconds + secondsFormat, true);
        }
        cancel.setOnClickListener(v -> mDialog.dismiss());
        sure.setOnClickListener(v -> {
            String mHour = (String) wpHour.getData().get(wpHour.getCurrentItemPosition());
            String mMinute = (String) wpMinute.getData().get(wpMinute.getCurrentItemPosition());
            String mSecond = (String) wpSeconds.getData().get(wpSeconds.getCurrentItemPosition());
            String hour = mHour.replace("时", "");
            String minute = mMinute.replace("分", "");
            String second = mSecond.replace("秒", "");
            if (listener != null) {
                listener.confirmTime(mDialog, title, Integer.parseInt(hour), Integer.parseInt(minute), Integer.parseInt(second));
            }
            mDialog.dismiss();
        });

        mView.setMinimumWidth(context.getResources().getDimensionPixelOffset(R.dimen.dp_100));
        // dialog弹出后会点击屏幕，dialog不消失；点击物理返回键dialog消失
        mDialog.setCanceledOnTouchOutside(false);
        // 点击物理返回键dialog是否消失
        mDialog.setCancelable(true);
        mDialog.setContentView(mView);
        mDialog.show();

        Window dialogWindow = mDialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams params = dialogWindow.getAttributes();
            // 设置dialog的宽和高
            params.width = (int) context.getResources().getDimension(R.dimen.dp_300);
            // 设置dialog居中
            params.gravity = Gravity.CENTER;
            dialogWindow.setAttributes(params);
        }
    }


    /**
     * 初始化滚轮
     *
     * @param wheelPicker {@link WheelPicker}
     * @param startValue  开始值
     * @param endValue    结束值
     * @param valueFormat 值的单位
     * @param selectValue 默认值
     * @param isAddZero   是否添加零
     */
    private static void initWheelPicker(WheelPicker wheelPicker, int startValue, int endValue, String valueFormat, String selectValue, boolean isAddZero) {
        if (valueFormat == null) {
            valueFormat = "";
        }
        List<String> list = new ArrayList<>();
        for (int i = startValue; i <= endValue; i++) {
            if (isAddZero) {
                if (i < 10) {
                    list.add("0" + i + valueFormat);
                    continue;
                }
            }
            list.add(i + valueFormat);
        }
        // 把数组添加到控件中
        wheelPicker.setData(list);
        if (!TextUtils.isEmpty(selectValue)) {
            // 获取需要选择的值在轮盘上的下标
            int position = list.indexOf(selectValue);
            // 判断是否等于-1
            if (position == -1) {
                // 是，集合不存在值
                // 默认选择为最后一个值
                wheelPicker.setSelectedItemPosition(list.size() - 1, false);
            } else {
                // 否，默认滑动选择器当前选择的值
                wheelPicker.setSelectedItemPosition(position, false);
            }
        }
    }


    public interface OnConfirmListener {

        /**
         * @param dialog {@link Dialog}
         * @param title  对话框标题
         * @param hour   小时
         * @param minute 分钟
         */
        void confirmTime(Dialog dialog, String title, int hour, int minute, int seconds);
    }
}
