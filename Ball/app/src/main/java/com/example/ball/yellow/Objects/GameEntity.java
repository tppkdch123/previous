package com.example.ball.yellow.Objects;

import android.opengl.GLES31;

import com.example.ball.yellow.opengUtils.ShapeUtil;
import com.example.ball.yellow.opengUtils.loadObjUtil;
import com.example.ball.yellow.opengUtils.openglUtil;
import com.jogamp.common.nio.Buffers;
import com.jogamp.graph.curve.opengl.RenderState;
import com.jogamp.opengl.Threading;
import com.jogamp.opengl.math.Matrix4;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by 世哲 on 2017/7/16.
 */

public abstract class GameEntity extends GameObject{
    public IntBuffer indices;
    public IntBuffer EBO;
    @Override
    public void init(){
        GLES31.glGenVertexArrays(1,VAO);
        GLES31.glGenBuffers(3,VBO);
        GLES31.glGenBuffers(1,EBO);
        GLES31.glBindVertexArray(VAO.get(0));
        this.pointsNum=indices.capacity();
    }
    @Override
    public void drawSelf(){
        super.drawSelf();
    }

    @Override
    public void drawDepthMap(int depthProgram){
        GLES31.glUseProgram(depthProgram);
        GLES31.glBindVertexArray(VAO.get(0));
        GLES31.glUniformMatrix4fv(GLES31.glGetUniformLocation(depthProgram,"Model"),1,false,Model,0);
        GLES31.glDrawElements(GLES31.GL_TRIANGLES,pointsNum,GLES31.GL_UNSIGNED_INT,0);
    }
    public void initElementArrayBuffer(){
        GLES31.glBindBuffer(GLES31.GL_ELEMENT_ARRAY_BUFFER,EBO.get(0));
        GLES31.glBufferData(GLES31.GL_ELEMENT_ARRAY_BUFFER,indices.capacity()*Buffers.SIZEOF_INT,indices,GLES31.GL_STATIC_DRAW);
    }

  public GameEntity(){
      super();
      EBO=IntBuffer.allocate(1);
  }
    public GameEntity(int Program){
        super();
        EBO=IntBuffer.allocate(1);
        this.Program=Program;
    }
    public GameEntity(GameEntity G){
        super(G);
    }
    public GameEntity(int Program,int Target){
        super();
        EBO=IntBuffer.allocate(1);
        this.Program=Program;
        this.singleBitmapTarget=Target;
    }
    public GameEntity(int Program,GameEntity G,int singleTargetBitmap){
        super(Program,G,singleTargetBitmap);
    }
  public GameEntity(int shaderProgram,FloatBuffer vertexes,FloatBuffer normals,FloatBuffer texCoords,IntBuffer indices,int singleBitmapTarget){
      super(shaderProgram,vertexes,normals,texCoords,singleBitmapTarget);
      EBO=IntBuffer.allocate(1);
      this.indices=indices;
  }
  public GameEntity(int shaderProgram,float[] vertexes,float[] normals,float[] texCoords,int[] indices,int singleBitmapTarget){
      super(shaderProgram,vertexes,normals,texCoords,singleBitmapTarget);
      EBO=IntBuffer.allocate(1);
      this.indices=IntBuffer.wrap(indices);
  }
  public GameEntity(int Program,float x,float y,float z,float width,float height,int width2,int height2,int num,int singleBitmapTarget){
      super(Program,singleBitmapTarget);
      EBO=IntBuffer.allocate(1);
      ShapeUtil.generateHorizontalRect(x,y,z,width,height,width2,height2,num,this);
  }
  public GameEntity(int Program,int xNum, int yNum, float R,int singleBitmapTarget){
      super(Program,singleBitmapTarget);
      EBO=IntBuffer.allocate(1);
      ShapeUtil.loadSphere(xNum,yNum,R,this);
  }
}
