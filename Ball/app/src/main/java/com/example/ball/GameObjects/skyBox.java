package com.example.ball.GameObjects;

import android.opengl.GLES31;
import android.opengl.Matrix;

import com.example.ball.yellow.Objects.Camera;
import com.example.ball.yellow.Objects.GameObject;
import com.example.ball.yellow.opengUtils.openglUtil;
import com.jogamp.nativewindow.VisualIDHolder;
import com.jogamp.opengl.math.Matrix4;
import com.jogamp.opengl.math.Quaternion;

import java.nio.FloatBuffer;

import javax.vecmath.Quat4f;

/**
 * Created by 世哲 on 2017/7/19.
 */

public class skyBox extends GameObject {
   public skyBox(int Program, int singleBitmapTarget, FloatBuffer vertexBuffer,FloatBuffer texCoordBuffer){
        super(Program,singleBitmapTarget);
        this.vertexes=vertexBuffer;
        this.texCoords=texCoordBuffer;
    }
    public skyBox(int Program, int singleBitmapTarget, float[] vertex){
        super(Program,singleBitmapTarget);
        this.vertexes=FloatBuffer.wrap(vertex);
    }
    @Override
    public void init(){
        super.init();
        initVertexes();
    }
    @Override
    public void drawSelf(){
        GLES31.glDisable(GLES31.GL_DEPTH_TEST);
        GLES31.glUseProgram(Program);
        GLES31.glBindVertexArray(VAO.get(0));
        GLES31.glUniformMatrix4fv(GLES31.glGetUniformLocation(Program,"VPMatrix"),1,false,Camera.skyBoxVP.getMatrix(),0);
        GLES31.glActiveTexture(GLES31.GL_TEXTURE0);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,this.singleBitmapTarget);
        GLES31.glUniform1i(GLES31.glGetUniformLocation(Program,"skyBox"),0);
        GLES31.glDrawArrays(GLES31.GL_TRIANGLES,0,pointsNum);
        GLES31.glEnable(GLES31.GL_DEPTH_TEST);
    }
}
