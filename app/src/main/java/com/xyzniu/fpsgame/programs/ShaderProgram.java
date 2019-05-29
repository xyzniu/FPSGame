package com.xyzniu.fpsgame.programs;
import android.content.Context;
import com.xyzniu.fpsgame.util.ShaderHelper;
import com.xyzniu.fpsgame.util.TextResourceReader;

import static android.opengl.GLES20.glUseProgram;

public class ShaderProgram {
    
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
    protected static final String U_COLOR = "u_Color";
    protected static final String U_TIME = "u_Time";
    protected static final String U_VECTOR_TO_LIGHT = "u_VectorToLight";
    protected static final String U_MV_MATRIX = "u_MVMatrix";
    protected static final String U_IT_MV_MATRIX = "u_IT_MVMatrix";
    protected static final String U_MVP_MATRIX = "u_MVPMatrix";
    protected static final String U_POINT_LIGHT_POSITIONS = "u_PointLightPositions";
    protected static final String U_POINT_LIGHT_POSITION = "u_PointLightPosition";
    protected static final String U_POINT_LIGHT_COLORS = "u_PointLightColors";
    protected static final String U_POINT_LIGHT_COLOR = "u_PointLightColor";
    
    
    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";
    protected static final String A_DIRECTION_VECTOR = "a_DirectionVector";
    protected static final String A_PARTICLE_START_TIME = "a_ParticleStartTime";
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