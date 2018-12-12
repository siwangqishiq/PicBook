package com.xinlan.picbook.engine.primitive;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;

import com.xinlan.picbook.engine.shader.TriangleShader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_FLOAT;

public class Triangle implements Elem {
    private float x1, y1;
    private float x2, y2;
    private float x3, y3;

    private float colorR, colorG, colorB, colorA;

    protected float vertex[] = {-0.5f, -0.5f,
            0, 0,
            1f, 1f};
    protected FloatBuffer mPosBuf;

    protected float colors[] = {0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
    };
    protected FloatBuffer mColorsBuf;
    protected TriangleShader mTriangleProgram;

    private float[] mColors = {0, 0, 0, 0};//rgba

    public Triangle(Context conext) {

        mPosBuf = ByteBuffer.allocateDirect(4 * vertex.length)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertex);


        mColorsBuf = ByteBuffer.allocateDirect(4 * colors.length)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertex);

        mTriangleProgram = new TriangleShader(conext);
    }


    @Override
    public void init(Context context) {
    }

    public void setPos(float x1, float y1,
                       float x2, float y2,
                       float x3, float y3) {

        mPosBuf.position(0);

        mPosBuf.put(x1);
        mPosBuf.put(y1);

        mPosBuf.put(x2);
        mPosBuf.put(y2);

        mPosBuf.put(x3);
        mPosBuf.put(y3);

        mPosBuf.position(0);
    }

    public void setColor(float r, float g, float b, float a) {
        mColors[0] = r;
        mColors[1] = g;
        mColors[2] = b;
        mColors[3] = a;
        mColorsBuf.position(0);

        mColorsBuf.put(mColors[0]);
        mColorsBuf.put(mColors[1]);
        mColorsBuf.put(mColors[2]);
        mColorsBuf.put(mColors[3]);

        mColorsBuf.put(mColors[0]);
        mColorsBuf.put(mColors[1]);
        mColorsBuf.put(mColors[2]);
        mColorsBuf.put(mColors[3]);

        mColorsBuf.put(mColors[0]);
        mColorsBuf.put(mColors[1]);
        mColorsBuf.put(mColors[2]);
        mColorsBuf.put(mColors[3]);

        mColorsBuf.position(0);
    }

    @Override
    public void render() {
        mTriangleProgram.useProgram();
        //set pos
        GLES20.glVertexAttribPointer(mTriangleProgram.getPositionIndex(),2, GL_FLOAT,false,
                0 ,mPosBuf);
        GLES20.glEnableVertexAttribArray(mTriangleProgram.getPositionIndex());

        //set color
        GLES20.glVertexAttribPointer(mTriangleProgram.getColorIndex(), 4, GL_FLOAT,false, 0, mColorsBuf);
        GLES20.glEnableVertexAttribArray(mTriangleProgram.getColorIndex());

        GLES30.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
    }
}
