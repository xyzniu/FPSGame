package com.xyzniu.fpsgame.programs;

import android.content.Context;
import com.xyzniu.fpsgame.R;

import static android.opengl.GLES20.*;

public class EndPointShaderProgram extends ShaderProgram {
    
    private final int uModelViewProjectionLocation;
    private final int uLightColorLocation;
    private final int aPositionLocation;
    
    
    protected EndPointShaderProgram(Context context) {
        super(context, R.raw.endpoint_vertex_shader, R.raw.endpoint_fragment_shader);
        
        uModelViewProjectionLocation = glGetUniformLocation(program, U_MODEL_VIEW_PROJECTION);
        uLightColorLocation = glGetUniformLocation(program, U_LIGHT_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
    }
    
    public void setUniforms(float[] modelViewProjection, float[] color) {
        glUniformMatrix4fv(uModelViewProjectionLocation, 1, false, modelViewProjection, 0);
        glUniform3fv(uLightColorLocation, 1, color, 0);
    }
    
    public int getPositionLocation() {
        return aPositionLocation;
    }
    
}
