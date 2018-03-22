package com.example.ball.yellow.opengUtils;

import android.opengl.GLES31;

import com.jogamp.opengl.math.Quaternion;
import com.jogamp.opengl.math.VectorUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

/**
 * Created by 世哲 on 2017/7/16.
 */

public class openglUtil {
    public static IntBuffer uboBlock;
    public static void initUbo(int n){
        uboBlock=IntBuffer.allocate(n);
        GLES31.glGenBuffers(n,uboBlock);
        for(int i=0;i< uboBlock.capacity();i++){
            GLES31.glBindBufferBase(GLES31.GL_UNIFORM_BUFFER,i,uboBlock.get(i));
        }
    }
    public static void printMatrix4(float[] f){
        if(f==null||f.length!=16)
            return;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++)
                System.out.print(f[3*i+j]);
            System.out.println();
        }
    }
    public static float[] getF(float[] f1,float[] f2){
        float[] f3=new float[f1.length+f2.length];
        for(int i=0;i<f3.length;i++){
            if(i<f1.length)
                f3[i]=f1[i];
            else f3[i]=f2[i-f1.length];
        }
        return f3;
    }
    public static float[] convertTo(List<Float> A){
        if(A==null||A.isEmpty()){
            return null;
        }
        float[] F=new float[A.size()];
        for(int i=0;i<F.length;i++)
            F[i]=A.get(i);
        return F;
    }
    public static FloatBuffer convertToBuffer(List<Float> A){
        if(A==null||A.isEmpty()){
            return null;
        }
        float[] F=new float[A.size()];
        for(int i=0;i<F.length;i++)
            F[i]=A.get(i);
        return FloatBuffer.wrap(F);
    }
    public static int[] convertTo2(List<Integer> A){
        if(A==null||A.isEmpty()){
            return null;
        }
        int[] F=new int[A.size()];
        for(int i=0;i<F.length;i++)
            F[i]=A.get(i);
        return F;
    }
    public static IntBuffer convertToIntBuffer(List<Integer> A){
        return IntBuffer.wrap(convertTo2(A));
    }
    public static FloatBuffer convertToFloatBuffer(List<Float> A){
        return FloatBuffer.wrap(convertTo(A));
    }
    public static float[] multNum(float[] vector,float num){
        float[] result=new float[vector.length];
        for(int i=0;i<vector.length;i++)
            result[i]=vector[i]*num;
        return result;
    }
    public static float[] multnum(float[] vector,float num){
        for(int i=0;i<vector.length;i++)
            vector[i]=vector[i]*num;
        return vector;
    }
    public static float getRad(float a){
        return (float)Math.PI*a/180;
    }
    public static void getRotation(Quaternion rotation,float[] point1,float[] point2){
        rotation.setIdentity();
        float[] f=new float[3];
        VectorUtil.crossVec3(f,point2,point1);
        float angle=VectorUtil.angleVec3(point2,point1);
        rotation.rotateByAngleNormalAxis(angle,f[0],f[1],f[2]);
    }
    public static float vec2Distance(int[] a,float x,float y){
        return (float)(Math.pow(a[0]-x,2)+Math.pow(a[1]-y,2));
    }
}
