package com.example.look.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 绘制画板
 */
public class SignBoardView extends View {
    /*4个像素点*/
    private int beierThreshold = 4;
    private float x = 0;
    private float y = 0;
    /*画笔*/
    private Paint mPaint;
    /*宽度*/
    private int strokeWidth = 10;

    /*yanbse*/
    private int color = Color.BLACK;

    /*当前笔画*/
    private Path path;

    private int state = State.CLEAR;

    private interface State {
        /*画板可以使用了*/
        int START = 0;

        /*停止使用画板*/
        int STOP = 1;
        /*清空画板*/
        int CLEAR = 2;
    }

    private List<EveryPenPath> everyPenPaths = new ArrayList<>();

    /*每一个笔画*/
    private static class EveryPenPath {
        public Path path;
    }

    public SignBoardView(Context context) {
        super(context);
    }

    public SignBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SignBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initPaint() {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setStrokeWidth(strokeWidth);
            mPaint.setColor(color);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setAntiAlias(true);
            mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        }
    }

    public void start() {
        state = State.START;
        initPaint();

    }

    /*停止使用*/
    public void stop() {
        state = State.STOP;
    }

    /*清空画板*/
    public void clear() {
        state = State.CLEAR;
        for (int i = everyPenPaths.size() - 1; i >= 0; i--) {
            EveryPenPath everyPenPath = everyPenPaths.get(i);
            everyPenPath.path.reset();
            everyPenPath.path.close();
            everyPenPath.path = null;

        }
        everyPenPaths.clear();
        invalidate();

    }


    public void back() {
        int count = everyPenPaths.size();
        if (count < 1)
            return;
        EveryPenPath everyPenPath = everyPenPaths.get(count - 1);
        everyPenPath.path.reset();
        everyPenPath.path.close();
        everyPenPath.path = null;
        everyPenPaths.remove(count - 1);
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (state == State.START) {

            /*先绘制完整笔画*/
            for (EveryPenPath e : everyPenPaths) {
                canvas.drawPath(e.path, mPaint);
            }
            //当前进行中的  path!=null
            if (path != null) {
                canvas.drawPath(path, mPaint);
            }

        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (state == State.START) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                actionUp(event);
                invalidate();
                return true;
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                actionMove(event);
                invalidate();
                return true;
            }
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                actionDown(event);
                invalidate();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }


    private void actionUp(MotionEvent event) {
        actionMove(event);
        /*构成一个笔画*/
        EveryPenPath everyPenPath = new EveryPenPath();
        everyPenPath.path = path;
        everyPenPaths.add(everyPenPath);
        //将当前画笔置位null;
        path = null;

    }

    /**/
    private void actionMove(MotionEvent event) {
        /*每次移动去绘制贝塞尔曲线*/
        float cX = event.getX();
        float cY = event.getY();
        float dX = Math.abs(cX - x);//变化量
        float dY = Math.abs(cY - y);

        if (dX >= beierThreshold || dY >= beierThreshold) {
            float rX = x + (cX - x) / 2;
            float rY = y + (cY - y) / 2;
            path.quadTo(rX, rY, cX, cY);
            //下次的x 域y 将重新计算
            x = cX;
            y = cY;
        }
    }

    /*开始时*/
    private void actionDown(MotionEvent event) {
        path = new Path();
        x = event.getX();
        y = event.getY();
        path.moveTo(x, y);
    }


    public void setBeierThreshold(int beierThreshold) {
        this.beierThreshold = beierThreshold;
    }


    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }


    public void setColor(int color) {
        this.color = color;
    }

    public Bitmap getResult(int bgColor) {
        if (everyPenPaths.size() == 0)
            return null;
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(bgColor);
        for (int i = 0; i < everyPenPaths.size(); i++) {
            if (mPaint == null) {
                initPaint();

            }
            canvas.drawPath(everyPenPaths.get(i).path, mPaint);
        }
        return bitmap;
    }

    public Bitmap getResult() {
        return getResult(Color.WHITE);
    }
}