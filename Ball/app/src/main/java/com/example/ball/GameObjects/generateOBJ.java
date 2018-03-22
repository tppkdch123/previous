package com.example.ball.GameObjects;

import android.opengl.GLES31;
import android.opengl.Matrix;
import com.example.ball.yellow.Objects.GameObject;
import com.example.ball.yellow.opengUtils.LightUtil;

/**
 * Created by 世哲 on 2017/9/1.
 */

public class generateOBJ extends GameObject {
    public generateOBJ(int shaderProgram,int objId,int singleBitmapTarget){
        super(shaderProgram, objId, singleBitmapTarget);
    }
    @Override
    public void init(){
        super.init();
        initVertexes();
        initTexCoords3D();
        initNormals();
        Matrix.setIdentityM(Model,0);
        Matrix.translateM(Model,0,0,-0.49f,0);
        Matrix.scaleM(Model,0,0.5f,0.5f,0.5f);
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
        GLES31.glDrawArrays(GLES31.GL_TRIANGLES,0,pointsNum);
    }

}