package com.example.look.views;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.look.R;
import com.example.look.utils.MLog;


/**
 * activity生命周期测试
 */
public class BLifeCycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MLog.d("生命周期=B页调用onCreate方法");
        setContentView(R.layout.activity_b_life_cycle);

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
        MLog.d("生命周期=B页调用onStart方法");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MLog.d("生命周期=B页调用onResume方法");
    }


    @Override
    protected void onPause() {
        super.onPause();
        MLog.d("生命周期=B页调用onPause方法");
    }

    @Override
    protected void onStop() {
        super.onStop();
        MLog.d("生命周期=B页调用onStop方法");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MLog.d("生命周期=B页调用onDestroy方法");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MLog.d("生命周期=B页调用onRestart方法");
    }
}