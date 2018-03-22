package com.example.ball.GameObjects;

import android.opengl.GLES31;
import android.opengl.Matrix;

import com.example.ball.Data;
import com.example.ball.yellow.Objects.Camera;
import com.example.ball.yellow.Objects.GameEntity;
import com.example.ball.yellow.opengUtils.LightUtil;
import com.example.ball.yellow.opengUtils.ShaderUtil;
import com.example.ball.yellow.opengUtils.openglUtil;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.Threading;
import com.jogamp.opengl.math.Matrix4;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by 世哲 on 2017/8/30.
 */

public class glasses extends GameEntity {
    private static glasses g;
    private Matrix4 MModel=new Matrix4();
    private FloatBuffer Instance;
    private int depthProgram;
    private IntBuffer instance=IntBuffer.allocate(1);
    private glasses(int shaderProgram,float[] vertexes,float[] normals,float[] texCoords,int[] indices,int singleBitmapTarget,float[] instance,int program){
        super(shaderProgram,vertexes,normals,texCoords,indices,singleBitmapTarget);
        this.Instance=FloatBuffer.wrap(instance);
        this.depthProgram=program;
    }
    @Override
    public void drawSelf(){
        GLES31.glUseProgram(Program);
        GLES31.glBindVertexArray(VAO.get(0));
        GLES31.glUniform1f(GLES31.glGetUniformLocation(Program,"shininess"),this.shininess);
        MModel.loadIdentity();
        MModel.rotate(Camera.rotation);
        GLES31.glUniformMatrix4fv(GLES31.glGetUniformLocation(Program,"Model"),1,false,MModel.getMatrix(),0);
        GLES31.glActiveTexture(GLES31.GL_TEXTURE0);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,singleBitmapTarget);
        GLES31.glUniform1i(GLES31.glGetUniformLocation(Program,"sky"),0);
        GLES31.glActiveTexture(GLES31.GL_TEXTURE1);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, LightUtil.depthTexture);
        GLES31.glUniform1i(GLES31.glGetUniformLocation(Program,"shadowMap"),1);
        GLES31.glEnable(GLES31.GL_BLEND);
        GLES31.glBlendFunc(GLES31.GL_SRC_ALPHA,GLES31.GL_ONE_MINUS_SRC_ALPHA);
        GLES31.glDrawElementsInstanced(GLES31.GL_TRIANGLES,pointsNum,GLES31.GL_UNSIGNED_INT,0,3);
        GLES31.glDisable(GLES31.GL_BLEND);

    }
    @Override
    public void init(){
        super.init();
        initElementArrayBuffer();
        initVertexes();
        initTexCoords2D();
        initNormals();
        initInstance();
    }
    public void initInstance(){
        GLES31.glEnableVertexAttribArray(4);
        GLES31.glGenBuffers(1,instance);
        GLES31.glBindBuffer(GLES31.GL_ARRAY_BUFFER,instance.get(0));
        GLES31.glBufferData(GLES31.GL_ARRAY_BUFFER,Instance.capacity()* Buffers.SIZEOF_FLOAT,Instance,GLES31.GL_STATIC_DRAW);
        GLES31.glVertexAttribPointer(4,3,GLES31.GL_FLOAT,false,3*Buffers.SIZEOF_FLOAT,0);
        GLES31.glVertexAttribDivisor(4,1);
        GLES31.glBindVertexArray(0);
    }
    public static glasses getNewGlasses(int shaderProgram,int bitmap,int depthProgram){
        if(g==null){
        g=new glasses(shaderProgram, Data.glassesVertex,Data.glassesNormal,Data.glassTexCoord,Data.glassesIndices,bitmap,Data.glassesPianyi,depthProgram);
        g.init();
        }
        return g;
    }
    @Override
    public void drawDepthMap(int depthProgram){
        GLES31.glUseProgram(this.depthProgram);
        GLES31.glBindVertexArray(VAO.get(0));
        GLES31.glUniformMatrix4fv(GLES31.glGetUniformLocation(depthProgram,"Model"),1,false,Model,0);
        GLES31.glDrawElementsInstanced(GLES31.GL_TRIANGLES,pointsNum,GLES31.GL_UNSIGNED_INT,0,2);
    }
}
