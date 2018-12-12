package com.xinlan.picbook.engine.shader;

import android.content.Context;

import com.xinlan.picbook.R;

import static android.opengl.GLES30.glGetAttribLocation;

public class ColorShader extends ShaderProgram{
    public static final String A_POSITION = "a_position";
    public static final String A_COLOR = "a_color";

    private final int aPositionLocation;
    private final int aColorLocation;

    protected ColorShader(Context context) {
        super(context, R.raw.triangle_vertex, R.raw.triangle_fragment);

        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aColorLocation = glGetAttribLocation(program, A_COLOR);
    }


}
