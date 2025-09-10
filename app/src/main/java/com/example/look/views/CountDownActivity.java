package com.example.look.views;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.look.MyBaseActivity;
import com.example.look.R;

 /**
  * desc: 两种倒计时方式
  * <p>
  * Created by hxj on
  */
public class CountDownActivity extends MyBaseActivity {

    TextView tvCount111, tvCount222;
    private int seconds = 10;
    // CountDownTimer方式
    private CountDownTimer countDownTimer;
    // Handler+Runnable方式
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (seconds > 0) {
                seconds--;
                tvCount111.setText("剩余时间:" + seconds + "秒");
                handler.postDelayed(this, 1000); // 1秒后再次执行
            } else {
                tvCount111.setEnabled(true);
                tvCount111.setText("开始计时");
                seconds = 10;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        buildActionBar();
        tvCount111 = findViewById(R.id.tv_count1);
        tvCount222 = findViewById(R.id.tv_count2);

        tvCount111.setOnClickListener(v -> {
            if (seconds == 10) { // 检查是否需要重置倒计时
                tvCount111.setEnabled(false);
                tvCount111.setText("剩余时间：" + seconds + "秒");
                handler.post(runnable);
            }
        });

        tvCount222.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countDownTimer == null) {
                    startCountdown();
                }
            }
        });
    }

    private void startCountdown() {
        countDownTimer = new CountDownTimer(10 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCount222.setText("剩余时间：" + (millisUntilFinished / 1000) + "秒");
            }

            @Override
            public void onFinish() {
                tvCount222.setEnabled(true);
                tvCount222.setText("开始计时");
                countDownTimer = null; // 重置计时器
            }
        }.start();
        tvCount222.setText("剩余时间：" + (10 * 1000 / 1000) + "秒");
        tvCount222.setEnabled(false);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable); // 确保在Activity销毁时移除回调
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}