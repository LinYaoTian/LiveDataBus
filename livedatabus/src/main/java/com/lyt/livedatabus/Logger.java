package com.lyt.livedatabus;

import android.util.Log;

/**
 * 日志工具类
 * @author linyaotian
 */
public class Logger {
    private final static boolean DEBUG = false;

    private final static String TAG = "LiveDataBus";

    public static void d(String tag, String msg){
        if (DEBUG){
            Log.d(TAG, tag + "——" +msg);
        }
    }

    public static void v(String tag, String msg){
        if (DEBUG){
            Log.v(TAG, tag + "——" +msg);
        }
    }

    public static void i(String tag, String msg){
        if (DEBUG){
            Log.i(TAG, tag + "——" +msg);
        }
    }

    public static void w(String tag, String msg){
        if (DEBUG){
            Log.w(TAG, tag + "——" +msg);
        }
    }

    public static void e(String tag, String msg){
        if (DEBUG){
            Log.e(TAG, tag + "——" +msg);
        }
    }

}
