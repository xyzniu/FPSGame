package com.xyzniu.fpsgame.objects;

import android.content.Context;
import android.util.Log;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.pojo.Camera;
import com.xyzniu.fpsgame.pojo.Geometry;
import com.xyzniu.fpsgame.pojo.Light;
import com.xyzniu.fpsgame.programs.MainShaderProgram;
import com.xyzniu.fpsgame.util.MatrixHelper;
import com.xyzniu.fpsgame.util.TextureHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static android.opengl.Matrix.*;

public class EnemyManager {
    
    public Object object;
    private int texture;
    private List<Enemy> enemies;
    private List<Geometry.Vector> mobSpawner;
    private MainShaderProgram program;
    private Random random = new Random();
    private Matrix matrix = new Matrix();
    private Camera camera = Camera.getCamera();
    private Light light = Light.getLight();
    private List<Bullet> bullets = BulletBag.getBullets();
    
    public EnemyManager(Context context, List<Geometry.Vector> mobSpawner) {
        object = new Object(context, R.raw.fox);
        texture = TextureHelper.loadTexture(context, R.raw.foxuv);
        enemies = new ArrayList<>();
        program = new MainShaderProgram(context);
        this.mobSpawner = mobSpawner;
    }
    
    public void draw() {
        // detect bullets
        detectBullets();
        // add enemies by spawner
        addEnemies();
        // loop to draw
        drawEnemies();
    }
    
    private void detectBullets() {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        Iterator<Enemy> enemyIterator = enemies.iterator();
        Bullet b;
        Enemy e;
        while (bulletIterator.hasNext()) {
            b = bulletIterator.next();
            b.update();
            while (enemyIterator.hasNext()) {
                e = enemyIterator.next();
                b.collisionDetect(e.getPosition(), 2f);
                if (b.isHit()) {
                    Log.w("bulletHit", "true");
                    e.setValid(false);
                } else {
                    Log.w("bulletPosition", b.getPosition().toString());
                }
            }
        }
    }
    
    private void drawEnemies() {
        program.useProgram();
        object.bindData(program);
        
        Enemy e;
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            e = iterator.next();
            e.update(camera.getPosition());
            if (e.isValid()) {
                drawEnemy(e);
            } else {
                iterator.remove();
            }
        }
    }
    
    private void drawEnemy(Enemy enemy) {
        setIdentityM(matrix.modelMatrix, 0);
        // MatrixHelper.rotateMatrix((matrix.modelMatrix, 0, getRotation(enemy.getPosition(), camera.getPosition()));
        MatrixHelper.translateMatrix(matrix.modelMatrix, 0, enemy.getPosition());
        MatrixHelper.translateMatrix(matrix.modelMatrix, 0, new Geometry.Vector(0, -1, 0));
        scaleM(matrix.modelMatrix, 0, 0.01f, 0.01f, 0.01f);
        matrix.updateMatrix();
        
        program.setUniforms(matrix.modelMatrix,
                matrix.it_modelMatrix,
                matrix.modelViewProjectionMatrix,
                light.getLightPosition(),
                light.getLightColor(),
                camera.getPositionVec3(),
                texture);
        
        object.draw();
    }

//    private Geometry.Vector getRotation(Geometry.Vector enemyPosition, Geometry.Vector cameraPosition) {
//
//
//
//    }
    
    private void addEnemies() {
        if (enoughEnemies()) {
            return;
        }
        boolean notEnoughEnemies = notEnoughEnemies();
        for (int i = 0; i < mobSpawner.size(); i++) {
            if (notEnoughEnemies) {
                enemies.add(new Enemy(mobSpawner.get(i), camera.getPosition()));
            } else if (random.nextInt(1000) < 5) {
                enemies.add(new Enemy(mobSpawner.get(i), camera.getPosition()));
            }
        }
    }
    
    private boolean notEnoughEnemies() {
        return enemies.size() < mobSpawner.size();
    }
    
    private boolean enoughEnemies() {
        return enemies.size() >= 15;
    }
    
    
}
