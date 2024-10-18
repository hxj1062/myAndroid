package com.example.look.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * desc: 日志打印封装
 * <p>
 */
public class MLog {

    /**
     * 设置日志级别，打印该级别以上
     * 如果级别为Debug,则显示Debug级别以上，不包括Verbose和Debug
     */
    public static int LOG_LEVEL = LogLevel.ALL;
    public static String LOG_TAG = "日志";

    public static void v(String msg) {
        v(setFlag(LOG_TAG), msg);
    }

    public static void v(String tag, String msg) {
        splitLog(LogLevel.VERBOSE, tag, msg);
    }

    public static void d(String msg) {
        d(setFlag(LOG_TAG), msg);
    }

    public static void d(String tag, String msg) {
        splitLog(LogLevel.DEBUG, tag, msg);
    }

    public static void i(String msg) {
        i(setFlag(LOG_TAG), msg);
    }

    public static void i(String tag, String msg) {
        splitLog(LogLevel.INFO, tag, msg);
    }

    public static void w(String msg) {
        w(setFlag(LOG_TAG), msg);
    }

    public static void w(String tag, String msg) {
        splitLog(LogLevel.WARN, tag, msg);
    }

    public static void e(String msg) {
        e(setFlag(LOG_TAG), msg);
    }

    public static void e(String tag, String msg) {
        splitLog(LogLevel.ERROR, tag, msg);
    }

//    private static String getTag() {
//        StackTraceElement element = (new Throwable().fillInStackTrace().getStackTrace())[2];
//        StringBuffer sb = new StringBuffer("=====")
//                .append(element.getClassName()).append(".")
//                .append(element.getMethodName()).append("():")
//                .append(element.getLineNumber());
//        return sb.toString().replace("com.mbox.cn.", "");
//    }

    /**
     * desc: 设置日志标识
     *
     * @param tag 标识
     */
    private static String setFlag(String tag) {
        try {
            // 获取当前线程的堆栈跟踪信息
            StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
            // 检查堆栈跟踪数组是否有足够的元素
            if (stackTraceElements.length > 2) {
                StackTraceElement element = stackTraceElements[2];
                // element.getLineNumber() MLog在哪行输出
                return tag + element.getLineNumber() + "行";
            } else {
                // 如果堆栈跟踪元素不足，返回错误信息
                return tag + "Error:堆栈跟踪元素不足";
            }
        } catch (Throwable t) {
            // 捕获并记录可能的异常
            return tag + "Error:正在检索堆栈跟踪" + t.getMessage();
        }
    }

    /**
     * desc: 将一个很长的日志信息分割成多个大小为3KB的片段，打印每个片段
     *
     * @param logLevel 日志级别
     * @param tag      标识
     * @param msg      日志信息
     */
    private static void splitLog(int logLevel, String tag, String msg) {
        if (LOG_LEVEL == LogLevel.NONE) {
            return;
        }
        if (!TextUtils.isEmpty(msg)) {
            msg = msg.trim();
            String logContent = "";
            boolean isFirst = true;
            int index = 0;
            int partSize = 3 * 1024;

            while (index < msg.length()) {
                if (msg.length() <= index + partSize) {
                    logContent = msg.substring(index);
                } else {
                    logContent = msg.substring(index, partSize + index);
                }
                index += partSize;
                if (!isFirst) {
                    tag = "";
                }
                printLog(logLevel, tag, logContent.trim());
                isFirst = false;
            }
        }
    }

    /**
     * desc: 打印日志
     *
     * @param logLevel 日志级别
     * @param tag      标识
     * @param msg      日志信息
     */
    private static void printLog(int logLevel, String tag, String msg) {
        switch (logLevel) {
            case LogLevel.VERBOSE:
                if (LogLevel.VERBOSE > LOG_LEVEL) {
                    Log.v(tag, msg);
                }
                break;
            case LogLevel.DEBUG:
                if (LogLevel.DEBUG > LOG_LEVEL) {
                    Log.d(tag, msg);
                }
                break;
            case LogLevel.INFO:
                if (LogLevel.INFO > LOG_LEVEL) {
                    Log.i(tag, msg);
                }
                break;
            case LogLevel.WARN:
                if (LogLevel.WARN > LOG_LEVEL) {
                    Log.w(tag, msg);
                }
                break;
            case LogLevel.ERROR:
                if (LogLevel.ERROR > LOG_LEVEL) {
                    Log.e(tag, msg);
                }
                break;
            default:
                break;
        }
    }
}
