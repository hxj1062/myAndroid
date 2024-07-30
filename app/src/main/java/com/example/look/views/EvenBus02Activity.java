package com.example.look.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.look.R;
import com.example.look.bean.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * desc: 发布者先发送了事件，但是此时订阅者还未产生，一段时间后订阅者才订阅了该事件，也就是使得在发送事件之后订阅者再订阅该事件也能收到该事件。
 * <p>
 */
public class EvenBus02Activity extends AppCompatActivity {

    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_bus02);
        tvContent = findViewById(R.id.tv_busText2);
        EditText edtContent2 = findViewById(R.id.edt_content2);

        EventBus.getDefault().register(this);

        findViewById(R.id.btn_bus2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread("posting") {
                    @Override
                    public void run() {
                        super.run();
                        EventBus.getDefault().post(new MessageEvent(edtContent2.getText().toString()));
                    }
                }.start();
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent06(MessageEvent event) {
        Log.d("线程名", "Event06:" + Thread.currentThread().getName());
        tvContent.setText(event.getMessage());
        EventBus.getDefault().removeStickyEvent(event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}