package com.example.look.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.look.R;

import java.util.Objects;

/**
 * desc: web弹窗
 * <p>
 * Created by hxj on 2024/7/29
 */
public class UrlDialog extends Dialog {

    private String txt;
    public OnCopyClickListener onCopyClickListener;

    public UrlDialog(Context context, String txt) {
        super(context);
        this.txt = txt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_url);
        setCanceledOnTouchOutside(false);

        WindowManager.LayoutParams lp = Objects.requireNonNull(getWindow()).getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);

        TextView tvContent = findViewById(R.id.tv_url_txt);
        TextView tvCancel = findViewById(R.id.tv_url_cancel);
        TextView tvCopy = findViewById(R.id.tv_url_copy);
        tvContent.setText(txt);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCopyClickListener != null) {
                    onCopyClickListener.onCopyClick();
                }
            }
        });
    }

    public UrlDialog setOnCopyClickListener(OnCopyClickListener onCopyClickListener) {
        this.onCopyClickListener = onCopyClickListener;
        return this;
    }

    public interface OnCopyClickListener {
        /**
         * 复制内容
         */
        void onCopyClick();
    }

}