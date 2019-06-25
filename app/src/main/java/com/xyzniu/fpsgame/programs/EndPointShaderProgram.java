package com.xyzniu.fpsgame.programs;

import android.content.Context;
import com.xyzniu.fpsgame.R;

import static android.opengl.GLES20.*;

public class EndPointShaderProgram extends ShaderProgram {
    
    private final int uModelViewProjectionLocation;
    private final int aPositionLocation;
    
    protected EndPointShaderProgram(Context context) {
        super(context, R.raw.endpoint_vertex_shader, R.raw.endpoint_fragment_shader);
        
        uModelViewProjectionLocation = glGetUniformLocation(program, U_MODEL_VIEW_PROJECTION);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
    }
    
    public void setUniforms(float[] modelViewProjection) {
        glUniformMatrix4fv(uModelViewProjectionLocation, 1, false, modelViewProjection, 0);
    }
    
    public int getPositionLocation() {
        return aPositionLocation;
    }
    
}
