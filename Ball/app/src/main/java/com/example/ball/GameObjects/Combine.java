package com.example.ball.GameObjects;

import com.example.ball.yellow.Objects.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 世哲 on 2017/9/1.
 */

public class Combine extends GameObject{
    private List<GameObject> L;
    private int depthMap;
    public Combine(int depthMap,GameObject...g){
        L=new ArrayList<GameObject>();
        for(int i=0;i<g.length;i++)
            L.add(g[i]);
        this.depthMap=depthMap;
    }
    @Override
    public void init(){
        for(int i=0;i<L.size();i++){
            L.get(i).init();
        }
    }
    @Override
    public void drawSelf(){
        for(int i=0;i<L.size();i++){
            L.get(i).drawSelf();
        }
    }
    @Override
    public void drawDepthMap(int Program){
        for(int i=0;i<L.size();i++){
            L.get(i).drawDepthMap(depthMap);
        }
    }
}
