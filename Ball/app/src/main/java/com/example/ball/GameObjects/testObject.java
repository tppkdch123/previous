package com.example.ball.GameObjects;

import android.opengl.GLES31;

import com.example.ball.R;
import com.example.ball.yellow.Objects.Camera;
import com.example.ball.yellow.Objects.GameEntity;
import com.example.ball.yellow.opengUtils.BitmapUtil;
import com.example.ball.yellow.opengUtils.ShaderUtil;
import com.example.ball.yellow.opengUtils.ShapeUtil;
import com.example.ball.yellow.opengUtils.openglUtil;
import com.jogamp.opengl.math.Matrix4;
import com.jogamp.opengl.math.Quaternion;

import java.nio.LongBuffer;

/**
 * Created by 世哲 on 2017/7/17.
 */
public class testObject extends GameEntity {
    public int bitmap;
    public Matrix4 Model;
    private float y;
    public testObject(int shaderProgram,int xNum,int yNum,float r){
        super();
        ShapeUtil.loadSphere(xNum,yNum,r,this);
        this.Program=shaderProgram;
     }
    @Override
    public void init(){
        super.init();
        initTexCoords2D();
        initNormals();
        initVertexes();
        initElementArrayBuffer();
        Model=new Matrix4();
        Model.translate(0,0,-5);
    }
    @Override
    public void drawSelf(){
        y=0.005f;
      //  Quaternion Q=new Quaternion();
        //Q.rotateByEuler(y,y,0);
        //Model.rotate(Q);
        /*GLES31.glEnable(GLES31.GL_DEPTH_TEST);
        GLES31.glUseProgram(Program);
        GLES31.glBindVertexArray(VAO.get(0));
        GLES31.glUniformMatrix4fv(GLES31.glGetUniformLocation(Program,"VPMatrix"),1,false, VP,0);
        GLES31.glUniformMatrix4fv(GLES31.glGetUniformLocation(Program,"Model"),1,false, Model.getMatrix(),0);
        GLES31.glUniform1f(GLES31.glGetUniformLocation(Program,"shininess"),this.shininess);
        GLES31.glActiveTexture(GLES31.GL_TEXTURE0);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D,this.bitmap);
        GLES31.glUniform1i(GLES31.glGetUniformLocation(Program,"sky"),0);
        GLES31.glDrawElements(GLES31.GL_TRIANGLES,indices.capacity(),GLES31.GL_UNSIGNED_INT,0);*/
    }
}
