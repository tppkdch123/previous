package com.example.ball.yellow.opengUtils;

import android.opengl.GLES31;

import com.example.ball.MyApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 世哲 on 2017/7/16.
 */

public class ShaderUtil {
    public static Map<Integer,Integer> shaderMap=new HashMap<Integer,Integer>();
    public static String getShaderContentById(int shaderSourceId) {
        String shaderContent = "";
        InputStream I = MyApplication.getContext().getResources().openRawResource(shaderSourceId);
        BufferedReader br = new BufferedReader(new InputStreamReader(I));
        String Line = "";
        try {
            while ((Line = br.readLine()) != null) {
                shaderContent += Line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shaderContent;
    }
    public static int loadShader(int type,String ShaderContent){
        int shader= GLES31.glCreateShader(type);
        GLES31.glShaderSource(shader, ShaderContent);
        GLES31.glCompileShader(shader);
        final int[] compileStatus = new int[1];
        GLES31.glGetShaderiv(shader, GLES31.GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0)
        {
            GLES31.glDeleteShader(shader);
            shader = 0;
        }
        System.out.println(GLES31.glGetShaderInfoLog(shader));
        return shader;
    }
    public static int loadShaderById(int type,int shaderId){
        if(shaderMap.containsKey(shaderId))
            return shaderMap.get(shaderId);
        else {
            String content=getShaderContentById(shaderId);
            int shader=loadShader(type,content);
            shaderMap.put(shaderId,shader);
            return shader;
        }
    }
    public static int getProgram(int vertexSource,int fragmentSource){
        int vertexShader=loadShaderById(GLES31.GL_VERTEX_SHADER,vertexSource);
        int fragmentShader=loadShaderById(GLES31.GL_FRAGMENT_SHADER,fragmentSource);
        int program=GLES31.glCreateProgram();
        GLES31.glAttachShader(program,vertexShader);
        GLES31.glAttachShader(program,fragmentShader);
        GLES31.glLinkProgram(program);
        return program;
    }
}

