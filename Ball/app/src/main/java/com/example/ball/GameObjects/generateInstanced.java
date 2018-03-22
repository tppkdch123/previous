package com.example.ball.GameObjects;

import android.graphics.Shader;
import android.opengl.GLES31;
import android.opengl.Matrix;

import com.example.ball.yellow.Objects.GameEntity;
import com.example.ball.yellow.Objects.GameObject;
import com.example.ball.yellow.opengUtils.LightUtil;
import com.example.ball.yellow.opengUtils.ShaderUtil;
import com.jogamp.common.nio.Buffers;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by 世哲 on 2017/9/1.
 */

public class generateInstanced extends GameObject {
    private FloatBuffer Instance;
    private IntBuffer instance=IntBuffer.allocate(1);
    public generateInstanced(int shaderProgram,int objId,int singleBitmapTarget,float[] instance){
        super(shaderProgram, objId, singleBitmapTarget);
        Instance=FloatBuffer.wrap(instance);
    }
    @Override
    public void init(){
        super.init();
        initVertexes();
        initTexCoords3D();
        initNormals();
        initInstance();
        Matrix.setIdentityM(Model,0);
        Matrix.translateM(Model,0,0,-0.49f,0);
        Matrix.scaleM(Model,0,0.6f,0.6f,0.6f);
    }
    @Override
    public void drawSelf(){
        super.drawSelf();
        GLES31.glActiveTexture(GLES31.GL_TEXTURE0);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,singleBitmapTarget);
        GLES31.glUniform1i(GLES31.glGetUniformLocation(Program,"sky"),0);
        GLES31.glActiveTexture(GLES31.GL_TEXTURE1);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, LightUtil.depthTexture);
        GLES31.glUniform1i(GLES31.glGetUniformLocation(Program,"shadowMap"),1);
        GLES31.glDrawArraysInstanced(GLES31.GL_TRIANGLES,0,pointsNum,Instance.capacity()/3);
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
    @Override
    public void drawDepthMap(int depthProgram){
        GLES31.glUseProgram(depthProgram);
        GLES31.glBindVertexArray(VAO.get(0));
        GLES31.glUniformMatrix4fv(GLES31.glGetUniformLocation(depthProgram,"Model"),1,false,Model,0);
        GLES31.glDrawArraysInstanced(GLES31.GL_TRIANGLES,0,pointsNum,Instance.capacity()/3);
    }
}
