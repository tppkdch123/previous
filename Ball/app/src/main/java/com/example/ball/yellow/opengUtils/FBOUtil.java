package com.example.ball.yellow.opengUtils;

import android.opengl.GLES20;
import android.opengl.GLES31;
import android.util.Log;

import com.jogamp.opengl.GLOffscreenAutoDrawable;

import java.nio.IntBuffer;

/**
 * Created by 世哲 on 2017/7/16.
 */

public class FBOUtil {
    public static IntBuffer generateTextureFBO(int FBO, int width, int height){
        IntBuffer texture=IntBuffer.allocate(1);
        IntBuffer rendererBuffer=IntBuffer.allocate(1);
        GLES31.glGenRenderbuffers(1,rendererBuffer);
        GLES31.glGenTextures(1,texture);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,texture.get(0));
        GLES31.glTexImage2D(GLES31.GL_TEXTURE_2D,0,GLES31.GL_RGB,width,height,0,GLES31.GL_RGB,GLES31.GL_UNSIGNED_BYTE,null);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MIN_FILTER, GLES31.GL_LINEAR );
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MAG_FILTER, GLES31.GL_LINEAR);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,0);
        GLES31.glBindRenderbuffer(GLES31.GL_RENDERBUFFER,rendererBuffer.get(0));
        GLES31.glRenderbufferStorage(GLES31.GL_RENDERBUFFER,GLES31.GL_DEPTH24_STENCIL8,1080,1865);
        GLES31.glBindRenderbuffer(GLES31.GL_RENDERBUFFER,0);
        GLES31.glBindFramebuffer(GLES31.GL_FRAMEBUFFER,FBO);
        GLES31.glFramebufferTexture2D(GLES31.GL_FRAMEBUFFER,GLES31.GL_COLOR_ATTACHMENT0,GLES31.GL_TEXTURE_2D,texture.get(0),0);
        GLES31.glFramebufferRenderbuffer(GLES31.GL_FRAMEBUFFER,GLES31.GL_DEPTH_STENCIL_ATTACHMENT,GLES31.GL_RENDERBUFFER,rendererBuffer.get(0));
        if(GLES31.glCheckFramebufferStatus(GLES31.GL_FRAMEBUFFER)!=GLES31.GL_FRAMEBUFFER_COMPLETE){
            Log.d("err","Error");
        }
        GLES31.glBindFramebuffer(GLES31.GL_FRAMEBUFFER,0);
        return texture;
    }
    public static int generateDepthMapFBO(int FBO){
        IntBuffer depthMap=IntBuffer.allocate(1);
        GLES31.glGenTextures(1,depthMap);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,depthMap.get(0));
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MIN_FILTER, GLES31.GL_NEAREST);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MAG_FILTER,GLES31.GL_LINEAR);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_WRAP_S, GLES31.GL_CLAMP_TO_EDGE);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_WRAP_T, GLES31.GL_CLAMP_TO_EDGE);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D,GLES31.GL_TEXTURE_COMPARE_MODE,GLES31.GL_COMPARE_REF_TO_TEXTURE);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D,GLES31.GL_TEXTURE_COMPARE_FUNC,GLES31.GL_LEQUAL);
        GLES31.glTexImage2D(GLES31.GL_TEXTURE_2D, 0, GLES31.GL_DEPTH_COMPONENT16, 1024, 1024, 0, GLES31.GL_DEPTH_COMPONENT, GLES31.GL_UNSIGNED_SHORT, null);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,0);
        GLES31.glBindFramebuffer(GLES31.GL_FRAMEBUFFER,FBO);
        GLES31.glReadBuffer(GLES31.GL_NONE);
        GLES31.glFramebufferTexture2D(GLES31.GL_FRAMEBUFFER,GLES31.GL_DEPTH_ATTACHMENT,GLES31.GL_TEXTURE_2D,depthMap.get(0),0);
        if(GLES31.glCheckFramebufferStatus(GLES31.GL_FRAMEBUFFER)!=GLES31.GL_FRAMEBUFFER_COMPLETE){
            System.err.println("Error");
            Log.d("err","Error");
        }
        GLES31.glBindFramebuffer(GLES31.GL_FRAMEBUFFER,0);
        return depthMap.get(0);
    }
    public static void beforeDrawDepthMap(int FBO){
        GLES31.glViewport(0, 0, 1024, 1024);
        GLES31.glBindFramebuffer(GLES31.GL_FRAMEBUFFER,FBO);
        GLES31.glClear(GLES31.GL_DEPTH_BUFFER_BIT);
        GLES31.glColorMask(false,false,false,false);
     //  GLES31.glEnable(GLES31.GL_POLYGON_OFFSET_FILL);
        GLES31.glPolygonOffset(4,100);
    }
    public static void afterDrawDepthMap(int width,int height){
        GLES31.glViewport(0,0,width,height);
        GLES31.glColorMask(true,true,true,true);
        GLES31.glDisable(GLES31.GL_POLYGON_OFFSET_FILL);
        GLES31.glBindFramebuffer(GLES31.GL_FRAMEBUFFER,0);
    }
    public static int initDepthFBO(){
        IntBuffer fbo=IntBuffer.allocate(1);
        IntBuffer rbo=IntBuffer.allocate(1);
        IntBuffer texture=IntBuffer.allocate(1);
        GLES31.glGenBuffers(1,fbo);
        GLES31.glGenRenderbuffers(1,rbo);
        GLES31.glGenTextures(1,texture);
        GLES31.glBindRenderbuffer(GLES31.GL_RENDERBUFFER, rbo.get(0));
        GLES31.glRenderbufferStorage(GLES31.GL_RENDERBUFFER,GLES31.GL_DEPTH_COMPONENT16,1024,1024);
        GLES31.glBindRenderbuffer(GLES31.GL_RENDERBUFFER,0);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,texture.get(0));
        sampleDepthMap1();
        GLES31.glTexImage2D(GLES31.GL_TEXTURE_2D,0,GLES31.GL_R16F,1024,1024,0,GLES31.GL_RED,GLES31.GL_FLOAT,null);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,0);
        GLES31.glBindFramebuffer(GLES31.GL_FRAMEBUFFER, fbo.get(0));
        GLES31.glFramebufferTexture2D(GLES31.GL_FRAMEBUFFER,GLES31.GL_COLOR_ATTACHMENT0,GLES31.GL_TEXTURE_2D,texture.get(0),0);
        GLES31.glFramebufferRenderbuffer(GLES31.GL_FRAMEBUFFER,GLES31.GL_DEPTH_ATTACHMENT,GLES31.GL_RENDERBUFFER,rbo.get(0));
        GLES31.glBindFramebuffer(GLES31.GL_FRAMEBUFFER,0);
        return texture.get(0);
    }
    public static void sampleDepthMap1(){
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MIN_FILTER, GLES31.GL_NEAREST);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MAG_FILTER,GLES31.GL_LINEAR);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_WRAP_S, GLES31.GL_CLAMP_TO_EDGE);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_WRAP_T, GLES31.GL_CLAMP_TO_EDGE);
       // GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D,GLES31.GL_TEXTURE_COMPARE_MODE,GLES31.GL_COMPARE_REF_TO_TEXTURE);
       // GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D,GLES31.GL_TEXTURE_COMPARE_FUNC,GLES31.GL_LEQUAL);
    }
    public static int generateRenderer1(int width,int height){
        IntBuffer rbo=IntBuffer.allocate(1);
        GLES31.glGenRenderbuffers(1,rbo);
        int r=rbo.get(0);
        GLES31.glBindRenderbuffer(GLES31.GL_RENDERBUFFER,r);
        GLES31.glRenderbufferStorage(GLES31.GL_RENDERBUFFER,GLES31.GL_DEPTH24_STENCIL8,width,height);
        GLES31.glBindRenderbuffer(GLES31.GL_RENDERBUFFER,0);
        return r;
    }
    public static int generateFBO1(int texture,int renderer){
        IntBuffer fbo=IntBuffer.allocate(1);
        GLES31.glGenFramebuffers(1,fbo);  int f=fbo.get(0);
        GLES31.glBindFramebuffer(GLES31.GL_FRAMEBUFFER,f);
        GLES31.glFramebufferTexture2D(GLES31.GL_FRAMEBUFFER,GLES31.GL_COLOR_ATTACHMENT0,GLES31.GL_TEXTURE_2D,texture,0);
        GLES31.glFramebufferRenderbuffer(GLES31.GL_FRAMEBUFFER,GLES31.GL_DEPTH_STENCIL_ATTACHMENT,GLES31.GL_RENDERBUFFER,renderer);
        IntBuffer draws=IntBuffer.allocate(1);
        draws.put(GLES31.GL_COLOR_ATTACHMENT0);draws.flip();
        GLES31.glDrawBuffers(1,draws);
        if(GLES31.glCheckFramebufferStatus(GLES31.GL_FRAMEBUFFER)!=GLES31.GL_FRAMEBUFFER_COMPLETE){
            Log.d("err","Error");
        }
        GLES31.glBindFramebuffer(GLES31.GL_FRAMEBUFFER,0);
        return f;
    }
}
