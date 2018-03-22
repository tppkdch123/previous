package com.example.ball.yellow.opengUtils;

import com.example.ball.yellow.Objects.GameEntity;
import com.example.ball.yellow.Objects.GameObject;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by 世哲 on 2017/7/17.
 */

public class ShapeUtil {
    public static void loadSphere(int xNum, int yNum, float R, GameEntity G){
        List<Float> L=new ArrayList<Float>();
        float y=180*1.0f/yNum;
        float x=360*1.0f/xNum;
        int size=(xNum+1)*(yNum+1);
        int indius=2*3*xNum*(yNum-1);
        float[] vertices=new float[size*3];
        float[] textureCoords=new float[(xNum+1)*(yNum+1)*2];
        int[] indices=new int[indius];
        L.add(0f);L.add(R);L.add(0f);
        int vertIndex=0,index=0;
        for(int i=0;i<=yNum;i++){
            float angle1=90-i*y;
            float Y=R*(float)Math.sin(angle1*Math.PI/180);
            float linshi=R*(float)Math.cos(angle1*Math.PI/180);
            for(int j=0;j<=xNum;j++){
                float angle3=(j+1)*x;float angle4=j*x;
                float X=linshi*(float)Math.sin(angle4*Math.PI/180);
                float Z=linshi*(float)Math.cos(angle4*Math.PI/180);
                vertices[vertIndex++] = X;
                vertices[vertIndex++] = Y;
                vertices[vertIndex++] = Z;
                if (i > 0 && j > 0) {
                    int a = (xNum + 1) * i + j;
                    int b = (xNum+ 1) * i + j - 1;
                    int c = (xNum + 1) * (i - 1) + j - 1;
                    int d = (xNum + 1) * (i - 1) + j;

                    if (i == yNum) {
                        indices[index++] = d;
                        indices[index++] = c;
                        indices[index++] = a;
                    } else if (i == 1) {
                        indices[index++] = c;
                        indices[index++] = b;
                        indices[index++] = a;
                    } else {
                        indices[index++] = c;
                        indices[index++] = b;
                        indices[index++] = a;
                        indices[index++] = d;
                        indices[index++] = c;
                        indices[index++] = a;
                    }
                }
            }
        }
        int uv=0;
        float u=1.0f/xNum;
        float v=1.0f/yNum;
        for(int i=0;i<=yNum;i++)
            for(int j=0;j<=xNum;j++){
                textureCoords[uv++]=u*j;
                textureCoords[uv++]=v*i;
            }
        G.vertexes=FloatBuffer.wrap(vertices);
        G.normals=FloatBuffer.wrap(vertices);
        G.texCoords=FloatBuffer.wrap(textureCoords);
        G.indices=IntBuffer.wrap(indices);
    }
    public static void generateHorizontalRect(float x,float y,float z,float width,float height,int width2,int height2,int num,GameObject G){
        List<Float> L=new ArrayList<Float>();
        List<Float> L2=new ArrayList<Float>();
        float f1=1.0f/width2;
        float f2=1.0f/height2;
        float f3=width/width2;
        float f4=height/height2;
        for(int i=0;i<width2;i++){
            for(int j=0;j<height2;j++){
                float x1=x+i*f3;
                float y1=y;
                float z1=z+j*f4;
                float x2=x+(i)*f3;
                float y2=y;
                float z2=z+(j+1)*f4;
                float x3=x+(i+1)*f3;
                float y3=y;
                float z3=z+j*f4;
                float x4=x+(i+1)*f3;
                float y4=y;
                float z4=z+(j+1)*f4;
                L.add(x1);L.add(y1);L.add(z1); L2.add(0+i*f1);L2.add(1-j*f2);
                L.add(x4);L.add(y4);L.add(z4); L2.add(0+(i+1)*f1);L2.add(1-(j+1)*f2);
                L.add(x3);L.add(y3);L.add(z3); L2.add(0+i*f1);L2.add(1-(j+1)*f2);
                L.add(x1);L.add(y1);L.add(z1); L2.add(0+i*f1);L2.add(1-j*f2);
                L.add(x2);L.add(y2);L.add(z2); L2.add(0+(i+1)*f1);L2.add(1-j*f2);
                L.add(x4);L.add(y4);L.add(z4); L2.add(0+(i+1)*f1);L2.add(1-(j+1)*f2);
            }
        }
        for(int i=0;i<L2.size();i++)
            L2.set(i,L.get(i)*num);
        G.vertexes=openglUtil.convertToBuffer(L);
        G.texCoords=openglUtil.convertToBuffer(L2);
        float [] f=new float[G.vertexes.capacity()];
        for(int i=0;i<f.length;i+=3){
            f[i]=0;
            f[i+1]=1;
            f[i+2]=0;
        }
        G.normals=FloatBuffer.wrap(f);
    }
    public static void generateHorizontalRect(float x,float y,float z,float width,float height,int width2,int height2,int num,GameEntity G){
        List<Float> L=new ArrayList<Float>();
        List<Float> L2=new ArrayList<Float>();
        List<Integer> A1=new ArrayList<Integer>();
        float f1=1.0f/width2;
        float f2=1.0f/height2;
        float f3=width/width2;
        float f4=height/height2;
        for(int i=0;i<width2+1;i++){
            for(int j=0;j<height2+1;j++){
                L.add(x+i*f3);L.add(y);L.add(z+j*f4);
                L2.add(f1*i*num);L2.add(f2*j*num);
            }
        }
        for(int i=0;i<width2;i++){
            for(int j=0;j<height2;j++){
                int a=(height2+1)*i+j;
                A1.add(a);A1.add(a+height2+2);A1.add(a+height2+1);
                A1.add(a);A1.add(a+1);A1.add(a+height2+2);
            }
        }
        G.vertexes=openglUtil.convertToBuffer(L);
        G.texCoords=openglUtil.convertToBuffer(L2);
        float[] f=new float[L.size()];
        for(int i=0;i<f.length;i+=3){
            f[i]=0;f[i+1]=1;f[i+2]=0;
        }
        G.normals=FloatBuffer.wrap(f);
        G.indices= openglUtil.convertToIntBuffer(A1);
    }
    public static void generateVerticalRect(float x,float y,float z,float width,float height,int width2,int height2,GameObject G){
        List<Float> L=new ArrayList<Float>();
        List<Float> L2=new ArrayList<Float>();
        float f1=1.0f/width2;
        float f2=1.0f/height2;
        float f3=width/width2;
        float f4=height/height2;
        for(int i=0;i<width2;i++){
            for(int j=0;j<height2;j++){
                float x1=x+i*f3;
                float y1=y+j*f4;
                float z1=z;
                float x2=x+(i)*f3;
                float y2=y+(j+1)*f4;
                float z2=z;
                float x3=x+(i+1)*f3;
                float y3=y+(j)*f4;
                float z3=z;
                float x4=x+(i+1)*f3;
                float y4=y+(j+1)*f4;
                float z4=z;
                L.add(x1);L.add(y1);L.add(z1); L2.add(0+i*f1);L2.add(0+j*f2);
                L.add(x4);L.add(y4);L.add(z4); L2.add(0+(i+1)*f1);L2.add(0+(j+1)*f2);
                L.add(x2);L.add(y2);L.add(z2); L2.add(0+i*f1);L2.add(0+(j+1)*f2);
                L.add(x1);L.add(y1);L.add(z1); L2.add(0+i*f1);L2.add(0+j*f2);
                L.add(x3);L.add(y3);L.add(z3); L2.add(0+(i+1)*f1);L2.add(0+j*f2);
                L.add(x4);L.add(y4);L.add(z4); L2.add(0+(i+1)*f1);L2.add(0+(j+1)*f2);
            }
        }
        G.vertexes=openglUtil.convertToBuffer(L);
        G.texCoords=openglUtil.convertToBuffer(L2);
        float [] f=new float[G.vertexes.capacity()];
        for(int i=0;i<f.length;i+=3){
            f[i]=0;
            f[i+1]=0;f[i+2]=1;
        }
        G.normals=FloatBuffer.wrap(f);
    }
}
