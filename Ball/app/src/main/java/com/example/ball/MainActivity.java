package com.example.ball;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ball.yellow.Objects.Camera;
import com.example.ball.yellow.opengUtils.BitmapUtil;
import com.example.ball.yellow.opengUtils.openglUtil;
import com.jogamp.opengl.math.VectorUtil;

import javax.vecmath.Vector3f;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private MyGLSurfaceView MyView;
    private View UiView;
    private ImageView scrollBall;
    public TextView FPS;
    public double fps;
    private Handler hand=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1: fps*=10;double f=((int)fps)/10.0;
                    FPS.setText("FPS:"+new Double(f).toString());break;
                default:break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        MyView=new MyGLSurfaceView(this);
        setContentView(MyView);
        UiView=View.inflate(this,R.layout.activity_main,null);
        ConstraintLayout.LayoutParams L=new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        addContentView(UiView,L);
        ImageButton i1=(ImageButton) findViewById(R.id.imageButton);
        i1.setOnClickListener(this);i1.setAlpha(0.5f);
        scrollBall=(ImageButton)findViewById(R.id.imageButton5);
        scrollBall.setAlpha(0.9f);scrollBall.setOnClickListener(this);
        scrollBall.setOnTouchListener(new MyScroller());
        FPS=(TextView)findViewById(R.id.textView);
        new Thread(){
            @Override
            public void run(){
                while(true){
hand.sendEmptyMessage(1);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }
    @Override
    public void onClick(View v) {
      new AddForce().execute();
    }
    class AddForce extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            MyView.testbody.applyCentralForce(new Vector3f(0,1000,0));
            return null;
        }
    }
    class MyScroller implements View.OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action=event.getAction()&MotionEvent.ACTION_MASK;
            switch (action){
                case MotionEvent.ACTION_DOWN:
                MyView.goHead=true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                   MyView.goHead=false;
                    break;
            }
            return true;
        }
    }
    public static int getDp(float px){
        return (int)(px/1.5f+0.5);
    }
}
