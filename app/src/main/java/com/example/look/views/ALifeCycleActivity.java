package com.example.look.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.look.R;
import com.example.look.utils.MLog;

/**
 * activity生命周期测试
 */
public class ALifeCycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MLog.d("生命周期=A页调用onCreate方法");
        setContentView(R.layout.activity_a_life_cycle);

        findViewById(R.id.btn_Ajump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentA = new Intent(ALifeCycleActivity.this, BLifeCycleActivity.class);
                startActivity(intentA);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        MLog.d("生命周期=A页调用onStar方法");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MLog.d("生命周期=A页调用onResume方法");
    }


    @Override
    protected void onPause() {
        super.onPause();
        MLog.d("生命周期=A页调用onPause方法");
    }

    @Override
    protected void onStop() {
        super.onStop();
        MLog.d("生命周期=A页调用onStop方法");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MLog.d("生命周期=A页调用onDestroy方法");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MLog.d("生命周期=A页调用onRestart方法");
    }
}