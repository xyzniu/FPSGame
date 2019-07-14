package com.xyzniu.fpsgame.objects;

import com.xyzniu.fpsgame.util.MatrixHelper;

import static android.opengl.Matrix.*;
import static android.opengl.Matrix.multiplyMM;

public class Matrix {
    
    public static final float[] projectionMatrix = new float[16];
    
    public final float[] modelMatrix = new float[16];
    public final float[] it_modelMatrix = new float[16];
    public final float[] modelViewMatrix = new float[16];
    public final float[] modelViewProjectionMatrix = new float[16];
    public final float[] tempMatrix = new float[16];
    
    public static void perspective(int width, int height) {
        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width / (float) height, 0.05f, 50f);
    }
    
    public void updateMatrix() {
        // it_modelMtrix
        invertM(tempMatrix, 0, modelMatrix, 0);
        transposeM(it_modelMatrix, 0, tempMatrix, 0);
        
        // ModelViewProjectionMatrix
        multiplyMM(modelViewMatrix, 0, PlayerManager.getViewMatrix(), 0, modelMatrix, 0);
        multiplyMM(modelViewProjectionMatrix, 0, projectionMatrix, 0, modelViewMatrix, 0);
    }
    
}
