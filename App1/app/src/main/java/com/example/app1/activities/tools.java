package com.example.app1.activities;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.app1.R;

/**
 * Created by 世哲 on 2017/8/10.
 */

public class tools {
    public static void golive(Activity A,int contentView,String thing){
        A.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        A.setContentView(contentView);
        A.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.menu_layout);
        ((TextView)A.findViewById(R.id.textView18)).setText(thing);
    }
    public static void golive2(Activity A,int contentView,String thing){
        A.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        A.setContentView(contentView);
        A.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.all_plates);
        ((TextView)A.findViewById(R.id.textView21)).setText(thing);
    }
    public static void golive3(Activity A,int contentView,String thing){
        A.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        A.setContentView(contentView);
        A.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.response_layout);
        ((TextView)A.findViewById(R.id.textView22)).setText(thing);
    }
    public static void golive4(Activity A,int contentView,String thing){
        A.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        A.setContentView(contentView);
        A.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.send_response);
        ((TextView)A.findViewById(R.id.textView23)).setText(thing);
    }
}
