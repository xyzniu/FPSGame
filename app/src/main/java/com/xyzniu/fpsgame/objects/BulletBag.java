package com.xyzniu.fpsgame.objects;

import android.content.Context;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.programs.MainShaderProgram;
import com.xyzniu.fpsgame.programs.ShaderProgramManager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static android.opengl.Matrix.*;

public class BulletBag {
    
    private static Object bulletObject;
    private static MainShaderProgram mainShaderProgram;
    private static Matrix matrix = new Matrix();
    private static int bulletTexture;
    private static List<Bullet> bullets;
    private static Camera camera = Camera.getCamera();
    public static volatile boolean addBullet;
    
    public static void init(Context context) {
        bulletObject = new Object(context, R.raw.ball);
        mainShaderProgram = ShaderProgramManager.mainShaderProgram;
        bulletTexture = TextureManager.bulletTexture;
        bullets = new LinkedList<>();
        addBullet = false;
    }
    
    public static List<Bullet> getBullets() {
        return bullets;
    }
    
    public static void draw() {
        
        synchronized (bullets) {
            if (addBullet) {
                bullets.add(new Bullet(camera.getPosition(), camera.getDirection(), true, false));
                addBullet = false;
            }
            
            mainShaderProgram.useProgram();
            bulletObject.bindData(mainShaderProgram);
            
            Iterator<Bullet> it = bullets.iterator();
            Bullet bullet;
            while (it.hasNext()) {
                bullet = it.next();
                if (bullet.isValid()) {
                    drawBullet(bullet);
                } else {
                    it.remove();
                }
            }
        }
    }
    
    private static void drawBullet(Bullet bullet) {
        Geometry.Vector position = bullet.getPosition();
        setIdentityM(matrix.modelMatrix, 0);
        translateM(matrix.modelMatrix, 0, position.getX(), position.getY(), position.getZ());
        scaleM(matrix.modelMatrix, 0, 0.01f, 0.01f, 0.01f);
        matrix.updateMatrix();
        
        mainShaderProgram.setUniforms(matrix.modelMatrix,
                matrix.it_modelMatrix,
                matrix.modelViewProjectionMatrix,
                bulletTexture);
        bulletObject.draw();
    }
    
    public static void updateBullets() {
        synchronized (bullets) {
            Iterator<Bullet> it = bullets.iterator();
            Bullet bullet;
            while (it.hasNext()) {
                bullet = it.next();
                bullet.update();
                if (Ground.hitWallDetection(bullet.getPosition())) {
                    bullet.setValid(false);
                }
                
                if (bullet.isValid()) {
                    /*
                    if (EnemyManager.hitEnemyDetection(bullet.getPosition())) {
                        bullet.setValid(false);
                        bullet.setHit(true);
                    }*/
                }
            }
        }
    }
}
