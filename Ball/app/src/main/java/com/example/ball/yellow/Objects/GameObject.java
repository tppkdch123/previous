package com.example.ball.yellow.Objects;

import android.opengl.GLES31;

import com.example.ball.MyGLSurfaceView;
import com.example.ball.yellow.opengUtils.loadObjUtil;
import com.example.ball.yellow.opengUtils.openglUtil;
import com.jogamp.common.nio.Buffers;
import com.jogamp.graph.curve.opengl.RenderState;
import com.jogamp.opengl.math.Matrix4;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import jogamp.graph.font.typecast.ot.table.Program;

/**
 * Created by 世哲 on 2017/7/16.
 */

public abstract class GameObject {
    public float[] VP;
    public FloatBuffer vertexes;
    public FloatBuffer normals;
    public FloatBuffer texCoords;
    public IntBuffer VAO;
    public IntBuffer VBO;
    public int Program;
    public int singleBitmapTarget;
    public int shininess=32;
    public float[] Model=new float[16];
    public MyGLSurfaceView GLView;
    public int pointsNum;
    public int shadowMap;
    public void init(){
        GLES31.glGenVertexArrays(1,VAO);
        GLES31.glGenBuffers(3,VBO);
        GLES31.glBindVertexArray(VAO.get(0));
        pointsNum=vertexes.capacity()/3;
    };
    public void drawSelf(){
        GLES31.glUseProgram(Program);
        GLES31.glBindVertexArray(VAO.get(0));
        GLES31.glUniform1f(GLES31.glGetUniformLocation(Program,"shininess"),this.shininess);
        GLES31.glUniformMatrix4fv(GLES31.glGetUniformLocation(Program,"Model"),1,false,Model,0);
    };
    public void beginDraw(){
        GLES31.glUseProgram(Program);
        GLES31.glBindVertexArray(VAO.get(0));
    }
    public void madeShadowMap(int n){
        GLES31.glUniform1f(GLES31.glGetUniformLocation(Program,"shadowMap"),n);
    }
    public void initVertexes(){
        GLES31.glBindBuffer(GLES31.GL_ARRAY_BUFFER,VBO.get(0));
        GLES31.glBufferData(GLES31.GL_ARRAY_BUFFER,this.vertexes.capacity()* Buffers.SIZEOF_FLOAT,this.vertexes,GLES31.GL_STATIC_DRAW);
        GLES31.glVertexAttribPointer(0,3,GLES31.GL_FLOAT,false,3*Buffers.SIZEOF_FLOAT,0);
        GLES31.glEnableVertexAttribArray(0);
        GLES31.glBindBuffer(GLES31.GL_ARRAY_BUFFER,0);
    }
    public void initNormals(){
        GLES31.glBindBuffer(GLES31.GL_ARRAY_BUFFER,VBO.get(1));
        GLES31.glBufferData(GLES31.GL_ARRAY_BUFFER,normals.capacity()* Buffers.SIZEOF_FLOAT,normals,GLES31.GL_STATIC_DRAW);
        GLES31.glVertexAttribPointer(1,3,GLES31.GL_FLOAT,false,3*Buffers.SIZEOF_FLOAT,0);
        GLES31.glEnableVertexAttribArray(1);
        GLES31.glBindBuffer(GLES31.GL_ARRAY_BUFFER,0);
    }
    public void initTexCoords3D(){
        GLES31.glBindBuffer(GLES31.GL_ARRAY_BUFFER,VBO.get(2));
        GLES31.glBufferData(GLES31.GL_ARRAY_BUFFER,texCoords.capacity()* Buffers.SIZEOF_FLOAT,texCoords,GLES31.GL_STATIC_DRAW);
        GLES31.glVertexAttribPointer(2,3,GLES31.GL_FLOAT,false,3*Buffers.SIZEOF_FLOAT,0);
        GLES31.glEnableVertexAttribArray(2);
        GLES31.glBindBuffer(GLES31.GL_ARRAY_BUFFER,0);
    }
    public void initTexCoords2D(){
        GLES31.glBindBuffer(GLES31.GL_ARRAY_BUFFER,VBO.get(2));
        GLES31.glBufferData(GLES31.GL_ARRAY_BUFFER,texCoords.capacity()* Buffers.SIZEOF_FLOAT,texCoords,GLES31.GL_STATIC_DRAW);
        GLES31.glVertexAttribPointer(2,2,GLES31.GL_FLOAT,false,2*Buffers.SIZEOF_FLOAT,0);
        GLES31.glEnableVertexAttribArray(2);
        GLES31.glBindBuffer(GLES31.GL_ARRAY_BUFFER,0);
    }
    public GameObject(){
        VAO=IntBuffer.allocate(1);
        VBO=IntBuffer.allocate(3);
        this.VP=Camera.VP;
    }
    public GameObject(int shaderProgram,int singleBitmapTarget){
        this();
        this.Program= shaderProgram;
        this.singleBitmapTarget=singleBitmapTarget;
    }
    public GameObject(int shaderProgram,int objId,int singleBitmapTarget){
        this();
        this.Program=shaderProgram;
        loadObjUtil.loadObj1(this,objId);
        this.singleBitmapTarget=singleBitmapTarget;
    }
    public GameObject(int shaderProgram,FloatBuffer vertexes,FloatBuffer normals,FloatBuffer texCoords,int singleBitmapTarget){
        this();
        this.Program=shaderProgram;
        this.vertexes=vertexes;
        this.normals=normals;
        this.texCoords=texCoords;
        this.singleBitmapTarget=singleBitmapTarget;
    }
    public GameObject(int shaderProgram,float[] vertexes,float[] normals,float[] texCoords,int singleBitmapTarget){
        this();
        this.Program=shaderProgram;
        this.vertexes=FloatBuffer.wrap(vertexes);
        this.normals=FloatBuffer.wrap(normals);
        this.texCoords=FloatBuffer.wrap(texCoords);
        this.singleBitmapTarget=singleBitmapTarget;
    }
    public GameObject(GameObject G){
        VAO=IntBuffer.allocate(1);
        VP=Camera.VP;
        VAO.put(G.VAO.get(0));
        VAO.flip();
        this.pointsNum=G.pointsNum;
        this.Program=G.Program;
        this.singleBitmapTarget=G.singleBitmapTarget;
    }
    public GameObject(int Program,GameObject G,int singleBitmapTarget){
        VAO=IntBuffer.allocate(1);
        VP=Camera.VP;
        VAO.put(G.VAO.get(0));
        VAO.flip();
        this.pointsNum=G.pointsNum;
        this.Program= Program;
        this.singleBitmapTarget=singleBitmapTarget;
    }
    public void setGLView(MyGLSurfaceView M){
        this.GLView=M;
    }
   // public void initDepthMap(int depthProgram){

    //}
    public void drawDepthMap(int depthProgram){
        GLES31.glUseProgram(depthProgram);
        GLES31.glBindVertexArray(VAO.get(0));
        GLES31.glUniformMatrix4fv(GLES31.glGetUniformLocation(depthProgram,"Model"),1,false,Model,0);
        GLES31.glDrawArrays(GLES31.GL_TRIANGLES,0,pointsNum);
    }
}
