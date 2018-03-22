package com.example.ball.yellow.aboutBullet;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.ViewConfiguration;

import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.narrowphase.PersistentManifold;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.BvhTriangleMeshShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.collision.shapes.TriangleIndexVertexArray;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.extras.gimpact.GImpactMeshShape;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.vecmath.Vector3f;

/**
 * Created by 世哲 on 2017/7/16.
 */

public class BulletUtil {
    public static TriangleIndexVertexArray makeTriangleIndexVertexArray(FloatBuffer vertexes, IntBuffer indices){
        int count=vertexes.capacity()/3;
        ByteBuffer vertex=ByteBuffer.allocateDirect(vertexes.capacity()*4).order(ByteOrder.nativeOrder());
        for(int i=0;i<vertexes.capacity();i++){
            vertex.putFloat(i*4,vertexes.get(i));
        }
        vertex.position(0);
        ByteBuffer Indices=ByteBuffer.allocateDirect(indices.capacity()*4).order(ByteOrder.nativeOrder());
        for(int i=0;i<indices.capacity();i++){
            Indices.putInt(indices.get(i));
        }
        Indices.position(0);
        TriangleIndexVertexArray indexVertexArray=new TriangleIndexVertexArray(indices.capacity()/3,Indices,12,vertexes.capacity()/3,vertex,12);
        return indexVertexArray;
    }
    public static BvhTriangleMeshShape makeLandForm(TriangleIndexVertexArray triangleIndexVertexArray){
        return new BvhTriangleMeshShape(triangleIndexVertexArray,true,true);
    }
    public static GImpactMeshShape makeObject(TriangleIndexVertexArray triangleIndexVertexArray){
        return new GImpactMeshShape(triangleIndexVertexArray);
    }
    public static BvhTriangleMeshShape makeLandFormByVertexes(FloatBuffer vertexes,IntBuffer indices){
        TriangleIndexVertexArray triangleIndexVertexArray=makeTriangleIndexVertexArray(vertexes,indices);
        return new BvhTriangleMeshShape(triangleIndexVertexArray,true,true);
    }
    public static GImpactMeshShape makeObjectByVertexes(FloatBuffer vertexes,IntBuffer indices){
        TriangleIndexVertexArray triangleIndexVertexArray=makeTriangleIndexVertexArray(vertexes,indices);
        return new GImpactMeshShape(triangleIndexVertexArray);
    }
    public void initWorld(){
        CollisionConfiguration collisionConfiguration=new DefaultCollisionConfiguration();
        CollisionDispatcher dispatcher=new CollisionDispatcher(collisionConfiguration);
        Vector3f worldMin=new Vector3f(-1000,-1000,-1000);
        Vector3f worldMax=new Vector3f(1000,1000,1000);
        int maxProxies=1024;
        AxisSweep3 overlappingPairCache=new AxisSweep3(worldMin,worldMax,maxProxies);
        SequentialImpulseConstraintSolver solver=new SequentialImpulseConstraintSolver();
       // this.dynamicsWorld=new DiscreteDynamicsWorld(dispatcher,overlappingPairCache,solver,collisionConfiguration);
       // dynamicsWorld.setGravity(new Vector3f(0,-9.8f,0));
       // boxShape=new BoxShape(new Vector3f(Constant.UNIT_SIZE,Constant.UNIT_SIZE,Constant.UNIT_SIZE));
       // planeShape=new StaticPlaneShape(new Vector3f(0,1,0),0);
       // boxShape.setUserPointer(3);
       // boxShape.setLocalScaling(new Vector3f(1,2,1));
        Vector3f Vx=new Vector3f();
        //planeShape.setUserPointer(2);
        ;    }
    public static void CollisionHandle(DynamicsWorld dynamicsWorld) {
        dynamicsWorld.stepSimulation(Constant.TIME_STEP, Constant.MAX_SUB_STEPS);
        int num = dynamicsWorld.getDispatcher().getNumManifolds();
        for (int i = 0; i < num; i++) {
            PersistentManifold pm = dynamicsWorld.getDispatcher().getManifoldByIndexInternal(i);
            int contacts = pm.getNumContacts();
            if (contacts > 0) {
                CollisionObject Oba = (CollisionObject) pm.getBody0();
                CollisionObject Obb = (CollisionObject) pm.getBody1();
            //    int a = (int) Oba.getUserPointer();
             //   int b = (int) Obb.getUserPointer();
            }
        }
    }
}