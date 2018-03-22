package com.example.ball.yellow.opengUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES31;
import android.opengl.GLUtils;
import android.os.AsyncTask;
import android.util.LruCache;
import com.example.ball.MyApplication;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static com.example.ball.yellow.opengUtils.BitmapUtil.bitmapLruCache;
import static com.example.ball.yellow.opengUtils.BitmapUtil.getBitmapById;
import static com.example.ball.yellow.opengUtils.BitmapUtil.getBitmapInRaw;

/**
 * Created by 世哲 on 2017/7/16.
 */

public class BitmapUtil {
    public static LruCache<Integer,Bitmap> bitmapLruCache=new LruCache<Integer,Bitmap>(((int)(Runtime.getRuntime().maxMemory()/1024))/8){
        @Override
        protected int sizeOf(Integer key,Bitmap bitmap){
            return bitmap.getRowBytes()*bitmap.getHeight()/1024;
        }
};
   public static Bitmap getBitmapInRaw(int bitmapId){
       Bitmap B=bitmapLruCache.get(bitmapId);
       if(B==null){
           B=getBitmapById(bitmapId);
           bitmapLruCache.put(bitmapId,B);
       }
       return B;
   }
    public static Bitmap getBitmapById(int bitmapId) {
        return BitmapFactory.decodeResource(MyApplication.getContext().getResources(), bitmapId);
    }
    public static void defaultBindTexture2D(Bitmap bitmap,int texture){
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,texture);
        GLUtils.texImage2D(GLES31.GL_TEXTURE_2D,0,bitmap,0);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MAG_FILTER, GLES31.GL_LINEAR);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MIN_FILTER, GLES31.GL_LINEAR);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_WRAP_S, GLES31.GL_CLAMP_TO_EDGE);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_WRAP_T, GLES31.GL_CLAMP_TO_EDGE);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_WRAP_R, GLES31.GL_CLAMP_TO_EDGE);
        GLES31.glGenerateMipmap(GLES31.GL_TEXTURE_2D);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,0);
    }
    public static void defaultBindTexture2DByRaw(int resource,int texture){
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,texture);
        GLUtils.texImage2D(GLES31.GL_TEXTURE_2D,0,getBitmapInRaw(resource),0);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MAG_FILTER, GLES31.GL_LINEAR);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MIN_FILTER, GLES31.GL_LINEAR);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_WRAP_S, GLES31.GL_CLAMP_TO_EDGE);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_WRAP_T, GLES31.GL_CLAMP_TO_EDGE);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_WRAP_R, GLES31.GL_CLAMP_TO_EDGE);
        GLES31.glGenerateMipmap(GLES31.GL_TEXTURE_2D);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,0);
    }
    public static int defaultGenerateTexture2D(int resource){
        IntBuffer a=IntBuffer.allocate(1);
        GLES31.glGenTextures(1,a);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,a.get(0));
        GLUtils.texImage2D(GLES31.GL_TEXTURE_2D,0,getBitmapInRaw(resource),0);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MAG_FILTER, GLES31.GL_NEAREST);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MIN_FILTER, GLES31.GL_NEAREST_MIPMAP_NEAREST);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_WRAP_S, GLES31.GL_REPEAT);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_WRAP_T, GLES31.GL_REPEAT);
        GLES31.glGenerateMipmap(GLES31.GL_TEXTURE_2D);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,0);
        return a.get(0);
    }
    public static int defaultGenerateTexture2D2(int resource){
        IntBuffer a=IntBuffer.allocate(1);
        GLES31.glGenTextures(1,a);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,a.get(0));
        GLUtils.texImage2D(GLES31.GL_TEXTURE_2D,0,getBitmapInRaw(resource),0);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MAG_FILTER, GLES31.GL_NEAREST);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MIN_FILTER, GLES31.GL_NEAREST_MIPMAP_NEAREST);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_WRAP_S, GLES31.GL_CLAMP_TO_EDGE);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_WRAP_T, GLES31.GL_CLAMP_TO_EDGE);
        GLES31.glGenerateMipmap(GLES31.GL_TEXTURE_2D);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,0);
        return a.get(0);
    }
    public static IntBuffer generateSkyBoxTexture(Bitmap[] bitmaps){
        IntBuffer skyBox=IntBuffer.allocate(1);
        GLES31.glGenTextures(1,skyBox);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_CUBE_MAP,skyBox.get(0));
        GLUtils.texImage2D(GLES31.GL_TEXTURE_CUBE_MAP_POSITIVE_X,0,bitmaps[3],0);
        GLUtils.texImage2D(GLES31.GL_TEXTURE_CUBE_MAP_POSITIVE_Y,0,bitmaps[0],0);
        GLUtils.texImage2D(GLES31.GL_TEXTURE_CUBE_MAP_POSITIVE_Z,0,bitmaps[4],0);
        GLUtils.texImage2D(GLES31.GL_TEXTURE_CUBE_MAP_NEGATIVE_X,0,bitmaps[2],0);
        GLUtils.texImage2D(GLES31.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z,0,bitmaps[5],0);
        GLUtils.texImage2D(GLES31.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y,0,bitmaps[1],0);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_CUBE_MAP, GLES31.GL_TEXTURE_MAG_FILTER, GLES31.GL_LINEAR);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_CUBE_MAP, GLES31.GL_TEXTURE_MIN_FILTER, GLES31.GL_LINEAR);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_CUBE_MAP, GLES31.GL_TEXTURE_WRAP_S, GLES31.GL_CLAMP_TO_EDGE);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_CUBE_MAP, GLES31.GL_TEXTURE_WRAP_T, GLES31.GL_CLAMP_TO_EDGE);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_CUBE_MAP, GLES31.GL_TEXTURE_WRAP_R, GLES31.GL_CLAMP_TO_EDGE);
        return skyBox;
    }
    public static IntBuffer generateSkyBoxTextureByRawId(int top,int down,int left,int right,int before,int behand){
        Bitmap[] bitmaps=new Bitmap[6];
        bitmaps[0]=getBitmapInRaw(top);
        bitmaps[2]=getBitmapInRaw(left);
        bitmaps[1]=getBitmapInRaw(down);
        bitmaps[3]=getBitmapInRaw(right);
        bitmaps[4]=getBitmapInRaw(before);
        bitmaps[5]=getBitmapInRaw(behand);
        return generateSkyBoxTexture(bitmaps);
    }
    public static int generateSkyBoxTextureId(Bitmap[] bitmaps){
        IntBuffer skyBox=IntBuffer.allocate(1);
        GLES31.glGenTextures(1,skyBox);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_CUBE_MAP,skyBox.get(0));
        GLUtils.texImage2D(GLES31.GL_TEXTURE_CUBE_MAP_POSITIVE_X,0,bitmaps[3],0);
        GLUtils.texImage2D(GLES31.GL_TEXTURE_CUBE_MAP_POSITIVE_Y,0,bitmaps[0],0);
        GLUtils.texImage2D(GLES31.GL_TEXTURE_CUBE_MAP_POSITIVE_Z,0,bitmaps[4],0);
        GLUtils.texImage2D(GLES31.GL_TEXTURE_CUBE_MAP_NEGATIVE_X,0,bitmaps[2],0);
        GLUtils.texImage2D(GLES31.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z,0,bitmaps[5],0);
        GLUtils.texImage2D(GLES31.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y,0,bitmaps[1],0);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_CUBE_MAP, GLES31.GL_TEXTURE_MAG_FILTER, GLES31.GL_NEAREST);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_CUBE_MAP, GLES31.GL_TEXTURE_MIN_FILTER, GLES31.GL_NEAREST_MIPMAP_NEAREST);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_CUBE_MAP, GLES31.GL_TEXTURE_WRAP_S, GLES31.GL_CLAMP_TO_EDGE);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_CUBE_MAP, GLES31.GL_TEXTURE_WRAP_T, GLES31.GL_CLAMP_TO_EDGE);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_CUBE_MAP, GLES31.GL_TEXTURE_WRAP_R, GLES31.GL_CLAMP_TO_EDGE);
        GLES31.glGenerateMipmap(GLES31.GL_TEXTURE_CUBE_MAP);
        return skyBox.get(0);
    }
    public static int generateSkyBoxTextureIdByRawId(int top,int down,int left,int right,int before,int behand) {
        Bitmap[] bitmaps = new Bitmap[6];
        bitmaps[0] = getBitmapInRaw(top);
        bitmaps[2] = getBitmapInRaw(left);
        bitmaps[1] = getBitmapInRaw(down);
        bitmaps[3] = getBitmapInRaw(right);
        bitmaps[4] = getBitmapInRaw(before);
        bitmaps[5] = getBitmapInRaw(behand);
        return generateSkyBoxTextureId(bitmaps);
    }
    public static void setSurroundMode1(int textureTarget){
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MIN_FILTER, GLES31.GL_NEAREST);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MAG_FILTER,GLES31.GL_LINEAR);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_WRAP_S, GLES31.GL_CLAMP_TO_EDGE);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_WRAP_T, GLES31.GL_CLAMP_TO_EDGE);
        // GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D,GLES31.GL_TEXTURE_COMPARE_MODE,GLES31.GL_COMPARE_REF_TO_TEXTURE);
        // GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D,GLES31.GL_TEXTURE_COMPARE_FUNC,GLES31.GL_LEQUAL);
    }
    public static int getFBOTexure(int width,int height){
        IntBuffer linshi=IntBuffer.allocate(1);
        GLES31.glGenTextures(1,linshi);
        int a=linshi.get(0);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,a);
        GLES31.glTexImage2D(GLES31.GL_TEXTURE_2D,0,GLES31.GL_RGB16F,width,height,0,GLES31.GL_RGB,GLES31.GL_FLOAT,null);
        setSurroundMode1(a);
        GLES31.glGenerateMipmap(GLES31.GL_TEXTURE_2D);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,0);
        return a;
    }
}
class initBitmap extends AsyncTask<Integer,Void,String> {
    @Override
    protected String doInBackground(Integer... params) {
        bitmapLruCache.put(params[0],getBitmapInRaw(params[0]));
        return null;
    }
}