package com.example.lsnnic.wechat_app.utils;

import android.util.Log;

import com.example.lsnnic.wechat_app.BuildConfig;

public class L {

    private static final String TAG = "lsnnic";

    private static boolean sDebug = BuildConfig.DEBUG;

    public static void d(String msg,Object... args){
        if(!sDebug){
            return;
        }
        Log.d(TAG,String.format(msg,args));
    }
}
