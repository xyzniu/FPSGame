package com.xyzniu.fpsgame.renderer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.util.Log;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.activity.MainActivity;
import com.xyzniu.fpsgame.objects.*;
import com.xyzniu.fpsgame.pojo.Camera;
import com.xyzniu.fpsgame.pojo.Geometry;
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
    private EnemyManager enemyManager;
    private Camera camera = Camera.getCamera();
    private List<Bullet> bullets = BulletBag.getBullets();
    
    public Renderer(Context context) {
        this.context = context;
    }
    
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        new ShaderProgramManager(context);
        new TextureManager(context);
        try {
            ground = new Ground(context, R.raw.map1);
        } catch (Exception e) {
            Log.w("Ground", "There is something wrong with the map");
        }
        camera.setGround(ground);
        enemyManager = new EnemyManager(context, ground.getMobSpawner());
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
        // drawBullets();
        ground.drawGround();
        // enemyManager.draw();
        if (camera.atEndPoint()) {
            Intent home = new Intent();
            home.setClass(this.context, MainActivity.class);
            context.startActivity(home);
            ((Activity) context).finish();
        }
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
    
}
