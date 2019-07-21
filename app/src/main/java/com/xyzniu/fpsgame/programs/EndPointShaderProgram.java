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
    
    public void setUniforms(float[] modelViewProjection, boolean hasCollectedAll) {
        glUniformMatrix4fv(uModelViewProjectionLocation, 1, false, modelViewProjection, 0);
        if (hasCollectedAll) {
            glUniform3fv(uLightColorLocation, 1, new float[]{0, 1, 0}, 0);
        } else {
            glUniform3fv(uLightColorLocation, 1, new float[]{1, 0, 0}, 0);
        }
    }
    
    public int getPositionLocation() {
        return aPositionLocation;
    }
    
}
