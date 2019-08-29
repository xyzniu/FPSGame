package com.xyzniu.fpsgame.manager;

import android.content.Context;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.pojo.Enemy;
import com.xyzniu.fpsgame.programs.MainShaderProgram;
import com.xyzniu.fpsgame.programs.ShaderProgramManager;
import com.xyzniu.fpsgame.util.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;

public class EnemyManager {
    
    public Model enemyModel;
    private List<Enemy> enemies;
    private List<Geometry.Vector> mobSpawner;
    private MainShaderProgram program;
    private Random random = new Random();
    private Matrix matrix = new Matrix();
    
    public EnemyManager(Context context, List<Geometry.Vector> mobSpawner) {
        enemyModel = new Model(context, R.raw.fox);
        enemies = new LinkedList<>();
        program = ShaderProgramManager.mainShaderProgram;
        this.mobSpawner = mobSpawner;
        initEnemies();
    }
    
    private void initEnemies() {
        for (int i = 0; i < mobSpawner.size(); i++) {
            enemies.add(new Enemy(mobSpawner.get(i)));
        }
    }
    
    
    public void drawEnemies() {
        program.useProgram();
        enemyModel.bindData(program);
        
        Enemy e;
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            e = iterator.next();
            if (e.isValid()) {
                drawEnemy(e);
            } else {
                iterator.remove();
            }
        }
    }
    
    private void drawEnemy(Enemy e) {
        setIdentityM(matrix.modelMatrix, 0);
        MatrixHelper.translateMatrix(matrix.modelMatrix, 0, e.getPosition());
        MatrixHelper.translateMatrix(matrix.modelMatrix, 0, new Geometry.Vector(0, -0.2f, 0));
        
        rotateM(matrix.modelMatrix, 0, getRotation(e, PlayerManager.getPosition()), 0, 1, 0);
        rotateM(matrix.modelMatrix, 0, e.getRotation(), 1, 0, 0);
        if (e.getRotation() > 0) {
            e.rotationDec();
        }
        matrix.updateMatrix();
        
        program.setUniforms(matrix.modelMatrix,
                matrix.it_modelMatrix,
                matrix.modelViewProjectionMatrix,
                TextureManager.foxTexture);
        
        enemyModel.draw();
    }
    
    private float getRotation(Enemy e, Geometry.Vector uPosition) {
        Geometry.Vector ePosition = e.getPosition();
        Geometry.Vector direction = Geometry.Vector.sub(uPosition, ePosition);
        e.setDirection(new Geometry.Vector(direction.getX(), 0, direction.getZ()));
        double radian = (Math.atan2(direction.getX(), direction.getZ()) - Math.atan2(0, 1));
        return (float) Math.toDegrees(radian);
    }
    
    public void updateEnemies() {
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
    
    public void addEnemies() {
        if (enoughEnemies()) {
            return;
        }
        Geometry.Vector mobSpawnerPosition;
        for (int i = 0; i < mobSpawner.size(); i++) {
            mobSpawnerPosition = mobSpawner.get(i);
            if (HitDetection.hitCamera(mobSpawnerPosition)) {
                continue;
            }
            if (random.nextInt(10) < 5) {
                enemies.add(new Enemy(mobSpawnerPosition));
            }
        }
    }
    
    private boolean enoughEnemies() {
        return enemies.size() >= 15;
    }
    
    public List<Enemy> getEnemies() {
        return enemies;
    }
}
