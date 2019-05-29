package com.xyzniu.fpsgame.programs;

import android.content.Context;
import com.xyzniu.fpsgame.R;

import static android.opengl.GLES20.*;
import static android.opengl.GLES20.glUniform1i;

public class ObjectShaderProgram extends ShaderProgram {
    
    
    private final int uMVMatrixLocation;
    private final int uIT_MVMatrixLocation;
    private final int uMVPMatrixLocation;
    
    private final int uVectorToLightLocation;
    private final int uPointLightPositionLocation;
    private final int uPointLightColorLocation;
    
    private final int uTextureUnitLocation;
    
    private final int aPositionLocation;
    private final int aNormalLocation;
    private final int aTextureCoordinatesLocation;
    
    public ObjectShaderProgram(Context context) {
        super(context, R.raw.object_vertex_shader, R.raw.object_fragment_shader);
        
        
        uMVMatrixLocation = glGetUniformLocation(program, U_MV_MATRIX);
        uIT_MVMatrixLocation = glGetUniformLocation(program, U_IT_MV_MATRIX);
        uMVPMatrixLocation = glGetUniformLocation(program, U_MVP_MATRIX);
        
        uPointLightPositionLocation = glGetUniformLocation(program, U_POINT_LIGHT_POSITION);
        uPointLightColorLocation = glGetUniformLocation(program, U_POINT_LIGHT_COLOR);
        uVectorToLightLocation = glGetUniformLocation(program, U_VECTOR_TO_LIGHT);
        
        uTextureUnitLocation = glGetUniformLocation(program, U_TEXTURE_UNIT);
        
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aNormalLocation = glGetAttribLocation(program, A_NORMAL);
        aTextureCoordinatesLocation = glGetAttribLocation(program, A_TEXTURE_COORDINATES);
        
    }
    
    public void setUniforms(float[] mvMatrix,
                            float[] it_mvMatrix,
                            float[] mvpMatrix,
                            float[] vectorToDirectionalLight,
                            float[] pointLightPosition,
                            float[] pointLightColor,
                            int textureId) {
        glUniformMatrix4fv(uMVMatrixLocation, 1, false, mvMatrix, 0);
        glUniformMatrix4fv(uIT_MVMatrixLocation, 1, false, it_mvMatrix, 0);
        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, mvpMatrix, 0);
        
        glUniform3fv(uVectorToLightLocation, 1, vectorToDirectionalLight, 0);
        glUniform4fv(uPointLightPositionLocation, 1, pointLightPosition, 0);
        glUniform3fv(uPointLightColorLocation, 1, pointLightColor, 0);
        
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureId);
        glUniform1i(uTextureUnitLocation, 0);
    }
    
    
    public int getPositionLocation() {
        return aPositionLocation;
    }
    
    public int getTextureCoordinatesLocation() {
        return aTextureCoordinatesLocation;
    }
    
    public int getNormalLocation() {
        return aNormalLocation;
    }
}
