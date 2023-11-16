package com.example.look.views;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.look.R;
import com.example.look.adpter.RoutePointAdapter;
import com.example.look.customview.BottomListDlg;
import com.example.look.customview.LinePlanDialog;
import com.example.look.customview.NewCommonDialog;
import com.example.look.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class ConfigureRouteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ConstraintLayout lineLike;
    TextView startPoint, endPoint, tv_selected, tvDeparture, navigationPreference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_route);
        recyclerView = findViewById(R.id.rl_line_route);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RoutePointAdapter());
        startPoint = findViewById(R.id.tv_starting_point);
        endPoint = findViewById(R.id.tv_destination);
        tvDeparture = findViewById(R.id.tv_departure);
        navigationPreference = findViewById(R.id.tv_navigation_preference);
        lineLike = findViewById(R.id.cl_line_like);
        tv_selected = findViewById(R.id.tv_selected);

        findViewById(R.id.tv_hfasdu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoticeDialog111();
            }
        });
        findViewById(R.id.cl_starting_point).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(ConfigureRouteActivity.this, LineSearchActivity.class);
                startIntent.putExtra("address_select", "startPoint");
                startActivityForResult(startIntent, 16);
            }
        });
        findViewById(R.id.cl_destination).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent endIntent = new Intent(ConfigureRouteActivity.this, LineSearchActivity.class);
                endIntent.putExtra("address_select", "endPoint");
                startActivityForResult(endIntent, 16);
            }
        });
        findViewById(R.id.cl_go_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.hourMinuteDialog(ConfigureRouteActivity.this, "选择时间", "", new CommonUtils.OnConfirmListener() {
                    @Override
                    public void confirmTime(Dialog dialog, String title, int hour, int minute, int seconds) {
                        tvDeparture.setText(hour + ":" + minute + ":" + seconds);
                    }
                });
            }
        });
        findViewById(R.id.cl_path_point).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfigureRouteActivity.this, RoutePointListActivity.class));
            }
        });
        findViewById(R.id.rl_path_planning).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLinePlanDialog();
            }
        });
        showEnvDialog();
    }

    private void showNoticeDialog111() {
        NewCommonDialog noticeDialog = new NewCommonDialog(this, R.style.QrCodeDialog, 1);
        noticeDialog.setsContent("路径规划点位已超过50个\n请减少路径规划点位")
                .setOnTwoClickListener(new NewCommonDialog.OnTwoClickListener() {
                    @Override
                    public void onLeftClick() {

                        noticeDialog.dismiss();
                    }

                    @Override
                    public void onRightClick() {
                        noticeDialog.dismiss();
                    }
                })
                .show();
    }

    private void showEnvDialog() {
        List<BottomListDlg.BottomData> testData = new ArrayList<>();
        testData.add(new BottomListDlg.BottomData(0, "时间最短"));
        testData.add(new BottomListDlg.BottomData(1, "距离最短"));
        testData.add(new BottomListDlg.BottomData(2, "不走高速"));
        lineLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomListDlg bottomListDlg = new BottomListDlg(ConfigureRouteActivity.this);
                bottomListDlg.setOnItemClickListener(new BottomListDlg.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos, BottomListDlg.BottomData bean) {
                        navigationPreference.setText(testData.get(pos).text);
                    }
                });
                bottomListDlg.show(testData);
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
                    }
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 16) {
            if (resultCode == 16) {
                String result = data.getStringExtra("point_address");
                startPoint.setText(result);
            } else if (resultCode == 18) {
                String result = data.getStringExtra("point_address");
                endPoint.setText(result);
            }
        }
    }
}
