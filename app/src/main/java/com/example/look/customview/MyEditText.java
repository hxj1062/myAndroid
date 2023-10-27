package com.example.look.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.EditText;

import com.example.look.R;


public class MyEditText extends EditText {

    private Paint paint; // 下划线画笔

    private TextPaint textPaint; // 绘制计数的画笔

    private int lineY; // 下划线Y坐标开始

    private int lineColor; // 下划线颜色

    private int count; // 当前输入字符数

    private StringBuffer countString; // 获取技术结果的字符串

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyEditText);
        lineColor = typedArray.getColor(R.styleable.MyEditText_lineColor, Color.BLUE);
        typedArray.recycle();
        countString = new StringBuffer();
        super.setHintTextColor(Color.LTGRAY);

        paint = new Paint();
        paint.setColor(lineColor);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new TextPaint(paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(40);
    }

}
