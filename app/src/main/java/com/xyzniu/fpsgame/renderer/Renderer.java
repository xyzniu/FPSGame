package com.xyzniu.fpsgame.renderer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.util.Log;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.activity.MainActivity;
import com.xyzniu.fpsgame.objects.*;
import com.xyzniu.fpsgame.objects.Camera;
import com.xyzniu.fpsgame.programs.ShaderProgramManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;

public class Renderer implements GLSurfaceView.Renderer {
    
    private final Context context;
    private Ground ground;
    private EnemyManager enemyManager;
    private BulletBag bulletBag;
    private Camera camera = Camera.getCamera();
    public volatile static boolean renderSet = false;
    
    private long time;
    public static float delta;
    public static float elapsedtime;
    
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
        ground = new Ground(context, R.raw.map1);
        camera.setGround(ground);
        enemyManager = new EnemyManager(context, ground.getMobSpawner());
        bulletBag = new BulletBag(context);
        renderSet = true;
        time = SystemClock.elapsedRealtime();
        elapsedtime = 0;
    }
    
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        Matrix.perspective(width, height);
    }
    
    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        long elapsed = SystemClock.elapsedRealtime() - time;
        time = SystemClock.elapsedRealtime();
        delta = elapsed / 1000f;
        elapsedtime += elapsed;
        
        ground.drawGround();
        bulletBag.draw();
        enemyManager.draw();
        
        
        if (elapsedtime > 20) {
            elapsedtime = 0;
            camera.updateCamera();
            bulletBag.updateBullets();
            enemyManager.updateEnemies();
            HitDetection.hitDetection(bulletBag.getBullets(), enemyManager.getEnemies());
        }
        
        if (camera.atEndPoint()) {
            Intent home = new Intent();
            home.setClass(this.context, MainActivity.class);
            context.startActivity(home);
            ((Activity) context).finish();
        }
    }
}
