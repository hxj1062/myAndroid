package com.example.look.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.look.R;


/**
 * activity生命周期测试
 */
public class BLifeCycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_life_cycle);
        Log.e("生命周期", "----BB页面调用了onCreate方法");

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
        Log.e("生命周期", "----BB页面调用了onStar方法");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("生命周期", "----BB页面调用了onResume方法");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.e("生命周期", "----BB页面调用了onPause方法");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("生命周期", "----BB页面调用了onStop方法");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("生命周期", "----BB页面调用了onDestroy方法");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("生命周期", "----BB页面调用了onRestart方法");
    }
}