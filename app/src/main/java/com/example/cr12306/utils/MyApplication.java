package com.example.cr12306.utils;

import android.app.Application;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;

public class MyApplication extends Application {
    private static Context context;
    private static FragmentActivity activity;

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }

    /**
     * 全局获取context
     * */
    public static Context getContext(){
        return context;
    }

    public static FragmentActivity getActivity(){
        return activity;
    }
}
