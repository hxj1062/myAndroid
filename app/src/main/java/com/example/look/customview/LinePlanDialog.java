package com.example.look.customview;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.look.R;

import java.util.Objects;

public class LinePlanDialog extends Dialog implements View.OnClickListener {

    private String time01, time02, distance01, distance02, lightNum01, lightNum02;
    private String fee01, fee02, travelTime01, travelTime02, replenishTime01, replenishTime02;
    private View.OnClickListener backReplenishListener, setCustomListener, setSystemListener;

    public LinePlanDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_line_plan);
        setCanceledOnTouchOutside(false);

        WindowManager.LayoutParams lp = Objects.requireNonNull(getWindow()).getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);

        TextView tvTime1 = findViewById(R.id.tv_time1);
        TextView tvTime2 = findViewById(R.id.tv_time2);
        TextView tvDistance1 = findViewById(R.id.tv_distance1);
        TextView tvDistance2 = findViewById(R.id.tv_distance2);
        TextView tvLightNum1 = findViewById(R.id.tv_light_num1);
        TextView tvLightNum2 = findViewById(R.id.tv_light_num2);
        TextView tvFee1 = findViewById(R.id.tv_fee1);
        TextView tvFee2 = findViewById(R.id.tv_fee2);
        TextView tvTravelTime1 = findViewById(R.id.tv_travel_time1);
        TextView tvTravelTime2 = findViewById(R.id.tv_travel_time2);
        TextView tvReplenishTime1 = findViewById(R.id.tv_replenish_time1);
        TextView tvReplenishTime2 = findViewById(R.id.tv_replenish_time2);
        Button btnBackReplenish = findViewById(R.id.btn_back_replenish);
        TextView btnCustom = findViewById(R.id.btn_custom);
        TextView btnSystem = findViewById(R.id.btn_system);

        if (!TextUtils.isEmpty(time01)) {
            tvTime1.setText(time01);
        }
        if (!TextUtils.isEmpty(time02)) {
            tvTime2.setText(time02);
        }
        if (!TextUtils.isEmpty(distance01)) {
            tvDistance1.setText(distance01);
        }
        if (!TextUtils.isEmpty(distance02)) {
            tvDistance2.setText(distance02);
        }
        if (!TextUtils.isEmpty(lightNum01)) {
            tvLightNum1.setText(lightNum01);
        }
        if (!TextUtils.isEmpty(lightNum02)) {
            tvLightNum2.setText(lightNum02);
        }
        if (!TextUtils.isEmpty(fee01)) {
            tvFee1.setText(fee01);
        }
        if (!TextUtils.isEmpty(fee02)) {
            tvFee2.setText(fee02);
        }
        if (!TextUtils.isEmpty(travelTime01)) {
            tvTravelTime1.setText(travelTime01);
        }
        if (!TextUtils.isEmpty(travelTime02)) {
            tvTravelTime2.setText(travelTime02);
        }
        if (!TextUtils.isEmpty(replenishTime01)) {
            tvReplenishTime1.setText(replenishTime01);
        }
        if (!TextUtils.isEmpty(replenishTime02)) {
            tvReplenishTime2.setText(replenishTime02);
        }
        btnCustom.setOnClickListener(this);
        btnSystem.setOnClickListener(this);
        btnBackReplenish.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_custom:  // 自定义
                this.dismiss();
                setCustomListener.onClick(v);
                break;
            case R.id.btn_system:  // 系统
                this.dismiss();
                setSystemListener.onClick(v);
                break;
            case R.id.btn_back_replenish: // 返回补货
                this.dismiss();
                backReplenishListener.onClick(v);
                break;
        }
    }


    /**
     * desc: 设置弹窗内容
     *
     * @param time01
     * @param time02
     */
    public LinePlanDialog setsContent(String time01, String time02, String distance01, String distance02, String lightNum01, String lightNum02, String fee01, String fee02, String travelTime01, String travelTime02, String replenishTime01, String replenishTime02) {
        this.time01 = time01;
        this.time02 = time02;
        this.distance01 = distance01;
        this.distance02 = distance02;
        this.lightNum01 = lightNum01;
        this.lightNum02 = lightNum02;
        this.fee01 = fee01;
        this.fee02 = fee02;
        this.travelTime01 = travelTime01;
        this.travelTime02 = travelTime02;
        this.replenishTime01 = replenishTime01;
        this.replenishTime02 = replenishTime02;
        return this;
    }


    /**
     * desc: 按钮事件
     *
     * @param backReplenishListener 监听事件
     */
    public LinePlanDialog goToReplenish(View.OnClickListener backReplenishListener) {
        this.backReplenishListener = backReplenishListener;
        return this;
    }

    /**
     * desc: 按钮事件
     *
     * @param setCustomListener 监听事件
     */
    public LinePlanDialog changeCustom(View.OnClickListener setCustomListener) {
        this.setCustomListener = setCustomListener;
        return this;
    }

    /**
     * desc: 按钮事件
     *
     * @param setSystemListener 监听事件
     */
    public LinePlanDialog changeSystem(View.OnClickListener setSystemListener) {
        this.setSystemListener = setSystemListener;
        return this;
    }

}
