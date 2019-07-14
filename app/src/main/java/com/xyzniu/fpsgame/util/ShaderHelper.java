package com.xyzniu.fpsgame.util;

import android.util.Log;

import static android.opengl.GLES20.*;
import static com.xyzniu.fpsgame.config.Configuration.LOG_ON;

public class ShaderHelper {
    
    private static final String TAG = "ShaderHelper";
    
    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }
    
    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }
    
    private static int compileShader(int type, String shaderCode) {
        // create a new shader object and check if the creation was successful
        final int shaderObjectId = glCreateShader(type);
        if (shaderObjectId == 0) {
            if (LOG_ON) {
                Log.w(TAG, "Could not create new shader.");
            }
            return 0;
        }
        
        // upload shader source code into the shader object
        glShaderSource(shaderObjectId, shaderCode);
        // compile the shader
        glCompileShader(shaderObjectId);
        // check if success
        final int[] compileStatus = new int[1];
        // this tells OPENGL to read the compile status associated with shaderObjectId
        // and write it to the 0th element of compile status
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);
        if (LOG_ON) {
            Log.v(TAG, "Results of compiling source: " + "\n" + shaderCode + "\n:" + glGetShaderInfoLog(shaderObjectId));
        }
        
        if (compileStatus[0] == 0) {
            glDeleteShader(shaderObjectId);
            if (LOG_ON) {
                Log.w(TAG, "Compilation of shader failed.");
            }
            return 0;
        }
        
        return shaderObjectId;
    }
    
    
    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        final int programObjectId = glCreateProgram();
        if (programObjectId == 0) {
            if (LOG_ON) {
                Log.w(TAG, "Could not create new program");
            }
            return 0;
        }
        
        // link
        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);
        glLinkProgram(programObjectId);
        
        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);
        if (LOG_ON) {
            Log.v(TAG, "Results of linking program:\n"
                    + glGetProgramInfoLog(programObjectId));
        }
        
        if (linkStatus[0] == 0) {
            glDeleteProgram(programObjectId);
            if (LOG_ON) {
                Log.w(TAG, "Linking of program failed.");
            }
            return 0;
        }
        return programObjectId;
    }
    
    public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);
        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
        Log.v(TAG, "Results of validating program: " + validateStatus[0] + "\nLog: " + glGetProgramInfoLog(programObjectId));
        return validateStatus[0] != 0;
    }
    
    public static int buildProgram(String vertexShaderSource, String fragmentShaderSource) {
        int program;
        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);
        program = linkProgram(vertexShader, fragmentShader);
        if (LOG_ON) {
            validateProgram(program);
        }
        return program;
    }
}