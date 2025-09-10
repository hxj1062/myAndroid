package com.example.look.views;

import android.os.Bundle;
import android.view.View;

import com.example.look.MyBaseActivity;
import com.example.look.R;
import com.example.look.utils.CommonUtils;


/**
 * desc: 按钮的点击效果
 * <p>
 * Created by hxj on
 */
public class BtnClickEffectActivity extends MyBaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_click_effect);
        buildActionBar();
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_testA).setOnClickListener(this);
        findViewById(R.id.btn_testB).setOnClickListener(this);
        findViewById(R.id.btn_testC).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // style="@style/Widget.AppCompat.Button"
            case R.id.btn_testA:
                showToast("设置了阴影效果style", 1);
                break;
            // style="@style/Widget.AppCompat.Button"
            // android:foreground="?android:attr/selectableItemBackground"
            case R.id.btn_testB:
                showToast("设置阴影效果style+波纹效果forground", 1);
                break;
            case R.id.btn_testC:
                showToast("设置阴影效果style+波纹效果forground+shape", 1);
                break;
            default:
                break;
        }
    }
}