package com.example.look.customview;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.look.R;
import com.example.look.utils.DimensionUtil;

import java.util.Objects;

public class NoticeDialog extends Dialog implements View.OnClickListener {

    private String title, content, color, imgUrl;
    private int flag = 1;
    private Context context;
    private View.OnClickListener lookDetailsListener;

    public NoticeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_notice);
        setCanceledOnTouchOutside(false);

        WindowManager.LayoutParams lp = Objects.requireNonNull(getWindow()).getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);

        TextView tvNoticeTitle = findViewById(R.id.tv_notice_title);
        TextView tvNoticeContent = findViewById(R.id.tv_notice_content);
        ImageView ivNoticeBanner000 = findViewById(R.id.iv_notice_banner000);
        ImageView ivNoticeCancel = findViewById(R.id.iv_notice_cancel);
        Button btnLookDetails = findViewById(R.id.btn_look_details);

        if (!TextUtils.isEmpty(title)) {
            tvNoticeTitle.setText(title);
        }

        if (!TextUtils.isEmpty(content)) {
            tvNoticeContent.setText(content);
        }

        if (imgUrl != null && !TextUtils.isEmpty(imgUrl)) {
            CornerTransform cornerTransform = new CornerTransform(context, DimensionUtil.dip2px(context, 10));
            cornerTransform.setNeedCorner(true, true, false, false);
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.background_img)
                    .error(R.drawable.background_img)
                    .transform(cornerTransform);
            Glide.with(context).asBitmap().load(R.drawable.ic_banner).apply(options).into(ivNoticeBanner000);
        }

        if (flag == 0) {
            btnLookDetails.setVisibility(View.GONE);
        } else {
            btnLookDetails.setVisibility(View.VISIBLE);
            setsShape(btnLookDetails, color);
        }

        ivNoticeCancel.setOnClickListener(this);
        btnLookDetails.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_notice_cancel:
                this.dismiss();
                break;
            case R.id.btn_look_details:
                lookDetailsListener.onClick(v);
                break;
        }
    }

    /**
     * desc: 设置弹窗标题
     *
     * @param title 字符串标题
     */
    public NoticeDialog setsTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * desc: 设置弹窗内容
     *
     * @param content 字符串内容
     */
    public NoticeDialog setsContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * desc: 设置图片
     *
     * @param imgUrl 图片资源
     */
    public NoticeDialog setsImg(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    /**
     * desc: 设置图片
     *
     * @param color 16进制颜色值
     */
    public NoticeDialog setsColor(String color) {
        this.color = color;
        return this;
    }

    /**
     * desc: 设置按钮隐藏
     *
     * @param flag 0隐藏 1显示
     */
    public NoticeDialog setsBtnVisible(int flag) {
        this.flag = flag;
        return this;
    }

    /**
     * desc: 按钮事件
     *
     * @param lookDetailsListener 监听事件
     */
    public NoticeDialog lookDetails(View.OnClickListener lookDetailsListener) {
        this.lookDetailsListener = lookDetailsListener;
        return this;
    }

    /**
     * desc: 动态设置shape
     *
     * @param btn   按钮
     * @param color 16进制颜色值
     */
    private void setsShape(Button btn, String color) {

        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(60);
        if (!TextUtils.isEmpty(color)) {
            shape.setColor(Color.parseColor(color));
        } else {
            shape.setColor(Color.parseColor("#3090FF"));
        }
        shape.setShape(GradientDrawable.RECTANGLE);
        btn.setBackground(shape);
    }
}
