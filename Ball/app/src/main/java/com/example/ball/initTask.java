package com.example.ball;

import android.os.AsyncTask;

import com.example.ball.yellow.Objects.Camera;
import com.example.ball.yellow.opengUtils.LightUtil;
import com.jogamp.opengl.math.Matrix4;

/**
 * Created by 世哲 on 2017/7/18.
 */

public class initTask extends AsyncTask{

    @Override
    protected Object doInBackground(Object[] params) {
        //LightUtil.initLight(new float[]{-1,-1,-1},new float[]{0.8f,0.8f,0.8f},new float[]{0,0,0},0.2f);
        Camera.init(50,1920.0f,948,0.3f,80);
        return null;
    }
}
