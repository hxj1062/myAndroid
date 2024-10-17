package com.example.look.customview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.look.R;

public class PopupWindowDialog {


    private static PopupWindow mPopupWindow;

    private static View initDialog(Activity activity, int resource) {
        return initDialog(activity, resource, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

    private static View initDialog(Activity activity, int resource, int width, int height) {
        View mView = null;
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mView = inflater.inflate(resource, null);
        }
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        mPopupWindow = new PopupWindow(mView, width, height);
        // 设置popWindow弹出窗体点击区域外不消失
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(false);
        return mView;
    }

    @SuppressLint("ClickableViewAccessibility")
    private static View BottomToTopPopupWindowDialog(Activity activity, View view, int resource,
                                                     OnDismissListener listener) {
        View mView = initDialog(activity, resource);

        // 实例化一个ColorDrawable颜色为透明
        ColorDrawable drawable = new ColorDrawable(Color.TRANSPARENT);
        mPopupWindow.setBackgroundDrawable(drawable);
        // 设置popWindow的显示和消失动画
        mPopupWindow.setAnimationStyle(R.style.BottomToTopDialog);
        // 在底部显示
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        // 当PopupWindow弹出时，背景半透明
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = 0.5f;
        activity.getWindow().setAttributes(params);

        mPopupWindow.setOnDismissListener(() -> {
            // 当PopupWindow关闭时，背景回复
            WindowManager.LayoutParams layoutParams = activity.getWindow().getAttributes();
            layoutParams.alpha = 1f;
            activity.getWindow().setAttributes(layoutParams);
            // 关闭弹框的回调
            if (listener != null) {
                listener.onDismiss();
            }
        });
        mPopupWindow.setTouchInterceptor((v, event) -> event.getY() < 0);
        return mView;
    }

    public static void showDrawPlateDialog(Activity activity, View view, OnDrawPlateListener listener) {
        View mView = BottomToTopPopupWindowDialog(activity, view, R.layout.dialog_stock_check, null);
        TextView tvNextReplay = mView.findViewById(R.id.tv_next_replay);
        TextView tvGoodsLoss = mView.findViewById(R.id.tv_goods_loss);
        TextView tvCancel = mView.findViewById(R.id.tv_cancel_report);

        tvNextReplay.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNextReplay();
            }
            mPopupWindow.dismiss();
        });

        tvGoodsLoss.setOnClickListener(v -> {
            if (listener != null) {
                listener.onGoodsLoss();
            }
            mPopupWindow.dismiss();
        });

        tvCancel.setOnClickListener(v -> mPopupWindow.dismiss());
    }

    public interface OnDismissListener {

        void onDismiss();
    }

    public interface OnDrawPlateListener {

        void onNextReplay();

        void onGoodsLoss();
    }
}
