package com.xyzniu.fpsgame.objects;

import android.content.Context;
import android.util.Log;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.programs.MainShaderProgram;
import com.xyzniu.fpsgame.util.MatrixHelper;
import com.xyzniu.fpsgame.util.TextureHelper;

import java.util.*;

import static android.opengl.Matrix.*;

public class EnemyManager {
    
    public static Object enemyObject;
    private static int enemyTexture;
    private static List<Enemy> enemies;
    private static List<Geometry.Vector> mobSpawner;
    private static MainShaderProgram program;
    private static Random random = new Random();
    private static Matrix matrix = new Matrix();
    private static Camera camera = Camera.getCamera();
    private static Light light = Light.getLight();
    
    public EnemyManager(Context context, List<Geometry.Vector> mobSpawner) {
        enemyObject = new Object(context, R.raw.fox);
        enemyTexture = TextureManager.foxTexture;
        enemies = new LinkedList<>();
        program = new MainShaderProgram(context);
        this.mobSpawner = mobSpawner;
    }
    
    public static void updateEnemies() {
        synchronized (enemies) {
            addEnemies();
            
            Enemy e;
            Iterator<Enemy> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                e = enemyIterator.next();
                e.update();
                if (!e.isValid()) {
                    enemyIterator.remove();
                }
            }
        }
    }
    
    public boolean hitEnemyDetection(Geometry.Vector position) {
        double min = 1;
        Iterator<Enemy> iterator = enemies.iterator();
        Enemy e;
        Enemy minE = null;
        while (iterator.hasNext()) {
            e = iterator.next();
            float distance = Geometry.distanceBetween(position, e.getPosition());
            if (min < distance) {
                min = distance;
                minE = e;
            }
        }
        if (minE != null) {
            minE.hit();
            return true;
        } else {
            return false;
        }
    }
    
    
    public void draw() {
        program.useProgram();
        enemyObject.bindData(program);
        
        synchronized (enemies) {
            
            Enemy e;
            Iterator<Enemy> iterator = enemies.iterator();
            while (iterator.hasNext()) {
                e = iterator.next();
                if (e.isValid()) {
                    drawEnemy(e);
                }
            }
        }
    }
    
    private void drawEnemy(Enemy e) {
        setIdentityM(matrix.modelMatrix, 0);
        MatrixHelper.translateMatrix(matrix.modelMatrix, 0, e.getPosition());
        MatrixHelper.translateMatrix(matrix.modelMatrix, 0, new Geometry.Vector(0, -0.2f, 0));
        rotateM(matrix.modelMatrix, 0, getRotation(e, camera.getPosition()), 0, 1, 0);
        matrix.updateMatrix();
        
        program.setUniforms(matrix.modelMatrix,
                matrix.it_modelMatrix,
                matrix.modelViewProjectionMatrix,
                light.getLightPosition(),
                light.getLightColor(),
                camera.getPositionVec3(),
                enemyTexture);
        
        enemyObject.draw();
    }
    
    private float getRotation(Enemy e, Geometry.Vector uPosition) {
        Geometry.Vector ePosition = e.getPosition();
        Geometry.Vector direction = Geometry.Vector.sub(uPosition, ePosition);
        e.setDirection(new Geometry.Vector(direction.getX(), 0, direction.getZ()));
        double radian = (Math.atan2(direction.getX(), direction.getZ()) - Math.atan2(0, 1));
        return (float) Math.toDegrees(radian);
    }
    
    private static void addEnemies() {
        if (enoughEnemies()) {
            return;
        }
        boolean notEnoughEnemies = notEnoughEnemies();
        for (int i = 0; i < mobSpawner.size(); i++) {
            if (notEnoughEnemies) {
                enemies.add(new Enemy(mobSpawner.get(i)));
            } else if (random.nextInt(10) < 3) {
                enemies.add(new Enemy(mobSpawner.get(i)));
            }
        }
    }
    
    private static boolean notEnoughEnemies() {
        return enemies.size() < mobSpawner.size();
    }
    
    private static boolean enoughEnemies() {
        return enemies.size() >= 15;
    }
    
    public List<Enemy> getEnemies() {
        return enemies;
    }
}
