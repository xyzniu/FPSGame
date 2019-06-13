package com.xyzniu.fpsgame.objects;

import android.content.Context;
import com.xyzniu.fpsgame.data.VertexArray;
import com.xyzniu.fpsgame.data.VertexData;
import com.xyzniu.fpsgame.pojo.Geometry;
import com.xyzniu.fpsgame.programs.MainShaderProgram;
import com.xyzniu.fpsgame.util.Constants;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;

public class Grass {
    
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
    private Geometry.Vector position = new Geometry.Vector(0, 0, 0);
    
    public Grass(Context context) {
        vertexArray = new VertexArray(VertexData.squareVertexData);
        numFaces = 6;
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
    
    public void draw() {
        glDrawArrays(GL_TRIANGLES, 0, numFaces);
    }
    
    public Geometry.Vector getPosition() {
        return position;
    }
    
}
