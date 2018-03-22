package com.example.app1;

import android.app.Application;
import android.content.Context;

/**
 * Created by 世哲 on 2017/8/2.
 */

public class MyApplication extends Application {
    private static Context context;
@Override
    public void onCreate(){
super.onCreate();
    context=this;
}
public static Context getContext(){
    return context;
}
}
