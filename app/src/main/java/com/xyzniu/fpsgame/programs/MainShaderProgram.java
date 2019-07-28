package com.xyzniu.fpsgame.programs;

import android.content.Context;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.manager.PlayerManager;

import static android.opengl.GLES20.*;

public class MainShaderProgram extends ShaderProgram {
    
    private final int uTextureUnitLocation;
    private final int uLightColorLocation;
    private final int uLightPositionLocation;
    private final int uViewPositionLocation;
    private final int uModelLocation;
    private final int uITModelLocation;
    private final int uModelViewProjectionLocation;
    
    private final int aPositionLocation;
    private final int aNormalLocation;
    private final int aTextureCoordinatesLocation;
    
    public MainShaderProgram(Context context) {
        super(context, R.raw.main_vertex_shader, R.raw.main_fragment_shader);
        
        uTextureUnitLocation = glGetUniformLocation(program, U_TEXTURE_UNIT);
        
        uLightColorLocation = glGetUniformLocation(program, U_LIGHT_COLOR);
        uLightPositionLocation = glGetUniformLocation(program, U_LIGHT_POSITION);
        
        uModelLocation = glGetUniformLocation(program, U_MODEL);
        uITModelLocation = glGetUniformLocation(program, U_IT_MODEL);
        uModelViewProjectionLocation = glGetUniformLocation(program, U_MODEL_VIEW_PROJECTION);
        uViewPositionLocation = glGetUniformLocation(program, U_VIEW_POSITION);
        
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aNormalLocation = glGetAttribLocation(program, A_NORMAL);
        aTextureCoordinatesLocation = glGetAttribLocation(program, A_TEXTURE_COORDINATES);
    }
    
    public void setUniforms(float[] model,
                            float[] it_model,
                            float[] modelViewProjection,
                            int textureId) {
        setUniforms(model, it_model, modelViewProjection, PlayerManager.getLightColor(), textureId);
    }
    
    public void setUniforms(float[] model,
                            float[] it_model,
                            float[] modelViewProjection,
                            float[] lightColor,
                            int textureId) {
        glUniformMatrix4fv(uModelLocation, 1, false, model, 0);
        glUniformMatrix4fv(uITModelLocation, 1, false, it_model, 0);
        glUniformMatrix4fv(uModelViewProjectionLocation, 1, false, modelViewProjection, 0);
        
        glUniform3fv(uLightPositionLocation, 1, PlayerManager.getPositionVec3(), 0);
        glUniform3fv(uLightColorLocation, 1, lightColor, 0);
        glUniform3fv(uViewPositionLocation, 1, PlayerManager.getPositionVec3(), 0);
        
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureId);
        glUniform1i(uTextureUnitLocation, 0);
    }
    
    public int getPositionLocation() {
        return aPositionLocation;
    }
    
    public int getNormalLocation() {
        return aNormalLocation;
    }
    
    public int getTextureCoordinatesLocation() {
        return aTextureCoordinatesLocation;
    }
}
