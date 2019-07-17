package com.xyzniu.fpsgame.renderer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import com.xyzniu.fpsgame.activity.GameRunnable;
import com.xyzniu.fpsgame.activity.ResultActivity;
import com.xyzniu.fpsgame.activity.MainActivity;
import com.xyzniu.fpsgame.objects.*;
import com.xyzniu.fpsgame.programs.ShaderProgramManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

public class Renderer implements GLSurfaceView.Renderer {
    
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
    
    private Player player;
    private GameRunnable gameRunnable;
    
    public Renderer(Context context, int mapId) {
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
        
        initPlayer();
        loadResources(context);
        initMap(context);
        initTimer();
        
        renderSet = true;
    }
    
    private void initTimer() {
        now = SystemClock.elapsedRealtime();
        elapsedtime = 0;
        times = 150;
    }
    
    private void initMap(Context context) {
        ground = new Ground(context, mapId);
        enemyManager = new EnemyManager(context, ground.getMobSpawner());
        bulletManager = new BulletManager(context);
    }
    
    private void loadResources(Context context) {
        ShaderProgramManager.init(context);
        TextureManager.init(context);
        SoundManager.init(context);
    }
    
    private void initPlayer() {
        player = new Player(3);
        PlayerManager.setPlayer(player, new Camera());
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
        
        ground.drawGround();
        bulletManager.draw();
        enemyManager.draw();
        
        
        if (elapsedtime > 20) {
            elapsedtime = 0;
            times += 1;
            if (times >= 100) {
                enemyManager.addEnemies();
                times = 0;
            }
            PlayerManager.updateCamera();
            bulletManager.updateBullets();
            enemyManager.updateEnemies();
            HitDetection.hitDetection(bulletManager.getBullets(), enemyManager.getEnemies());
            
            // check player
            if (player.dead()) {
                dead();
            }
        }
        
        if (PlayerManager.atEndPoint()) {
            win();
        }
    }
    
    private void goToActivity(boolean win) {
        Intent activity = new Intent();
        activity.setClass(this.context, ResultActivity.class);
        activity.putExtra(ResultActivity.TIME, gameRunnable.getTime());
        activity.putExtra(ResultActivity.WIN, win);
        activity.putExtra(ResultActivity.KILL, player.getKill());
        context.startActivity(activity);
        ((Activity) context).finish();
    }
    
    private void dead() {
        goToActivity(false);
    }
    
    private void win() {
        goToActivity(true);
    }
    
    public void setGameRunnable(GameRunnable gameRunnable) {
        this.gameRunnable = gameRunnable;
    }
}
