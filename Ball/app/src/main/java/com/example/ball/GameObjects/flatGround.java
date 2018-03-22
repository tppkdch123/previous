package com.example.ball.GameObjects;

import android.opengl.GLES31;
import android.opengl.Matrix;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.QuaternionUtil;
import com.bulletphysics.linearmath.Transform;
import com.example.ball.MyGLSurfaceView;
import com.example.ball.yellow.Objects.GameEntity;
import com.example.ball.yellow.opengUtils.LightUtil;
import com.example.ball.yellow.opengUtils.ShapeUtil;
import com.example.ball.yellow.opengUtils.openglUtil;
import com.jogamp.opengl.math.Matrix4;
import com.jogamp.opengl.math.Quaternion;

import java.nio.IntBuffer;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

/**
 * Created by 世哲 on 2017/7/18.
 */

public class flatGround extends GameEntity {
    public RigidBody body;
    public boolean isDynamic;
    float y=0;
    public flatGround(int Program,float x,float y,float z,float width,float height,int width2,int height2,int num,int singleBitmapTarget){
        super(Program, x, y, z, width, height, width2, height2, num,singleBitmapTarget);
    }
    public void enterDynamicsWorld(DynamicsWorld dynamicsWorld, float x, float y, float z, CollisionShape collisionShape, Vector3f inertia,float friction,float restitution){
        Transform transform=new Transform();
        transform.setIdentity();
        transform.origin.set(x,y,z);
        DefaultMotionState defaultMotionState=new DefaultMotionState(transform);
        RigidBodyConstructionInfo rbInfo=new RigidBodyConstructionInfo(0,defaultMotionState,collisionShape,inertia);
        body=new RigidBody(rbInfo);
        body.setFriction(friction);
        body.setRestitution(restitution);
        dynamicsWorld.addRigidBody(body);
    }
    @Override
    public void init(){
        super.init();
        initElementArrayBuffer();
        initVertexes();
        initNormals();
        initTexCoords2D();
    }
    @Override
    public void drawSelf(){
        Transform T=body.getMotionState().getWorldTransform(new Transform());
        T.getOpenGLMatrix(Model);
        super.drawSelf();
        GLES31.glActiveTexture(GLES31.GL_TEXTURE0);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,this.singleBitmapTarget);
        GLES31.glUniform1i(GLES31.glGetUniformLocation(Program,"sky"),0);
        GLES31.glActiveTexture(GLES31.GL_TEXTURE1);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,LightUtil.depthTexture);
        GLES31.glUniform1i(GLES31.glGetUniformLocation(Program,"shadowMap"),1);
        GLES31.glDrawElements(GLES31.GL_TRIANGLES,indices.capacity(),GLES31.GL_UNSIGNED_INT,0);
    }
    @Override
    public void drawDepthMap(int depthProgram){
        Transform T=body.getMotionState().getWorldTransform(new Transform());
        T.getOpenGLMatrix(Model);
        super.drawDepthMap(depthProgram);
    }
}
