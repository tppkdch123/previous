package com.example.ball.yellow.Objects;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.DynamicsWorld;

import javax.vecmath.Vector3f;

/**
 * Created by 世哲 on 2017/7/19.
 */

public interface rigidbodyMessage {
    public void enterDynamicWorld(DynamicsWorld dynamicsWorld, float x, float y, float z, CollisionShape collisionShape, float friction, float restitution,float mass);
}
