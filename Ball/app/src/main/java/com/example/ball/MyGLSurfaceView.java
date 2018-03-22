package com.example.ball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.opengl.GLES10;
import android.opengl.GLES10Ext;
import android.opengl.GLES31;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodSession;
import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.narrowphase.PersistentManifold;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.QuaternionUtil;
import com.example.ball.GameObjects.Combine;
import com.example.ball.GameObjects.flatGround;
import com.example.ball.GameObjects.generateInstanced;
import com.example.ball.GameObjects.generateOBJ;
import com.example.ball.GameObjects.glasses;
import com.example.ball.GameObjects.myCanvas;
import com.example.ball.GameObjects.plane;
import com.example.ball.GameObjects.rigidbodyObject;
import com.example.ball.GameObjects.skyBox;
import com.example.ball.GameObjects.skyBoxEX;
import com.example.ball.GameObjects.testObject;
import com.example.ball.yellow.Objects.Camera;
import com.example.ball.yellow.Objects.GameObject;
import com.example.ball.yellow.aboutBullet.BulletUtil;
import com.example.ball.yellow.aboutBullet.Constant;
import com.example.ball.yellow.opengUtils.BitmapUtil;
import com.example.ball.yellow.opengUtils.FBOUtil;
import com.example.ball.yellow.opengUtils.LightUtil;
import com.example.ball.yellow.opengUtils.ShaderUtil;
import com.example.ball.yellow.opengUtils.ShapeUtil;
import com.example.ball.yellow.opengUtils.openglUtil;
import com.jogamp.common.util.InterruptSource;
import com.jogamp.opengl.math.Matrix4;
import com.jogamp.opengl.math.Quaternion;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.LogRecord;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

/**
 * Created by 世哲 on 2017/7/16.
 */

public class MyGLSurfaceView extends GLSurfaceView {
    private float y;
    private float x;
    private float lastX;
    private float lastY;
    public DiscreteDynamicsWorld dynamicsWorld;
    public BoxShape boxShape;
    public boolean isDown;
    Vector3f v=new Vector3f(0,1,0);
    public Random random;
    public StaticPlaneShape planeShape;
    public HandlerThread ht;
    public Handler handler;
    public MyRenderer myRenderer;
    public RigidBody testbody;
    public boolean goHead;
    public SphereShape sphereShape;
    public MainActivity M;
    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(3);
        setEGLConfigChooser(8,8,8,8,16,8);
        this.M=(MainActivity)context;
       /* ht=new HandlerThread("yellow");
        ht.start();
        handler=new Handler(ht.getLooper(),new Handler.Callback(){
            @Override
            public boolean handleMessage(Message msg) {
                return false;
            }
        });*/
        myRenderer=new MyRenderer();
        myRenderer.myGLSurfaceView=this;
        setRenderer(myRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        random=new Random();
        initWorld();
    }
    class MyRenderer implements Renderer {
        private int fbo1;
        private int texture1;
        public GLSurfaceView myGLSurfaceView;
        public float f;
        private int width;
        private int height;
        private testObject t;
        private long time;
        private flatGround ground;
        private int depthProgram;
        private myCanvas mc;
        public skyBoxEX skyBox;
        public ArrayList<plane> planes=new ArrayList<plane>();
        public ArrayList<GameObject> rigidbodyObjects=new ArrayList<GameObject>();
        @Override
        public void onSurfaceCreated(final GL10 gl, EGLConfig config) {
            LightUtil.initLight(new float[]{-1,-1,-1},new float[]{0.7f,0.7f,0.7f},0.2f);
            int defaultShader=ShaderUtil.getProgram(R.raw.defaultvertexshader2,R.raw.defaultfragmentshader2);
            int glassesShader=ShaderUtil.getProgram(R.raw.glassesvertexshader,R.raw.glassesfragmentshader);
            int glassesDepthShader=ShaderUtil.getProgram(R.raw.glassesvertexshadow,R.raw.glassesfragshadow);
            new initTask().execute();
            Camera.initUniformBlockVPMatrix();
            GLES31.glEnable(GLES31.GL_CULL_FACE);
            GLES31.glFrontFace(GLES31.GL_CCW);
            GLES31.glCullFace(GLES31.GL_BACK);
            GLES31.glEnable(GLES31.GL_DEPTH_TEST);
            glasses g=glasses.getNewGlasses(glassesShader,BitmapUtil.defaultGenerateTexture2D(R.drawable.circle),glassesDepthShader);
            rigidbodyObjects.add(g);
            int linshi=BitmapUtil.defaultGenerateTexture2D(R.drawable.desert_evening_left);
            Combine C=new Combine(glassesDepthShader,new generateInstanced(glassesShader,R.raw.cy12,BitmapUtil.defaultGenerateTexture2D2(R.drawable.qita),Data.roomInstance),
                    new generateInstanced(glassesShader,R.raw.mutou,BitmapUtil.defaultGenerateTexture2D2(R.drawable.mutou2),Data.roomInstance),
                    new generateInstanced(glassesShader,R.raw.plane,BitmapUtil.defaultGenerateTexture2D2(R.drawable.dimian),Data.roomInstance)
            ,new generateInstanced(glassesShader,R.raw.wa,BitmapUtil.defaultGenerateTexture2D(R.drawable.wa2),Data.roomInstance),
                   new generateInstanced(glassesShader,R.raw.sgtou,BitmapUtil.defaultGenerateTexture2D2(R.drawable.shitou),Data.roomInstance),
                   new generateInstanced(glassesShader,R.raw.qiang,BitmapUtil.defaultGenerateTexture2D2(R.drawable.qiang2),Data.roomInstance));
            C.init();
            rigidbodyObjects.add(C);
           ground=new flatGround(defaultShader,-120,0,-120,240,240,20,15,20,BitmapUtil.defaultGenerateTexture2D(R.drawable.cao));
           rigidbodyObject r=new rigidbodyObject(defaultShader,20, 20, 1,BitmapUtil.defaultGenerateTexture2D(R.drawable.earth));
            rigidbodyObjects.add(r);
            r.init();
            r.enterDynamicWorld(dynamicsWorld,0,3,-3,sphereShape,0.2f,0.7f,3);
            Camera.charaterController=r.body;
            ground.enterDynamicsWorld(dynamicsWorld,0,-0.5f,0,planeShape,new Vector3f(0,0,0),0.3f,0.3f);
            IntBuffer h=BitmapUtil.generateSkyBoxTextureByRawId(R.drawable.alpine_top,R.drawable.alpine_top,
                    R.drawable.alpine_left,R.drawable.alpine_right,R.drawable.alpine_front,R.drawable.alpine_back);
            skyBox=new skyBoxEX(ShaderUtil.getProgram(R.raw.skyboxvertexshader,R.raw.skyboxfragmentshader),h.get(0),Data.getSkyBoxVertexes,Data.SkyBoxIndics);
            skyBox.init();
            generate(3,3,2,defaultShader);generate(-3,2,5,defaultShader);generate(0,4,-2,defaultShader);generate(1,2,-2,defaultShader);
            ground.init();
            testbody=r.body;
            depthProgram=ShaderUtil.getProgram(R.raw.shadowvertexshader,R.raw.shadowfragmentshader);
            new Thread(){
                public void run(){
                    while(true) {
                        BulletUtil.CollisionHandle(dynamicsWorld);
                        if(testbody.isActive()==false){
                            testbody.activate(true);
                        }
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
            new Thread(){
                @Override
                public void run(){
                    while(true) {
                        Camera.followGameObject();
                        if (goHead) {
                            testbody.applyCentralForce(new Vector3f(Camera.objectfront[0] * 10, Camera.objectfront[1] * 10, Camera.objectfront[2] * 10));
                        }
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            f=width*1.0f/height;
            this.width=width;
            this.height=height;
            generateFBO1(width,height);
            GLES31.glViewport(0, 0, width, height);
            initCanvas();
        }
        @Override
        public void onDrawFrame(GL10 gl) {
            long now=System.currentTimeMillis();
            double d=1000/(now-time);
            M.fps=d;
            time=now;
            Camera.updateCameraMatrix();
            Camera.updateCameraPosition();
            LightUtil.updateLightMatrix();
            FBOUtil.beforeDrawDepthMap(LightUtil.depthFbo);
            for(int i=0;i<rigidbodyObjects.size();i++) {
                rigidbodyObjects.get(i).drawDepthMap(depthProgram);
           }
           GLES31.glCullFace(GLES31.GL_FRONT);
           for(int i=0;i<planes.size();i++)
               planes.get(i).drawDepthMap(depthProgram);
            GLES31.glCullFace(GLES31.GL_BACK);
           ground.drawDepthMap(depthProgram);
            FBOUtil.afterDrawDepthMap(width,height);
            GLES31.glBindFramebuffer(GLES31.GL_FRAMEBUFFER,0);
            GLES31.glClearColor(0,0,0,1);
            GLES31.glClear(GLES31.GL_COLOR_BUFFER_BIT|GLES31.GL_DEPTH_BUFFER_BIT|GLES31.GL_STENCIL_BUFFER_BIT);
            skyBox.drawSelf();
            for(int i=0;i<rigidbodyObjects.size();i++) {
              rigidbodyObjects.get(i).drawSelf();
            } ground.drawSelf();
            for(int i=0;i<planes.size();i++)
                planes.get(i).drawSelf();
          // GLES31.glBindFramebuffer(GLES31.GL_FRAMEBUFFER,0);
          //  GLES31.glClearColor(0,0,0,1);
           // GLES31.glClear(GLES31.GL_COLOR_BUFFER_BIT|GLES31.GL_DEPTH_BUFFER_BIT|GLES31.GL_STENCIL_BUFFER_BIT);
          //  mc.drawSelf();
        }
        public void generateBox(int defaultShader,int linshi){
            createPlane(defaultShader,0,4,-7,BitmapUtil.defaultGenerateTexture2D(R.drawable.desert_evening_front));
            createPlane(defaultShader,0,2,3,linshi);
            createPlane(defaultShader,0,7,-3,linshi);createPlane(defaultShader,4,8,5,linshi);
            createPlane(defaultShader,2,6,-2,linshi);
            createPlane(defaultShader,5,10,-7,linshi);
            createPlane(defaultShader,-8,15,-3,linshi);
            createPlane(defaultShader,-3,13,-2,linshi);
        }
        public void generate(float x,float y,float z,int program){
            rigidbodyObject r=new rigidbodyObject(program,20, 20, 1f,BitmapUtil.defaultGenerateTexture2D(R.drawable.yellow1));
            r.enterDynamicWorld(dynamicsWorld,x,y,z,sphereShape,0.7f,0.7f,1);
            r.init();
            rigidbodyObjects.add(r);
        }
        public void initCanvas(){
            this.mc=new myCanvas(ShaderUtil.getProgram(R.raw.resultvertexshader,R.raw.resultfragmentshader),Data.canvasVertexes,Data.canvasNormals,Data.canvasTexCoord,Data.canvasIndices,texture1);
            mc.init();
        }
        public void generateFBO1(int width,int height){
            texture1=BitmapUtil.getFBOTexure(width,height);
            fbo1=FBOUtil.generateFBO1(texture1,FBOUtil.generateRenderer1(width,height));
        }
        public void createPlane(int program,float x,float y,float z,int a){
            plane p=new plane(program,Data.values,Data.normal,Data.uv,a);
            p.init();
            p.enterDynamicWorld(dynamicsWorld,x,y,z,boxShape,1f,0.8f,0);
            synchronized (planes){
            planes.add(p);
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent e){
        int action=e.getAction()&MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY=e.getY();
                lastX=e.getX();
                isDown=true;
                break;
            case MotionEvent.ACTION_MOVE:
            new moveTask().execute(new Float(e.getX()),new Float(e.getY()));
                break;
            case MotionEvent.ACTION_UP:
                isDown=false;
                break;
        }
        return true;
    }
    class moveTask extends AsyncTask<Float,Void,Void>{

        @Override
        protected Void doInBackground(Float... params) {
            float a1=params[0]-lastX;
            float a2=params[1]-lastY;
            x+=a2/5;x%=360;
            y+=a1/5;y%=360;
            lastX=params[0];
            lastY=params[1];
            Camera.changeCameraRotationByEuler(y,x);
            return null;
        }
    }
    public void initWorld(){
        CollisionConfiguration collisionConfiguration=new DefaultCollisionConfiguration();
        CollisionDispatcher dispatcher=new CollisionDispatcher(collisionConfiguration);
        Vector3f worldMin=new Vector3f(-300,-300,-300);
        Vector3f worldMax=new Vector3f(300,300,300);
        int maxProxies=1024;
        AxisSweep3 overlappingPairCache=new AxisSweep3(worldMin,worldMax,maxProxies);
        SequentialImpulseConstraintSolver solver=new SequentialImpulseConstraintSolver();
         this.dynamicsWorld=new DiscreteDynamicsWorld(dispatcher,overlappingPairCache,solver,collisionConfiguration);
         dynamicsWorld.setGravity(new Vector3f(0,-9.8f,0));
         boxShape=new BoxShape(new Vector3f(Constant.UNIT_SIZE,Constant.UNIT_SIZE,Constant.UNIT_SIZE));
         planeShape=new StaticPlaneShape(new Vector3f(0,1,0),0);
         boxShape.setLocalScaling(new Vector3f(3,1,6));
        sphereShape=new SphereShape(1);
    }
    class testTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            testbody.applyCentralForce(new Vector3f(0,1000,0));
            return null;
        }
    }
    class downTask extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] params) {
            return null;
        }
    }
}
