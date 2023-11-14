package com.example.look.views;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.look.R;
import com.example.look.utils.CommonUtils;

/**
 * @description: 按钮的点击效果
 */
public class BtnClickEffectActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_click_effect);
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
                CommonUtils.showToast(BtnClickEffectActivity.this, "设置了阴影效果style");
                break;
            // style="@style/Widget.AppCompat.Button"
            // android:foreground="?android:attr/selectableItemBackground"
            case R.id.btn_testB:
                CommonUtils.showToast(BtnClickEffectActivity.this, "设置阴影效果style+波纹效果forground");
                break;
            case R.id.btn_testC:
                CommonUtils.showToast(BtnClickEffectActivity.this, "设置阴影效果style+波纹效果forground+shape");
                break;
            default:
                break;
        }
    }
}