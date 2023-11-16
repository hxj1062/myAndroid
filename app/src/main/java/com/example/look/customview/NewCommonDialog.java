package com.example.look.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.look.R;

import java.util.Objects;

public class NewCommonDialog extends Dialog {

    private String content, leftTxt, rightTxt, oneTxt;
    private Context context;
    int btnNum = 1;  // 按钮个数
    public OnTwoClickListener onTwoClickListener;
    public OnOneClickListener onOneClickListener;

    public NewCommonDialog(@NonNull Context context, int themeResId, int btnNum) {
        super(context, themeResId);
        this.context = context;
        this.btnNum = btnNum;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common_show);
        setCanceledOnTouchOutside(false);

        WindowManager.LayoutParams lp = Objects.requireNonNull(getWindow()).getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);

        TextView tvCommonTitle = findViewById(R.id.tv_common_title);
        LinearLayout oneBtn = findViewById(R.id.one_btn);
        LinearLayout twoBtn = findViewById(R.id.two_btn);
        TextView oneBtnTxt = findViewById(R.id.one_btn_txt);
        TextView twoBtnLeft = findViewById(R.id.two_btn_left);
        TextView twoBtnRight = findViewById(R.id.two_btn_right);

        if (!TextUtils.isEmpty(content)) {
            tvCommonTitle.setText(content);
        }
        if (!TextUtils.isEmpty(oneTxt)) {
            oneBtnTxt.setText(oneTxt);
        }
        if (!TextUtils.isEmpty(leftTxt)) {
            twoBtnLeft.setText(leftTxt);
        }
        if (!TextUtils.isEmpty(rightTxt)) {
            twoBtnRight.setText(rightTxt);
        }

        if (btnNum == 1) {
            oneBtn.setVisibility(View.VISIBLE);
        } else {
            twoBtn.setVisibility(View.VISIBLE);
        }

        oneBtnTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOneClickListener != null) {
                    onOneClickListener.oneBtnClick();
                }
            }
        });
        twoBtnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTwoClickListener != null) {
                    onTwoClickListener.onLeftClick();
                }
            }
        });
        twoBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTwoClickListener != null) {
                    onTwoClickListener.onRightClick();
                }
            }
        });
    }


    /**
     * desc: 设置弹窗内容
     *
     * @param content 内容
     */
    public NewCommonDialog setsContent(String content) {
        this.content = content;
        return this;
    }


    /**
     * desc: 设置左边按钮文字
     *
     * @param leftTxt 文字
     */
    public NewCommonDialog setsBtnLeft(String leftTxt) {
        this.leftTxt = leftTxt;
        return this;
    }

    /**
     * desc: 设置右边按钮文字
     *
     * @param rightTxt 文字
     */
    public NewCommonDialog setsBtnRight(String rightTxt) {
        this.rightTxt = rightTxt;
        return this;
    }

    /**
     * desc: 设置一个按钮文字
     *
     * @param oneTxt 文字
     */
    public NewCommonDialog setsBtnOne(String oneTxt) {
        this.oneTxt = oneTxt;
        return this;
    }

    public NewCommonDialog setOnTwoClickListener(OnTwoClickListener onTwoClickListener) {
        this.onTwoClickListener = onTwoClickListener;
        return this;
    }

    public NewCommonDialog setOnOneClickListener(OnOneClickListener onOneClickListener) {
        this.onOneClickListener = onOneClickListener;
        return this;
    }


    public interface OnTwoClickListener {
        /**
         * 点击取消按钮事件
         */
        void onLeftClick();

        /**
         * 点击确定按钮事件
         */
        void onRightClick();


    }

    public interface OnOneClickListener {

        /**
         * 一个按钮点击
         */
        void oneBtnClick();
    }

}
