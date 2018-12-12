package com.xinlan.picbook.engine;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLES32;
import android.opengl.GLSurfaceView;

import com.xinlan.picbook.engine.module.Elem;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES30.glClear;

/**
 * PicBookEngine
 */
public class PicBookEngine implements GLSurfaceView.Renderer {
    public static final boolean DEBUG = true;
    protected GLSurfaceView mGLSurfaceView;

    public List<Elem> elems = new ArrayList<Elem>();

    public PicBookEngine(GLSurfaceView glView) {
        try {
            init(glView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init(GLSurfaceView glView) throws Exception {
        if (glView == null)
            throw new Exception("GLSurfaceView can not be null!");

        this.mGLSurfaceView = glView;
        mGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        mGLSurfaceView.setEGLConfigChooser(LoggerConfig.ON);
        mGLSurfaceView.setEGLContextClientVersion(3);//opengl es 3.0
    }

    public void startRender(){
        if(mGLSurfaceView == null)
            return;

        mGLSurfaceView.setRenderer(this);
        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public void onResume(){
        if(mGLSurfaceView != null){
            mGLSurfaceView.onResume();
        }
    }

    public void onPause(){
        if(mGLSurfaceView != null){
            mGLSurfaceView.onPause();
        }
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        for (Elem e : elems) {
            e.init();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        for (Elem e : elems) {
            e.render();
        }
    }
}//end class
