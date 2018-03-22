package com.example.ball.GameObjects;

import android.opengl.GLES20;
import android.opengl.GLES31;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import com.example.ball.yellow.Objects.GameEntity;
import com.example.ball.yellow.Objects.rigidbodyMessage;
import com.example.ball.yellow.opengUtils.LightUtil;

import javax.vecmath.Vector3f;

/**
 * Created by 世哲 on 2017/7/19.
 */

public class rigidbodyObject extends GameEntity implements rigidbodyMessage {
    public RigidBody body;
    public boolean isDynamic;
    public rigidbodyObject(int Program,int xNum, int yNum, float R,int singleBitmapTarget){
    super(Program, xNum, yNum, R, singleBitmapTarget);
    }
    @Override
    public void enterDynamicWorld(DynamicsWorld dynamicsWorld, float x, float y, float z, CollisionShape collisionShape, float friction, float restitution, float mass) {
        Transform transform=new Transform();
        transform.setIdentity();
        transform.origin.set(x,y,z);
        isDynamic=(mass!=0f);Vector3f inertia=new Vector3f(0,0,0);
        if(isDynamic)collisionShape.calculateLocalInertia(mass,inertia);
        DefaultMotionState defaultMotionState=new DefaultMotionState(transform);
        RigidBodyConstructionInfo rbInfo=new RigidBodyConstructionInfo(mass,defaultMotionState,collisionShape,inertia);
        body=new RigidBody(rbInfo);
        body.setRestitution(restitution);
        body.setFriction(friction);
        dynamicsWorld.addRigidBody(body);
    }
    @Override
    public void init(){
        super.init();
        initElementArrayBuffer();
        initVertexes();
        initTexCoords2D();
        initNormals();
    }
    @Override
    public void drawSelf(){
        body.getMotionState().getWorldTransform(new Transform()).getOpenGLMatrix(Model);
        super.drawSelf();
        GLES31.glActiveTexture(GLES31.GL_TEXTURE0);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,singleBitmapTarget);
        GLES31.glUniform1i(GLES31.glGetUniformLocation(Program,"sky"),0);
        GLES31.glActiveTexture(GLES31.GL_TEXTURE1);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,LightUtil.depthTexture);
        GLES31.glUniform1i(GLES31.glGetUniformLocation(Program,"shadowMap"),1);
        GLES31.glDrawElements(GLES31.GL_TRIANGLES,pointsNum,GLES31.GL_UNSIGNED_INT,0);
    }
    @Override
    public void drawDepthMap(int depthProgram){
        body.getMotionState().getWorldTransform(new Transform()).getOpenGLMatrix(Model);
        super.drawDepthMap(depthProgram);
    }
}
