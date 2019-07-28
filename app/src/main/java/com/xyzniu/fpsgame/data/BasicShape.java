package com.xyzniu.fpsgame.data;

import com.xyzniu.fpsgame.programs.EndPointShaderProgram;
import com.xyzniu.fpsgame.programs.MainShaderProgram;
import com.xyzniu.fpsgame.programs.ShaderProgram;
import com.xyzniu.fpsgame.config.Constants;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;

public class BasicShape {
    
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int TEXTURE_COMPONENT_COUNT = 2;
    private static final int NORMAL_COMPONENT_COUNT = 3;
    private static final int TOTAL_COMPONENT_COUNT =
            POSITION_COMPONENT_COUNT
                    + TEXTURE_COMPONENT_COUNT
                    + NORMAL_COMPONENT_COUNT;
    private static final int STRIDE = TOTAL_COMPONENT_COUNT * Constants.BYTES_PER_FLOAT;
    
    private int numFaces;
    private VertexArray vertexArray;
    
    public BasicShape(VertexArray vertexArray, int numFaces) {
        this.vertexArray = vertexArray;
        this.numFaces = numFaces;
    }
    
    public void bindData(MainShaderProgram program) {
        vertexArray.setVertexAttribPointer(
                0,
                program.getPositionLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);
        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                program.getTextureCoordinatesLocation(),
                TEXTURE_COMPONENT_COUNT,
                STRIDE);
        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT + TEXTURE_COMPONENT_COUNT,
                program.getNormalLocation(),
                NORMAL_COMPONENT_COUNT,
                STRIDE);
    }
    
    public void bindData(EndPointShaderProgram program) {
        vertexArray.setVertexAttribPointer(
                0,
                program.getPositionLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);
    }
    
    public void draw() {
        glDrawArrays(GL_TRIANGLES, 0, numFaces);
    }
    
    
}
