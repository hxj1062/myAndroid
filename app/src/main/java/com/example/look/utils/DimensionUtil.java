package com.example.look.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class DimensionUtil {

    /**
     * desc: dp转px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * desc: px转dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * desc: 获取屏幕宽度
     */
    public static DisplayMetrics getScreeMessage(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

}

