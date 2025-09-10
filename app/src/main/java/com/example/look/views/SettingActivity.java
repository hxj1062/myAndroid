package com.example.look.views;

import android.os.Bundle;
import android.widget.TextView;

import com.example.look.MyBaseActivity;
import com.example.look.R;

public class SettingActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        buildActionBar();
        TextView tvUserName = findViewById(R.id.tv_mine_userName);
        TextView tvLength = findViewById(R.id.tv_txtLengh);
        String userName = "账户文案显示显货架上的机会覅哦上帝示变";
        tvUserName.setText(userName);
        tvLength.setText("账户名称长度:" + userName.length());
    }

}
