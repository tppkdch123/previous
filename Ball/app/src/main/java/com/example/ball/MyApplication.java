package com.example.ball;

import android.app.Application;
import android.content.Context;

/**
 * Created by 世哲 on 2017/7/16.
 */

public class MyApplication extends Application {
    private static Context c;
    public MyApplication(){}
    @Override
    public void onCreate(){
        super.onCreate();
        c=getApplicationContext();
    }
    public static Context getContext(){
        return c;
    }
}