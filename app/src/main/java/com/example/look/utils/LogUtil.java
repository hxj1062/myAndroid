package com.example.look.utils;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogUtil {

    // 将异常堆栈信息转为字符串
    public static String getStackTraceString(Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        // 使用StringWriter捕获堆栈信息
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw); // 将堆栈信息写入PrintWriter
        pw.close();
        return sw.toString(); // 返回字符串形式的堆栈信息
    }

    // 直接通过Log.d打印异常堆栈
    public static void logStack(Throwable throwable , String tag) {
        String stackTrace = getStackTraceString(throwable);
        Log.d(tag, "异常堆栈信息:\n" + stackTrace);
    }
}