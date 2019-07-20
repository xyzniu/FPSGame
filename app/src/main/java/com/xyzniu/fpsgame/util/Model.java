package com.xyzniu.fpsgame.util;

import android.content.Context;
import com.xyzniu.fpsgame.data.VertexArray;
import com.xyzniu.fpsgame.programs.MainShaderProgram;
import com.xyzniu.fpsgame.util.ObjectLoader;

import static android.opengl.GLES20.*;

public class Model {
    
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int TEXTURE_COMPONENT_COUNT = 2;
    private static final int NORMAL_COMPONENT_COUNT = 3;
    private static final int TOTAL_COMPONENT_COUNT =
            POSITION_COMPONENT_COUNT
                    + TEXTURE_COMPONENT_COUNT
                    + NORMAL_COMPONENT_COUNT;
    
    private int numFaces;
    private VertexArray positions;
    private VertexArray normals;
    private VertexArray textureCoordinates;
    
    public Model(Context context, int file) {
        ObjectLoader objectLoader = new ObjectLoader(context, file);
        
        numFaces = objectLoader.numFaces;
        
        // Initialize the buffers.
        positions = new VertexArray(objectLoader.positions);
        normals = new VertexArray(objectLoader.normals);
        textureCoordinates = new VertexArray(objectLoader.textureCoordinates);
    }
    
    public void bindData(MainShaderProgram program) {
        positions.setVertexAttribPointer(0, program.getPositionLocation(), POSITION_COMPONENT_COUNT, 0);
        normals.setVertexAttribPointer(0, program.getNormalLocation(), NORMAL_COMPONENT_COUNT, 0);
        textureCoordinates.setVertexAttribPointer(0, program.getTextureCoordinatesLocation(), TEXTURE_COMPONENT_COUNT, 0);
    }
    
    public void draw() {
        glDrawArrays(GL_TRIANGLES, 0, numFaces);
    }
}