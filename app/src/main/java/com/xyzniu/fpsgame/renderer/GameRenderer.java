package com.xyzniu.fpsgame.renderer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import com.xyzniu.fpsgame.activity.GameRunnable;
import com.xyzniu.fpsgame.activity.ResultActivity;
import com.xyzniu.fpsgame.manager.*;
import com.xyzniu.fpsgame.programs.ShaderProgramManager;
import com.xyzniu.fpsgame.util.Matrix;
import com.xyzniu.fpsgame.util.SoundHelper;
import com.xyzniu.fpsgame.util.TextureManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

public class GameRenderer implements GLSurfaceView.Renderer {
    
    private final Context context;
    private Ground ground;
    private EnemyManager enemyManager;
    private BulletManager bulletManager;
    public volatile static boolean renderSet = false;
    
    private long now;
    public static float delta;
    public float elapsedtime;
    private int times;
    private int mapId;
    
    private GameRunnable gameRunnable;
    
    public GameRenderer(Context context, int mapId) {
        this.context = context;
        this.mapId = mapId;
    }
    
    @Override
    @Deprecated
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        loadResources(context);
        initManager(context);
        initTimer();
        
        renderSet = true;
    }
    
    private void initManager(Context context) {
        PlayerManager.init();
        ground = new Ground(context, mapId);
        enemyManager = new EnemyManager(context, ground.getMobSpawner());
        bulletManager = new BulletManager(context);
    }
    
    private void initTimer() {
        now = SystemClock.elapsedRealtime();
        elapsedtime = 0;
        times = 150;
    }
    
    private void loadResources(Context context) {
        ShaderProgramManager.init(context);
        TextureManager.init(context);
        SoundHelper.init(context);
    }
    
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        Matrix.perspective(width, height);
    }
    
    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        long elapsed = SystemClock.elapsedRealtime() - now;
        now = SystemClock.elapsedRealtime();
        delta = elapsed / 10f;
        elapsedtime += elapsed;
        
        // draw the opaque first
        ground.drawGround();
        bulletManager.drawBullets();
        enemyManager.drawEnemies();
        
        // draw the transparent
        glDepthMask(false);
        ground.drawEndPoint();
        glDepthMask(true);
        
        
        if (elapsedtime > 20) {
            elapsedtime = 0;
            times += 1;
            if (times >= 300) {
                enemyManager.addEnemies();
                times = 0;
            }
            PlayerManager.updateCamera();
            bulletManager.updateBullets();
            enemyManager.updateEnemies();
            HitDetection.hitDetection(bulletManager.getBullets(), enemyManager.getEnemies());
            
            // check player
            if (PlayerManager.dead() || gameRunnable.getTimeArray()[0] >= 60) {
                dead();
            }
        }
        
        if (ground.openEndPoint() && PlayerManager.atEndPoint()) {
            win();
        }
    }
    
    private void goToResultActivity(boolean win) {
        Intent activity = new Intent();
        activity.setClass(this.context, ResultActivity.class);
        activity.putExtra(ResultActivity.TIME, gameRunnable.getTime());
        activity.putExtra(ResultActivity.WIN, win);
        activity.putExtra(ResultActivity.KILL, PlayerManager.getKill());
        context.startActivity(activity);
        ((Activity) context).finish();
    }
    
    private void dead() {
        goToResultActivity(false);
    }
    
    private void win() {
        goToResultActivity(true);
    }
    
    public void setGameRunnable(GameRunnable gameRunnable) {
        this.gameRunnable = gameRunnable;
    }
}