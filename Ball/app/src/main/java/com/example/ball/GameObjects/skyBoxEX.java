package com.example.ball.GameObjects;

import android.opengl.GLES31;

import com.example.ball.yellow.Objects.Camera;
import com.example.ball.yellow.Objects.GameEntity;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by 世哲 on 2017/7/23.
 */

public class skyBoxEX extends GameEntity {
    public skyBoxEX(int Program, int singleBitmapTarget, float[] vertex,int[] indices){
        super(Program,singleBitmapTarget);
        this.vertexes=FloatBuffer.wrap(vertex);
        this.indices= IntBuffer.wrap(indices);
    }
    @Override
    public void init(){
        super.init();  initElementArrayBuffer();
        initVertexes();
    }
    @Override
    public void drawSelf(){
        GLES31.glDisable(GLES31.GL_DEPTH_TEST);
        GLES31.glUseProgram(Program);
        GLES31.glBindVertexArray(VAO.get(0));
        GLES31.glUniformMatrix4fv(GLES31.glGetUniformLocation(Program,"VPMatrix"),1,false, Camera.skyBoxVP.getMatrix(),0);
        GLES31.glActiveTexture(GLES31.GL_TEXTURE0);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,this.singleBitmapTarget);
        GLES31.glUniform1i(GLES31.glGetUniformLocation(Program,"skyBox"),0);
        GLES31.glDrawElements(GLES31.GL_TRIANGLES,this.pointsNum,GLES31.GL_UNSIGNED_INT,0);
        GLES31.glEnable(GLES31.GL_DEPTH_TEST);
    }
}
