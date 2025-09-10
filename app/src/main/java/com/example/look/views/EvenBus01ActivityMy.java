package com.example.look.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.look.MyBaseActivity;
import com.example.look.R;
import com.example.look.bean.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EvenBus01ActivityMy extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_bus01);
        buildActionBar();
        EventBus.getDefault().register(this);
        EditText edtContent1 = findViewById(R.id.edt_content1);
        findViewById(R.id.tv_bus1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new MessageEvent(edtContent1.getText().toString()));
                Intent intent = new Intent(EvenBus01ActivityMy.this, EvenBus02Activity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * desc: 订阅者方法将在发布事件所在的线程中被调用。这是 默认的线程模式。事件的传递是同步的，
     * 一旦发布事件，所有该模式的订阅者方法都将被调用。这种线程模式意味着最少的性能开销，
     * 因为它避免了线程的切换。因此，对于不要求是主线程并且耗时很短的简单任务推荐使用该模式。
     * 使用该模式的订阅者方法应该快速返回，以避免阻塞发布事件的线程，这可能是主线程。
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEvent01(MessageEvent event) {
        Log.d("线程名", "Event01:" + Thread.currentThread().getName());
        Log.d("线程名", "Event01:" + event.getMessage());
    }

    /**
     * desc: 订阅者方法将在主线程（UI线程）中被调用。因此，可以在该模式的订阅者方法中直接更新UI界面。
     * 如果发布事件的线程是主线程，那么该模式的订阅者方法将被直接调用。
     * 使用该模式的订阅者方法必须快速返回，以避免阻塞主线程。
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent02(MessageEvent event) {
        Log.d("线程名", "Event02:" + Thread.currentThread().getName());
    }

    /**
     * desc: 订阅者方法将在主线程（UI线程）中被调用。因此，可以在该模式的订阅者方法中直接更新UI界面。
     * 事件将先进入队列然后才发送给订阅者，所以发布事件的调用将立即返回。这使得事件的处理保持严格的串行顺序。
     * 使用该模式的订阅者方法必须快速返回，以避免阻塞主线程。
     */
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onMessageEvent03(MessageEvent event) {
        Log.d("线程名", "Event03:" + Thread.currentThread().getName());
    }

    /**
     * desc: 订阅者方法将在后台线程中被调用。如果发布事件的线程不是主线程，那么订阅者方法将直接在该线程中被调用。
     * 如果发布事件的线程是主线程，那么将使用一个单独的后台线程，该线程将按顺序发送所有的事件。
     * 使用该模式的订阅者方法应该快速返回，以避免阻塞后台线程。
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent04(MessageEvent event) {
        Log.d("线程名", "Event04:" + Thread.currentThread().getName());
    }

    /**
     * desc: 订阅者方法将在一个单独的线程中被调用。因此，发布事件的调用将立即返回。如果订阅者方法的执行需要一些时间，
     * 例如网络访问，那么就应该使用该模式。避免触发大量的长时间运行的订阅者方法，
     * 以限制并发线程的数量。EventBus使用了一个线程池来有效地重用已经完成调用订阅者方法的线程。
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEvent05(MessageEvent event) {
        Log.d("线程名", "Event05:" + Thread.currentThread().getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}