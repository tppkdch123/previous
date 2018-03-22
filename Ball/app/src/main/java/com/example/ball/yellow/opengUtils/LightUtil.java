package com.example.ball.yellow.opengUtils;

import android.opengl.GLES31;
import android.opengl.Matrix;

import com.example.ball.yellow.Objects.Camera;
import com.jogamp.opengl.math.FloatUtil;
import com.jogamp.opengl.math.Matrix4;
import com.jogamp.opengl.math.Quaternion;
import com.jogamp.opengl.math.VectorUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by 世哲 on 2017/7/17.
 */

public class LightUtil {
    public static IntBuffer light=IntBuffer.allocate(2);
    public static Matrix4 LightVPMatrix;
    public static Quaternion LightDirect;
    public static int depthTexture;
    public static int depthFbo;
    public static float[] Z=new float[]{0,0,1};
    public static float[] X=new float[]{1,0,0};
    public static float[] Y=new float[]{0,1,1};
    public static void initLight(float[] direct,float[] color,float ambientStrength){
        initFBO();
        float[] cameraPosition=new float[]{0,0,0};
        LightVPMatrix=new Matrix4();
        LightDirect=new Quaternion();
        FloatBuffer directBuffer;
        FloatBuffer colorBuffer;
        FloatBuffer cameraBuffer;
        FloatBuffer ambientBuffer=FloatBuffer.allocate(1);
        ambientBuffer.put(ambientStrength);ambientBuffer.flip();
        if(direct.length==3&&color.length==3&&cameraPosition.length==3){
            directBuffer = FloatBuffer.wrap(direct);
            colorBuffer=FloatBuffer.wrap(color);
            cameraBuffer=FloatBuffer.wrap(cameraPosition);
        }
        else return;
        GLES31.glGenBuffers(2,light);
        GLES31.glBindBuffer(GLES31.GL_UNIFORM_BUFFER,light.get(0));
        GLES31.glBufferData(GLES31.GL_UNIFORM_BUFFER,52,null,GLES31.GL_STATIC_DRAW);
        GLES31.glBindBufferBase(GLES31.GL_UNIFORM_BUFFER,0,light.get(0));
        GLES31.glBufferSubData(GLES31.GL_UNIFORM_BUFFER,0,12,directBuffer);
        GLES31.glBufferSubData(GLES31.GL_UNIFORM_BUFFER,16,12,colorBuffer);
        GLES31.glBufferSubData(GLES31.GL_UNIFORM_BUFFER,32,12,cameraBuffer);
        GLES31.glBufferSubData(GLES31.GL_UNIFORM_BUFFER,48,4,ambientBuffer);
        GLES31.glBindBuffer(GLES31.GL_UNIFORM_BUFFER,0);
        //LightDirect.setLookAt(direct,new float[]{0,0,1},new float[]{1,0,0},new float[]{0,1,0},new float[]{0,0,1});
        openglUtil.getRotation(LightDirect,new float[]{0,0,-1},direct);
        LightVPMatrix.makeOrtho(-50f,50f, -70f, 70f,-50f,50f);
        LightVPMatrix.rotate(LightDirect);
        transformLightAxis();
        GLES31.glBindBuffer(GLES31.GL_UNIFORM_BUFFER,light.get(1));
        GLES31.glBufferData(GLES31.GL_UNIFORM_BUFFER,64,FloatBuffer.wrap(LightVPMatrix.getMatrix()),GLES31.GL_DYNAMIC_DRAW);
        GLES31.glBindBufferBase(GLES31.GL_UNIFORM_BUFFER,2,light.get(1));
        GLES31.glBindBuffer(GLES31.GL_UNIFORM_BUFFER,0);
    }
    public static void changeDirect(float[] direct){
        FloatBuffer directBuffer;
        if(direct.length==3){
            directBuffer = FloatBuffer.wrap(direct);
        }
        else return;
        GLES31.glBindBuffer(GLES31.GL_UNIFORM_BUFFER,light.get(0));
        GLES31.glBufferSubData(GLES31.GL_UNIFORM_BUFFER,0,12,directBuffer);
        GLES31.glBindBuffer(GLES31.GL_UNIFORM_BUFFER,0);
    }
    public static void initFBO(){
        IntBuffer fbo=IntBuffer.allocate(1);
        GLES31.glGenFramebuffers(1,fbo);
        depthFbo=fbo.get(0);
        depthTexture=FBOUtil.generateDepthMapFBO(depthFbo);
    }
    public static void changeColor(float[] color){
        FloatBuffer colorBuffer;
        if(color.length==3){
            colorBuffer = FloatBuffer.wrap(color);
        }
        else return;
        GLES31.glBindBuffer(GLES31.GL_UNIFORM_BUFFER,light.get(0));
        GLES31.glBufferSubData(GLES31.GL_UNIFORM_BUFFER,16,12,colorBuffer);
        GLES31.glBindBuffer(GLES31.GL_UNIFORM_BUFFER,0);
    }
    public static void changeDirect(float x,float y,float z){
        FloatBuffer F=FloatBuffer.wrap(new float[]{x,y,z});
        GLES31.glBindBuffer(GLES31.GL_UNIFORM_BUFFER,light.get(0));
        GLES31.glBufferSubData(GLES31.GL_UNIFORM_BUFFER,0,12,F);
        GLES31.glBindBuffer(GLES31.GL_UNIFORM_BUFFER,0);
    }
 public static void updateLightMatrix(){
     GLES31.glBindBuffer(GLES31.GL_UNIFORM_BUFFER,light.get(1));
     GLES31.glBufferSubData(GLES31.GL_UNIFORM_BUFFER,0,64,FloatBuffer.wrap(LightVPMatrix.getMatrix()));
     GLES31.glBindBuffer(GLES31.GL_UNIFORM_BUFFER,0);
 }
 public static void transformLightAxis(){
     LightDirect.rotateVector(Z,0, Camera.zA,0);
     LightDirect.rotateVector(Y,0,Camera.yA,0);
     LightDirect.rotateVector(X,0,Camera.xA,0);
 }
    public static float[] generateLightMatrixTransform(float x, float y,float z){
        float[] f1=openglUtil.multNum(X,-x);
        float[] f2=openglUtil.multNum(Y,-y);
        float[] f3=openglUtil.multNum(Z,-z);
        VectorUtil.addVec3(f1,f1,f2);
        VectorUtil.addVec3(f1,f1,f3);
        return f1;
    }
    public static void updateLightPosition(float x,float y,float z){
        LightVPMatrix.loadIdentity();
        LightVPMatrix.makeOrtho(-25f,25f, -25f, 25f,-23,23f);
        LightUtil.LightVPMatrix.translate(x,y,z);
        LightVPMatrix.rotate(LightDirect);
    }
}
