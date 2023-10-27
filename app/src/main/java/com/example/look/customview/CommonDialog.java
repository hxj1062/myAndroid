package com.example.look.customview;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.look.R;


public class CommonDialog extends Dialog implements View.OnClickListener {
    private TextView tvTitle, tvContent;
    private Button btnLeft, btnRight;
    private RelativeLayout rlCommonDialog;
    private OnItemLeftListener onItemLeftListener;
    private OnItemRightListener onItemRightListener;
    private String titleValue, contentValue, leftValue, rightValue;

    public CommonDialog(@NonNull Context context) {
        super(context, R.style.QrCodeDialog);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common);
        setCanceledOnTouchOutside(false);
        initView();
        initData();
    }

    private void initView() {
        tvTitle = this.findViewById(R.id.dialog_title);
        tvContent = this.findViewById(R.id.dialog_content);
        btnLeft = this.findViewById(R.id.dialog_btn_left);
        btnRight = this.findViewById(R.id.dialog_btn_right);
        rlCommonDialog = this.findViewById(R.id.rl_common_dialog);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
    }

    private void initData() {
        if (!TextUtils.isEmpty(titleValue))
            tvTitle.setText(titleValue);
        if (!TextUtils.isEmpty(contentValue))
            tvContent.setText(contentValue);
        if (!TextUtils.isEmpty(leftValue))
            btnLeft.setText(leftValue);
        if (!TextUtils.isEmpty(rightValue))
            btnRight.setText(rightValue);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_btn_left:
                this.dismiss();
                break;
            case R.id.dialog_btn_right:
                if (onItemRightListener != null) {
                    onItemRightListener.onRightClick();
                }
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    /**
     * desc: 右边按钮监听器
     */
    public interface OnItemLeftListener {
        void onLeftClick();
    }

    /**
     * desc: 右边按钮监听器
     */
    public interface OnItemRightListener {
        void onRightClick();
    }

    /**
     * desc: 左边按钮监听事件
     *
     * @param onItemLeftListener
     * @return CommonDialog
     */
    public CommonDialog setOnItemLeftListener(OnItemLeftListener onItemLeftListener) {
        this.onItemLeftListener = onItemLeftListener;
        return this;
    }

    /**
     * desc: 右边按钮监听事件
     *
     * @param onItemRightListener
     * @return CommonDialog
     */
    public CommonDialog setOnItemRightListener(OnItemRightListener onItemRightListener) {
        this.onItemRightListener = onItemRightListener;
        return this;
    }

    /**
     * desc: 设置标题
     *
     * @param titleValue 标题文本内容
     * @return CommonDialog
     */
    public CommonDialog setTitleValue(String titleValue) {
        this.titleValue = titleValue;
        return this;
    }

    /**
     * desc: 设置弹窗内容
     *
     * @param contentValue 弹窗内容文本
     * @return CommonDialog
     */
    public CommonDialog setContentValue(String contentValue) {
        this.contentValue = contentValue;
        return this;
    }

    /**
     * desc: 设置左侧按钮内容
     *
     * @param leftValue  左边按钮值
     * @return CommonDialog
     */
    public CommonDialog setLeftBtnValue(String leftValue) {
        this.leftValue = leftValue;
        return this;
    }

    /**
     * desc: 设置右侧按钮内容
     *
     * @param rightValue 右边按钮文本内容
     * @return CommonDialog
     */
    public CommonDialog setRightBtnValue(String rightValue) {
        this.rightValue = rightValue;
        return this;
    }

    /**
     * desc: 动态设置shape
     *
     * @param view  视图
     * @param color 16进制颜色值
     */
    private void setsShape(View view, String color) {
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(60);
        shape.setColor(Color.parseColor("#3090FF"));
        shape.setShape(GradientDrawable.RECTANGLE);
        view.setBackground(shape);
    }
}