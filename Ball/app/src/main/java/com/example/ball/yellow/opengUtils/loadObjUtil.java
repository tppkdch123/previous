package com.example.ball.yellow.opengUtils;

import com.example.ball.MyApplication;
import com.example.ball.yellow.Objects.GameEntity;
import com.example.ball.yellow.Objects.GameObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static com.example.ball.yellow.opengUtils.openglUtil.convertTo;

/**
 * Created by 世哲 on 2017/7/16.
 */

public class loadObjUtil {
    public static InputStream getInputStreamOfObj(int objId){
        return MyApplication.getContext().getResources().openRawResource(objId);
    }
    public static void loadObj1(GameObject G,int ObjId) {
        ArrayList<Float> Vertexes=new ArrayList<Float>();
        ArrayList<Float> Normals=new ArrayList<Float>();
        ArrayList<Float> TexCoords=new ArrayList<Float>();
        ArrayList<Float> tVertexes=new ArrayList<Float>();
        ArrayList<Float> tNormals=new ArrayList<Float>();
        ArrayList<Float> tTexCoords=new ArrayList<Float>();
        InputStream I = getInputStreamOfObj(ObjId);
        InputStreamReader reader = new InputStreamReader(I);
        BufferedReader br = new BufferedReader(reader);
        String temps = null;
        try {
            while ((temps = br.readLine()) != null) {
                String[] temp = temps.split("[ ]+");
                if (temp[0].trim().equals("v")) {
                    tVertexes.add(Float.parseFloat(temp[1]));
                    tVertexes.add(Float.parseFloat(temp[2]));
                    tVertexes.add(Float.parseFloat(temp[3]));
                }
                else if(temp[0].trim().equals("vt")){
                    tTexCoords.add(Float.parseFloat(temp[1]));
                    tTexCoords.add(Float.parseFloat(temp[2]));
                    tTexCoords.add(Float.parseFloat(temp[3]));
                }
                else if(temp[0].trim().equals("vn")){
                    tNormals.add(Float.parseFloat(temp[1]));
                    tNormals.add(Float.parseFloat(temp[2]));
                    tNormals.add(Float.parseFloat(temp[3]));
                }
                else if(temp[0].trim().equals("f")){
                    int vIndex1=Integer.parseInt(temp[1].split("/")[0])-1;
                    int vIndex2=Integer.parseInt(temp[2].split("/")[0])-1;
                    int vIndex3=Integer.parseInt(temp[3].split("/")[0])-1;
                    int tIndex1=Integer.parseInt(temp[1].split("/")[1])-1;
                    int tIndex2=Integer.parseInt(temp[2].split("/")[1])-1;
                    int tIndex3=Integer.parseInt(temp[3].split("/")[1])-1;
                    int nIndex1=Integer.parseInt(temp[1].split("/")[2])-1;
                    int nIndex2=Integer.parseInt(temp[2].split("/")[2])-1;
                    int nIndex3=Integer.parseInt(temp[3].split("/")[2])-1;
                    Vertexes.add(tVertexes.get(3*vIndex1));
                    Vertexes.add(tVertexes.get(3*vIndex1+1));
                    Vertexes.add(tVertexes.get(3*vIndex1+2));
                    Vertexes.add(tVertexes.get(3*vIndex2));
                    Vertexes.add(tVertexes.get(3*vIndex2+1));
                    Vertexes.add(tVertexes.get(3*vIndex2+2));
                    Vertexes.add(tVertexes.get(3*vIndex3));
                    Vertexes.add(tVertexes.get(3*vIndex3+1));
                    Vertexes.add(tVertexes.get(3*vIndex3+2));
                    TexCoords.add(tTexCoords.get(3*tIndex1));
                    TexCoords.add(tTexCoords.get(3*tIndex1+1));
                    TexCoords.add(tTexCoords.get(3*tIndex1+2));
                    TexCoords.add(tTexCoords.get(3*tIndex2));
                    TexCoords.add(tTexCoords.get(3*tIndex2+1));
                    TexCoords.add(tTexCoords.get(3*tIndex2+2));
                    TexCoords.add(tTexCoords.get(3*tIndex3));
                    TexCoords.add(tTexCoords.get(3*tIndex3+1));
                    TexCoords.add(tTexCoords.get(3*tIndex3+2));
                    Normals.add(tNormals.get(3*nIndex1));
                    Normals.add(tNormals.get(3*nIndex1+1));
                    Normals.add(tNormals.get(3*nIndex1+2));
                    Normals.add(tNormals.get(3*nIndex2));
                    Normals.add(tNormals.get(3*nIndex2+1));
                    Normals.add(tNormals.get(3*nIndex2+2));
                    Normals.add(tNormals.get(3*nIndex3));
                    Normals.add(tNormals.get(3*nIndex3+1));
                    Normals.add(tNormals.get(3*nIndex3+2));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        float[] v= openglUtil.convertTo(Vertexes);
        float[] t=openglUtil.convertTo(TexCoords);
        float[] n=openglUtil.convertTo(Normals);
        G.vertexes= FloatBuffer.wrap(v);
        G.texCoords=FloatBuffer.wrap(t);
        G.normals=FloatBuffer.wrap(n);
    }
}
