package com.example.look.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;

/**
 *  activity生命周期测试
 */
public class ALifeCycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_life_cycle);
        Log.v("生命周期", "----AA页面调用了onCreate方法");

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
        Log.v("生命周期", "----AA页面调用了onStar方法");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("生命周期", "----AA页面调用了onResume方法");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.v("生命周期", "----AA页面调用了onPause方法");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("生命周期", "----AA页面调用了onStop方法");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("生命周期", "----AA页面调用了onDestroy方法");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("生命周期", "----AA页面调用了onRestart方法");
    }
}