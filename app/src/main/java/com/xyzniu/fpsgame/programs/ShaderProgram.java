package com.xyzniu.fpsgame.programs;

import android.content.Context;
import com.xyzniu.fpsgame.util.ShaderHelper;
import com.xyzniu.fpsgame.util.TextResourceReader;

import static android.opengl.GLES20.glUseProgram;

public class ShaderProgram {
    
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
    protected static final String U_LIGHT_COLOR = "u_LightColor";
    protected static final String U_LIGHT_POSITION = "u_LightPosition";
    protected static final String U_VIEW_POSITION = "u_ViewPosition";
    protected static final String U_MODEL = "u_Model";
    protected static final String U_IT_MODEL = "u_IT_Model";
    protected static final String U_MODEL_VIEW_PROJECTION = "u_ModelViewProjection";
    
    protected static final String A_POSITION = "a_Position";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";
    protected static final String A_NORMAL = "a_Normal";
    
    protected final int program;
    
    protected ShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        program = ShaderHelper.buildProgram(
                TextResourceReader.readTextFileFromResource(
                        context, vertexShaderResourceId),
                TextResourceReader.readTextFileFromResource(
                        context, fragmentShaderResourceId));
    }
    
    public void useProgram() {
        glUseProgram(program);
    }
    
}