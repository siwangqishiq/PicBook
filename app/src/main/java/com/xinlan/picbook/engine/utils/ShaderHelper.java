package com.xinlan.picbook.engine.utils;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.util.Log;

import com.xinlan.picbook.engine.LoggerConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ShaderHelper {
    private static final String TAG = "ShaderHelper";

    /**
     * Loads and compiles a vertex shader, returning the OpenGL object ID.
     */
    public static int compileVertexShader(String shaderCode) {
        return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode);
    }

    /**
     * Loads and compiles a fragment shader, returning the OpenGL object ID.
     */
    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int compileShader(int type, String shaderCode) {
        // Create a new shader object.
        final int shaderObjectId = GLES20.glCreateShader(type);

        if (shaderObjectId == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not create new shader.");
            }

            return 0;
        }

        // Pass in the shader source.
        GLES30.glShaderSource(shaderObjectId, shaderCode);

        // Compile the shader.
        GLES30.glCompileShader(shaderObjectId);

        // Get the compilation status.
        final int[] compileStatus = new int[1];
        GLES30.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

        if (LoggerConfig.ON) {
            // Print the shader info log to the Android log output.
//			Log.w(TAG, "Results of compiling source:" + "\n" + shaderCode + "\n:"
//					+ glGetShaderInfoLog(shaderObjectId));
            Log.w(TAG, "Results of compiling source:" + GLES20.glGetShaderInfoLog(shaderObjectId));
        }
        // Verify the compile status.
        if (compileStatus[0] == 0) {
            // If it failed, delete the shader object.
            GLES20.glDeleteShader(shaderObjectId);

            if (LoggerConfig.ON) {
                Log.w(TAG, "Compilation of shader failed.");
            }

            return 0;
        }

        // Return the shader object ID.
        return shaderObjectId;
    }

    /**
     * Links a vertex shader and a fragment shader together into an OpenGL
     * program. Returns the OpenGL program object ID, or 0 if linking failed.
     */
    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        // Create a new program object.
        final int programObjectId = GLES20.glCreateProgram();

        if (programObjectId == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not create new program");
            }

            return 0;
        }
        // Attach the vertex shader to the program.
        GLES30.glAttachShader(programObjectId, vertexShaderId);
        // Attach the fragment shader to the program.
        GLES30.glAttachShader(programObjectId, fragmentShaderId);

        // Link the two shaders together into a program.
        GLES30.glLinkProgram(programObjectId);

        // Get the link status.
        final int[] linkStatus = new int[1];
        GLES30.glGetProgramiv(programObjectId, GLES30.GL_LINK_STATUS, linkStatus, 0);

        if (LoggerConfig.ON) {
            // Print the program info log to the Android log output.
            Log.v(TAG, "Results of linking program:\n"
                    + GLES30.glGetProgramInfoLog(programObjectId));
        }

        // Verify the link status.
        if (linkStatus[0] == 0) {
            // If it failed, delete the program object.
            GLES30.glDeleteProgram(programObjectId);
            if (LoggerConfig.ON) {
                Log.w(TAG, "Linking of program failed.");
            }
            return 0;
        }


        // Return the program object ID.
        return programObjectId;

    }


    /**
     * Validates an OpenGL program. Should only be called when developing the
     * application.
     */
    public static boolean validateProgram(int programObjectId) {
        GLES30.glValidateProgram(programObjectId);

        final int[] validateStatus = new int[1];
        GLES30.glGetProgramiv(programObjectId, GLES20.GL_VALIDATE_STATUS, validateStatus, 0);
        Log.v(TAG, "Results of validating program: " + validateStatus[0]
                + "\nLog:" + GLES30.glGetProgramInfoLog(programObjectId));

        return validateStatus[0] != 0;
    }

    public static int buildProgram(String vertexShaderSource, String fragmentShaderSource) {
        int program;
        // Compile the shaders.
        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);
        // Link them into a shader program.
        program = linkProgram(vertexShader, fragmentShader);
        if (LoggerConfig.ON) {
            validateProgram(program);
        }
        return program;
    }

    /**
     * 从资源文件中读出文本文件
     *
     * @param context
     * @param resourceId
     * @return
     */
    public static String readTextFileFromResource(Context context,
                                                  int resourceId) {
        StringBuilder body = new StringBuilder();

        try {
            InputStream inputStream =
                    context.getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String nextLine;

            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not open resource: " + resourceId, e);
        } catch (Resources.NotFoundException nfe) {
            throw new RuntimeException("Resource not found: " + resourceId, nfe);
        }

        return body.toString();
    }

}
