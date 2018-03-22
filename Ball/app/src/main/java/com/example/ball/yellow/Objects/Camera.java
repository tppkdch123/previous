package com.example.ball.yellow.Objects;

import android.opengl.GLES31;
import android.opengl.Matrix;
import android.os.AsyncTask;
import android.view.View;

import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.QuaternionUtil;
import com.bulletphysics.linearmath.Transform;
import com.example.ball.yellow.opengUtils.LightUtil;
import com.example.ball.yellow.opengUtils.openglUtil;
import com.jogamp.opengl.math.FloatUtil;
import com.jogamp.opengl.math.Matrix4;
import com.jogamp.opengl.math.Quaternion;
import com.jogamp.opengl.math.VectorUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.vecmath.Quat4d;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

/**
 * Created by 世哲 on 2017/7/16.
 */

public class Camera {
    public static final float[] xA=new float[]{1,0,0};
    public static final float[] yA=new float[]{0,1,0};
    public static final float[] zA=new float[]{0,0,1};
    public static final float[] outFront=new float[]{0,0,-1};
    public static RigidBody charaterController;
    public static float[] vector=new float[]{0,1.7f,7};
    public static float[] worldVector=new float[]{0,1,2};
    public static IntBuffer VPMatrix=IntBuffer.allocate(1);
    public static float[] Projection=new float[16];
    public static Matrix4 View=new Matrix4();
    public static Matrix4 skyBoxVP=new Matrix4();
    public static float[] VP=new float[16];
    public static Quaternion rotation=new Quaternion();
    public static float x;
    public static float y;
    public static float z;
    public static boolean isDown;
    public static float[] front;
    public static float[] top;
    public static float[] right;
    public static float[] forward;
    public static float[] objectfront;
    public static float[] objectright;
    public static float[] ViewTransform=new float[3];
    public static void init(float angle,float width,float height,float in,float out){
        front=new float[]{0,0,1};
        top=new float[]{0,1,0};
        right=new float[]{1,0,0};
        forward=new float[]{0,0,-1};
        objectfront=new float[]{0,0,-1};
        objectright=new float[]{1,0,0};
        Matrix.setIdentityM(Projection,0);
        View.loadIdentity();
        Matrix.setIdentityM(VP,0);
        rotation.setIdentity();
        Matrix.perspectiveM(Projection,0,angle,width/height,in,out);
        Matrix.multiplyMM(VP,0,Projection,0,View.getMatrix(),0);
        System.arraycopy(Projection,0,skyBoxVP.getMatrix(),0,16);
        skyBoxVP.rotate(rotation);
    }
    public static void followGameObject(){
        Transform c=charaterController.getMotionState().getWorldTransform(new Transform());
        c.origin.get(worldVector);
       float[] tx=generateWorldTransform(vector);
        Camera.x=worldVector[0]+tx[0];
        Camera.y=worldVector[1]+tx[1];
        Camera.z=worldVector[2]+tx[2];
        float[] LightTransform=LightUtil.generateLightMatrixTransform(x,y,z);
        LightUtil.updateLightPosition(LightTransform[0],LightTransform[1],LightTransform[2]);
        View.loadIdentity();
        ViewTransform=generateCameraTransform();
        View.translate(ViewTransform[0],ViewTransform[1],ViewTransform[2]);
        View.rotate(rotation);
        onCameraChange();
    }
    public static void onCameraChange(){
        Matrix.multiplyMM(VP,0,Projection,0,View.getMatrix(),0);
    }
    public static void changeCameraRotationByEuler(float yAsix,float xAsix){
        rotation.setIdentity();
        rotation.rotateByAngleNormalAxis(getRad(xAsix),1,0,0);
        rotation.rotateVector(objectfront,0,outFront,0);
        transformWorldTransform(objectfront);
        rotation.rotateVector(objectright,0,xA,0);
        transformWorldTransform(objectright);
        rotation.rotateByAngleNormalAxis(getRad(yAsix),0,1,0);
        rotation.rotateVector(front,0,zA,0);
        rotation.rotateVector(top,0,yA,0);
        rotation.rotateVector(right,0,xA,0);
        System.arraycopy(Projection,0,skyBoxVP.getMatrix(),0,16);
        skyBoxVP.rotate(rotation);
    }
    public static void changeCameraTransform(float x,float y,float z){
        Camera.x=x;
        Camera.y=y;
        Camera.z=z;
        float[] translate=generateCameraTransform();
        View.loadIdentity();
        View.translate(translate[0],translate[1],translate[2]);
        View.rotate(rotation);
        onCameraChange();
    }
    public static void forward(float a){
        float Z=VectorUtil.dotVec3(forward,front);
        float X=VectorUtil.dotVec3(forward,right);
        float Y=VectorUtil.dotVec3(forward,top);
        Camera.x+=a*X;
        Camera.z+=a*Z;
        Camera.y+=a*Y;
        float[] translate=generateCameraTransform();
        View.loadIdentity();
        View.translate(translate[0],translate[1],translate[2]);
        View.rotate(rotation);
        onCameraChange();
    }
    public static void changeViewAll(float yAsix,float xAsix,float x,float y,float z){
        rotation.setIdentity();
        rotation.rotateByEuler(getRad(xAsix),getRad(yAsix),0);
        rotation.rotateVector(front,0,new float[]{0,0,1},0);
        rotation.rotateVector(top,0,new float[]{0,1,0},0);
        rotation.rotateVector(right,0,new float[]{1,0,0},0);
        Camera.x=x;
        Camera.y=y;
        Camera.z=z;
        float[] translate=generateCameraTransform();
        View.loadIdentity();
        View.translate(translate[0],translate[1],translate[2]);
        View.rotate(rotation);
        System.arraycopy(Projection,0,skyBoxVP.getMatrix(),0,16);
        skyBoxVP.rotate(rotation);
        onCameraChange();
    }
    public static void initUniformBlockVPMatrix(){
        GLES31.glGenBuffers(1,VPMatrix);
        GLES31.glBindBuffer(GLES31.GL_UNIFORM_BUFFER,VPMatrix.get(0));
        GLES31.glBufferData(GLES31.GL_UNIFORM_BUFFER,64,null,GLES31.GL_DYNAMIC_DRAW);
        GLES31.glBufferSubData(GLES31.GL_UNIFORM_BUFFER,0,64, FloatBuffer.wrap(VP));
        GLES31.glBindBufferBase(GLES31.GL_UNIFORM_BUFFER,1,VPMatrix.get(0));
        GLES31.glBindBuffer(GLES31.GL_UNIFORM_BUFFER,0);
    }
    public static float getRad(float a){
        return (float)Math.PI*a/180;
    }
    public static float[] generateCameraTransform(){
        float[] translate=new float[3];
        VectorUtil.addVec3(translate,openglUtil.multNum(front,-z),openglUtil.multNum(top,-y));
        VectorUtil.addVec3(translate,translate,openglUtil.multNum(right,-x));
        return translate;
    }
    public static float[] generateWorldTransform(float[] cameraVector){
        float X=0,Y=0,Z=0;
        X=VectorUtil.dotVec3(right,cameraVector);
        Y=VectorUtil.dotVec3(top,cameraVector);
        Z=VectorUtil.dotVec3(front,cameraVector);
        return new float[]{X,Y,Z};
    }
    public static float[] generateCameraTransform(float[] worldVector){
        float[] f1=openglUtil.multNum(right,worldVector[0]);
        float[] f2=openglUtil.multNum(top,worldVector[1]);
        float[] f3=openglUtil.multNum(front,worldVector[2]);
        VectorUtil.addVec3(f1,f1,f2);
        VectorUtil.addVec3(f1,f1,f3);
        return f1;
    }
    public static void transformWorldTransform(float[] cameraVector){
        float X=0,Y=0,Z=0;
        X=VectorUtil.dotVec3(right,cameraVector);
        Y=VectorUtil.dotVec3(top,cameraVector);
        Z=VectorUtil.dotVec3(front,cameraVector);
        cameraVector[0]=X;
        cameraVector[1]=Y;
        cameraVector[2]=Z;
    }
    public static void transformCameraTransform(float[] worldVector){
        float[] f1=openglUtil.multNum(right,worldVector[0]);
        float[] f2=openglUtil.multNum(top,worldVector[1]);
        float[] f3=openglUtil.multNum(front,worldVector[2]);
        VectorUtil.addVec3(worldVector,f1,f2);
        VectorUtil.addVec3(worldVector,worldVector,f3);
    }
    public static void updateCameraPosition(){
        FloatBuffer f=FloatBuffer.allocate(3);
        f.put(x);f.put(y);
        f.put(z);f.flip();
        GLES31.glBindBuffer(GLES31.GL_UNIFORM_BUFFER,LightUtil.light.get(0));
        GLES31.glBufferSubData(GLES31.GL_UNIFORM_BUFFER,32,12,f);
        GLES31.glBindBuffer(GLES31.GL_UNIFORM_BUFFER,0);
    }
    public static void updateCameraMatrix(){
        GLES31.glBindBuffer(GLES31.GL_UNIFORM_BUFFER,VPMatrix.get(0));
        GLES31.glBufferSubData(GLES31.GL_UNIFORM_BUFFER,0,64, FloatBuffer.wrap(VP));
        GLES31.glBindBuffer(GLES31.GL_UNIFORM_BUFFER,0);
    }
}
