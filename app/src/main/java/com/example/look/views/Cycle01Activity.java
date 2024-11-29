package com.example.look.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.look.R;
import com.example.look.utils.MLog;

/**
 * desc: activity生命周期测试
 * <p>
 * Created by hxj on
 */
public class Cycle01Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MLog.d("生命周期=A页调用onCreate");
        setContentView(R.layout.activity_cycle01);

        findViewById(R.id.btn_Ajump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cycle01Activity.this, Cycle02Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        MLog.d("生命周期=A页调用onStar");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MLog.d("生命周期=A页调用onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        MLog.d("生命周期=A页调用onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        MLog.d("生命周期=A页调用onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MLog.d("生命周期=A页调用onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MLog.d("生命周期=A页调用onRestart");
    }
}