package com.xyzniu.fpsgame.renderer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.objects.*;
import com.xyzniu.fpsgame.pojo.Camera;
import com.xyzniu.fpsgame.pojo.Geometry;
import com.xyzniu.fpsgame.programs.MainShaderProgram;
import com.xyzniu.fpsgame.programs.ShaderProgramManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import java.util.Iterator;
import java.util.List;

import static android.opengl.GLES20.*;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;

public class Renderer implements GLSurfaceView.Renderer {
    
    private final Context context;
    private Ground ground;
    private Camera camera = Camera.getCamera();
    private List<Bullet> bullets = BulletBag.getBullets();
    
    public Renderer(Context context) {
        this.context = context;
    }
    
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        
        ShaderProgramManager.init(context);
        
        ground = new Ground(context, R.raw.map1);
    }
    
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        Matrix.perspective(width, height);
    }
    
    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        camera.updateCamera();
        
        drawBullets();
        ground.drawGround();
    }
    
    
    private void drawBullets() {
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = it.next();
            bullet.update();
            Log.w("bulletPosition", String.valueOf(bullet.getPosition()));
            bullet.collisionDetect(new Geometry.Vector(0, 0, 0), 1f);
            if (bullet.isHit()) {
                Log.w("bulletHit", "true");
                bullet.setValid(false);
            }
            if (!bullet.isValid()) {
                it.remove();
            }
        }
    }
    
    /*private void drawObject() {
        program.useProgram();
        object.bindData(program);
        
        setIdentityM(Matrix.modelMatrix, 0);
        MatrixHelper.translateMatrix(Matrix.modelMatrix, 0, object.getPosition());
        rotateM(Matrix.modelMatrix, 0, r++, 1f, 0f, 0f);
        scaleM(Matrix.modelMatrix, 0, 0.05f, 0.05f, 0.05f);
        Matrix.updateMatrix();
        
        program.setUniforms(Matrix.modelMatrix,
                Matrix.it_modelMatrix,
                Matrix.modelViewProjectionMatrix,
                light.getLightPosition(),
                light.getLightColor(),
                camera.getPositionVec3(),
                carTexture);
        
        object.draw();
    }*/
}
