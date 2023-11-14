package com.example.look.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.look.R;
import com.example.look.adpter.RoutePointAdapter;
import com.example.look.customview.LinePlanDialog;
import com.example.look.utils.CommonUtils;

public class ConfigureRouteActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_route);
        recyclerView = findViewById(R.id.rl_line_route);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RoutePointAdapter());
        findViewById(R.id.cl_test_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLinePlanDialog();
            }
        });
    }

    public void showLinePlanDialog() {
        LinePlanDialog noticeDialog = new LinePlanDialog(this, R.style.QrCodeDialog);
        noticeDialog.changeCustom(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonUtils.showToast(ConfigureRouteActivity.this, "选用自定义");
                    }
                }).changeSystem(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonUtils.showToast(ConfigureRouteActivity.this, "选用系统");
                    }
                }).
                goToReplenish(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ConfigureRouteActivity.this, RoutePointListActivity.class));
                        CommonUtils.showToast(ConfigureRouteActivity.this, "返回修改补货顺序");
                    }
                }).show();
    }

}
