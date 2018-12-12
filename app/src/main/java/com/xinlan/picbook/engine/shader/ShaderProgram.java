package com.xinlan.picbook.engine.shader;


import android.content.Context;
import com.xinlan.picbook.engine.utils.ShaderHelper;
import static android.opengl.GLES30.glUseProgram;

public class ShaderProgram {

    // Shader program
    public final int program;

    protected ShaderProgram(Context context, int vertexShaderResourceId,
                            int fragmentShaderResourceId) {
        // 编译并链接shader
        program = ShaderHelper.buildProgram(
                ShaderHelper.readTextFileFromResource(
                        context, vertexShaderResourceId),
                ShaderHelper.readTextFileFromResource(
                        context, fragmentShaderResourceId));
    }

    /**
     * 设置使用shader
     *
     */
    public void useProgram() {
        glUseProgram(program);
    }
}
