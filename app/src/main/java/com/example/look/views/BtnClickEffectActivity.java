package com.example.look.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;
/**
 *@description: 按钮的点击效果
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
                Toast.makeText(this, "设置了阴影效果style", Toast.LENGTH_LONG).show();
                break;
            // style="@style/Widget.AppCompat.Button"
            // android:foreground="?android:attr/selectableItemBackground"
            case R.id.btn_testB:
                Toast.makeText(this, "设置阴影效果style+波纹效果forground",
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_testC:
                Toast.makeText(this, "设置阴影效果style+波纹效果forground+shape",
                        Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}