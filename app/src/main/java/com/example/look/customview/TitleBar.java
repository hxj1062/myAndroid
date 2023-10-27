package com.example.look.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.look.R;


public class TitleBar extends FrameLayout {

    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvMore;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    // 初始化视图
    private void initView(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.title_bar, this);
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        tvMore = findViewById(R.id.tv_more);
        init(context, attrs);
    }

    // 初始化资源文件
    private void init(final Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        String title = typedArray.getString(R.styleable.TitleBar_title);
        int leftIcon = typedArray.getResourceId(R.styleable.TitleBar_left_icon, R.drawable.head_back);
        String rightTxt = typedArray.getString(R.styleable.TitleBar_right_txt);
        boolean auto_get_line = typedArray.getBoolean(R.styleable.TitleBar_auto_get_line, true);
        ivBack.setImageResource(leftIcon);
        tvTitle.setText(title);
        tvMore.setText(rightTxt);
        // 添加属性控制执行流程
        if (auto_get_line) {
            testPrint();
        }
        typedArray.recycle();
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity) context).finish();
            }
        });
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setLeftIconClickListener(OnClickListener clickListener) {
        ivBack.setOnClickListener(clickListener);
    }

    public void setRightTxtClickListener(OnClickListener clickListener) {
        tvMore.setOnClickListener(clickListener);
    }


    private void testPrint() {
        Log.d("新增属性", "添加auto_get_line属性");
    }
}
