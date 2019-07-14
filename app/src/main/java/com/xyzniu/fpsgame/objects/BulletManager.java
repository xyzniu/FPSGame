package com.xyzniu.fpsgame.objects;

import android.content.Context;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.programs.MainShaderProgram;
import com.xyzniu.fpsgame.programs.ShaderProgramManager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static android.opengl.Matrix.*;

public class BulletManager {
    
    private Object bulletObject;
    private MainShaderProgram mainShaderProgram;
    private Matrix matrix = new Matrix();
    private int bulletTexture;
    private List<Bullet> bullets;
    public static volatile boolean addBullet;
    
    public BulletManager(Context context) {
        bulletObject = new Object(context, R.raw.ball);
        mainShaderProgram = ShaderProgramManager.mainShaderProgram;
        bulletTexture = TextureManager.bulletTexture;
        bullets = new LinkedList<>();
        addBullet = false;
    }
    
    public List<Bullet> getBullets() {
        return bullets;
    }
    
    public void draw() {
        if (addBullet) {
            bullets.add(new Bullet(PlayerManager.getPosition(), PlayerManager.getDirection(), true));
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
    
    private void drawBullet(Bullet bullet) {
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
    
    public void updateBullets() {
        Iterator<Bullet> it = bullets.iterator();
        Bullet bullet;
        while (it.hasNext()) {
            bullet = it.next();
            bullet.update();
            if (Ground.hitWallDetection(bullet.getPosition())) {
                bullet.setValid(false);
            }
            if (!bullet.isValid()) {
                it.remove();
            }
        }
    }
}
