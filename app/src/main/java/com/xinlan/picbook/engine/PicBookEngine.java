package com.xinlan.picbook.engine;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.xinlan.picbook.engine.primitive.Elem;
import com.xinlan.picbook.engine.primitive.Triangle;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES30.glClear;

/**
 * PicBookEngine
 */
public class PicBookEngine implements GLSurfaceView.Renderer {
    public static final boolean DEBUG = true;
    protected GLSurfaceView mGLSurfaceView;
    protected Context mContext;

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
        this.mContext = mGLSurfaceView.getContext();

        mGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        mGLSurfaceView.setEGLConfigChooser(LoggerConfig.ON);
        mGLSurfaceView.setEGLContextClientVersion(3);//opengl es 3.0

    }

    public void startRender() {
        if (mGLSurfaceView == null)
            return;

        mGLSurfaceView.setRenderer(this);
        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public void onResume() {
        if (mGLSurfaceView != null) {
            mGLSurfaceView.onResume();
        }
    }

    public void onPause() {
        if (mGLSurfaceView != null) {
            mGLSurfaceView.onPause();
        }
    }

    Triangle tri1 = null;
    Triangle tri2 = null;
    long frame;
    float pos;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        tri1 = new Triangle(mContext);
        addEle(tri1);
        tri1.setPos(0f, 0f, 0.5f, 0f, 1f, 1f);
        tri1.setColor(1, 0, 0, 1);

        tri2 = new Triangle(mContext);
        addEle(tri2);
        tri2.setPos(0f, 0f, -0.5f, 0f, -1f, -1f);
        tri2.setColor(0, 1f, 1f, 1);


        for (Elem e : elems) {
            e.init(mContext);
        }

    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        tri1.setPos(0f, 0f, 0.5f, 0f, pos, 1f);
        pos -= 0.01f;
        if (pos < -1) {
            pos = 1;
        }

        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        for (Elem e : elems) {
            e.render();
        }

        //mGLSurfaceView.requestRender();
    }

    public void addEle(Elem e) {
        if (e == null)
            return;

        elems.add(e);
    }
}//end class
