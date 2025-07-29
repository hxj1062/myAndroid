package com.example.look.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.example.look.R;


public class FloatingBtnUtil {
    private Context context;
    private WindowManager windowManager;
    private ImageView floatingButton;
    private WindowManager.LayoutParams params;
    private int screenWidth;
    private int screenHeight;
    private int statusBarHeight;
    private boolean isRightSide = true;
    private OnClickListener onClickListener;
    private OnLongClickListener onLongClickListener;

    public FloatingBtnUtil(Activity activity) {
        this.context = activity;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        // 获取屏幕尺寸
        Point size = new Point();
        windowManager.getDefaultDisplay().getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        // 获取状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }

        // 创建悬浮按钮
        createFloatingBtn();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void createFloatingBtn() {
        floatingButton = new ImageView(context);
        floatingButton.setImageResource(R.drawable.ic_ai); // 设置按钮图标
//        floatingButton.setBackgroundResource(R.drawable.bg_login_gray); // 设置按钮背景

        // 设置布局参数
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                        WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP | Gravity.START;
//        params.x = screenWidth - dpToPx(60); // 初始位置靠右
        params.x = screenWidth; // 初始位置靠右
        params.y = screenHeight - dpToPx(130); // 初始位置垂直居中

        // 设置触摸监听器
        floatingButton.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;
            private long touchDownTime;
            private boolean isLongClickHandled = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchDownTime = System.currentTimeMillis();
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        isLongClickHandled = false;

                        // 长按检测
                        v.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (onLongClickListener != null &&
                                        System.currentTimeMillis() - touchDownTime > 500) {
                                    isLongClickHandled = true;
                                    onLongClickListener.onLongClick();
                                }
                            }
                        }, 500);
                        return true;

                    case MotionEvent.ACTION_UP:
                        // 判断是点击还是拖动
                        long touchDuration = System.currentTimeMillis() - touchDownTime;
                        float dx = Math.abs(event.getRawX() - initialTouchX);
                        float dy = Math.abs(event.getRawY() - initialTouchY);

                        if (!isLongClickHandled && touchDuration < 500 && dx < 10 && dy < 10) {
                            // 处理点击事件
                            if (onClickListener != null) {
                                onClickListener.onClick();
                            }
                        }

                        // 自动贴边
                        animateToEdge();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        if (isLongClickHandled) {
                            return true;
                        }

                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);

                        // 限制悬浮窗不超出屏幕范围
                        if (params.x < 0) params.x = 0;
                        if (params.x > screenWidth - floatingButton.getWidth()) {
                            params.x = screenWidth - floatingButton.getWidth();
                        }
                        if (params.y < 0) params.y = 0;
                        if (params.y > screenHeight - floatingButton.getHeight() - statusBarHeight) {
                            params.y = screenHeight - floatingButton.getHeight() - statusBarHeight;
                        }

                        windowManager.updateViewLayout(floatingButton, params);
                        return true;
                }
                return false;
            }
        });
    }

    private int dpToPx(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public void show() {
        if (floatingButton.getParent() == null) {
            try {
                windowManager.addView(floatingButton, params);
                android.util.Log.d("FloatingBtnUtil==", "流程3AddView");
            } catch (Exception e) {
                android.util.Log.e("FloatingBtnUtil==", "流程4ErrorAddView=" + e.getMessage());
            }
        }
    }

    public void hide() {
        if (floatingButton.getParent() != null) {
            try {
                windowManager.removeView(floatingButton);
                android.util.Log.d("FloatingBtnUtil==", "流程4RemoveView");
            } catch (Exception e) {
                android.util.Log.e("FloatingBtnUtil==", "流程5ErrorRemoveView=" + e.getMessage());
            }
        }
    }

    private void animateToEdge() {
        int startX = params.x;
        int endX;

        // 判断最终停靠在左侧还是右侧
        isRightSide = startX > screenWidth / 2;

//        int halfWidth =  floatingButton.getWidth() / 2;
//        endX = isRightSide ?
//                screenWidth - halfWidth : -halfWidth;
        endX = isRightSide ?
                screenWidth - dpToPx(10) - dpToPx(16) : dpToPx(16);

        ValueAnimator animator = ValueAnimator.ofInt(startX, endX);
        animator.setDuration(300);
        animator.setInterpolator(new OvershootInterpolator(1.2f));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.x = (int) animation.getAnimatedValue();
                if (floatingButton.getParent() != null) {
                    windowManager.updateViewLayout(floatingButton, params);
                }
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // 动画结束后隐藏半边
            }
        });
        animator.start();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.onClickListener = listener;
    }

    public void setOnLongClickListener(OnLongClickListener listener) {
        this.onLongClickListener = listener;
    }

    public interface OnClickListener {
        void onClick();
    }

    public interface OnLongClickListener {
        void onLongClick();
    }
}    