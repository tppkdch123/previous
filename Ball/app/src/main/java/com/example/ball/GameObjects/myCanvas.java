package com.example.ball.GameObjects;

import android.opengl.GLES31;

import com.example.ball.yellow.Objects.GameEntity;

/**
 * Created by 世哲 on 2017/7/24.
 */

public class myCanvas extends GameEntity {
    public myCanvas(int Program,float[] vertexes,float[] normals,float[] texCoords,int[] indices,int singleTexure){
        super(Program,vertexes,normals,texCoords,indices,singleTexure);
    }
    @Override
    public void init(){
        super.init();
        initVertexes();
        initTexCoords2D();
        initElementArrayBuffer();
    }
    @Override
    public void drawSelf(){
        GLES31.glUseProgram(Program);
        GLES31.glBindVertexArray(VAO.get(0));
        GLES31.glActiveTexture(GLES31.GL_TEXTURE0);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,singleBitmapTarget);
        GLES31.glUniform1i(GLES31.glGetUniformLocation(Program,"canvas"),0);
        GLES31.glUniform1f(GLES31.glGetUniformLocation(Program,"exposure"),1f);
        GLES31.glDrawElements(GLES31.GL_TRIANGLES,this.pointsNum,GLES31.GL_UNSIGNED_INT,0);
    }
}
