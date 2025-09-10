package com.example.look.views;

import android.os.Bundle;
import android.view.View;

import com.example.look.MyBaseActivity;
import com.example.look.R;
import com.example.look.utils.MLog;


/**
 * desc: activity生命周期测试
 * <p>
 * Created by hxj on
 */
public class Cycle02Activity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MLog.d("生命周期=B页调用onCreate");
        setContentView(R.layout.activity_cycle02);
        buildActionBar();

        findViewById(R.id.btn_Bjump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        MLog.d("生命周期=B页调用onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MLog.d("生命周期=B页调用onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        MLog.d("生命周期=B页调用onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        MLog.d("生命周期=B页调用onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MLog.d("生命周期=B页调用onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MLog.d("生命周期=B页调用onRestart");
    }
}