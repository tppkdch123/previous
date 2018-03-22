package com.example.ball.yellow.opengUtils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by 世哲 on 2017/7/16.
 */

public class FontUtil {
    public static int R;
    public static int G;
    public static int B;
    public static int A;
    public static int textSize=20;
    public static Random random=new Random();
    public static void setRGBASize(int r,int g,int b,int a,int size){
        R=r;
        G=g;
        B=b;
        A=a;
        textSize=size;
    }
    public static Bitmap generateWLT(String[] str, int width, int height){
        Paint paint=new Paint();
        paint.setARGB(A,R,G,B);
        paint.setTextSize(textSize);
        paint.setTypeface(null);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        Bitmap bitmap=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        for(int i=0;i<str.length;i++){
            canvas.drawText(str[i],0,textSize*i+(i-1)*5,paint);
        }
        return bitmap;
    }
    public static void generateRandomColor(boolean b){
        if(b) A=random.nextInt(255);
        R=random.nextInt(255);
        G=random.nextInt(255);
        B=random.nextInt(255);
    }
}