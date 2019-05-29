package com.xyzniu.fpsgame.renderer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.objects.Object;
import com.xyzniu.fpsgame.pojo.Camera;
import com.xyzniu.fpsgame.programs.ObjectShaderProgram;
import com.xyzniu.fpsgame.util.MatrixHelper;
import com.xyzniu.fpsgame.util.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.Matrix.*;

public class Renderer implements GLSurfaceView.Renderer {
    
    private final Context context;
    private int r = 0;
    private final float[] modelMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] tempMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];
    private final float[] modelViewMatrix = new float[16];
    private final float[] it_modelViewMatrix = new float[16];
    
    final float[] vectorToLight = {0f, 2f, 0f, 0f};
    private final float[] pointLightPosition = new float[]{0f, 2f, 0f, 1f};
    private final float[] pointLightColor = new float[]{1.0f, 1.0f, 1.0f};
    private Object object;
    private ObjectShaderProgram program;
    private int texture;
    private Camera camera = Camera.getCamera();
    
    public Renderer(Context context) {
        this.context = context;
    }
    
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        
        object = new Object(context);
        program = new ObjectShaderProgram(context);
        texture = TextureHelper.loadTexture(context, R.raw.caruv);
    }
    
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width / (float) height, 1f, 10f);
        // updateViewMatrices();
    }
    
    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        camera.updateCamera();
        drawObject();
    }
    
    private void updateMvpMatrix() {
        multiplyMM(modelViewMatrix, 0, camera.getViewMatrix(), 0, modelMatrix, 0);
        // used to get the right normals
        invertM(tempMatrix, 0, modelViewMatrix, 0);
        transposeM(it_modelViewMatrix, 0, tempMatrix, 0);
        multiplyMM(modelViewProjectionMatrix, 0, projectionMatrix, 0, modelViewMatrix, 0);
    }
    
    private void drawObject() {
        program.useProgram();
        object.bindData(program);
        
        setIdentityM(modelMatrix, 0);
        rotateM(modelMatrix, 0, r++, 1f, 0f, 0f);
        scaleM(modelMatrix, 0, 0.05f, 0.05f, 0.05f);
        updateMvpMatrix();
        
        float[] vectorToLightInEyeSpace = new float[4];
        float[] pointPositionsInEyeSpcae = new float[4];
        multiplyMV(vectorToLightInEyeSpace, 0, camera.getViewMatrix(), 0, vectorToLight, 0);
        multiplyMV(pointPositionsInEyeSpcae, 0, camera.getViewMatrix(), 0, pointLightPosition, 0);
        
        program.setUniforms(modelViewMatrix,
                it_modelViewMatrix,
                modelViewProjectionMatrix,
                vectorToLightInEyeSpace,
                pointPositionsInEyeSpcae,
                pointLightColor,
                texture);
        object.draw();
    }
}
