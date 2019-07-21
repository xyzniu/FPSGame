package com.xyzniu.fpsgame.manager;

import android.content.Context;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.pojo.Bullet;
import com.xyzniu.fpsgame.pojo.Player;
import com.xyzniu.fpsgame.programs.MainShaderProgram;
import com.xyzniu.fpsgame.programs.ShaderProgramManager;
import com.xyzniu.fpsgame.util.Geometry;
import com.xyzniu.fpsgame.util.Matrix;
import com.xyzniu.fpsgame.util.Model;
import com.xyzniu.fpsgame.util.TextureManager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static android.opengl.Matrix.*;

public class BulletManager {
    
    private Model appleModel;
    private MainShaderProgram mainShaderProgram;
    private Matrix matrix = new Matrix();
    private int appleTexture;
    private List<Bullet> bullets;
    public static volatile boolean addBullet;
    
    public BulletManager(Context context) {
        appleModel = new Model(context, R.raw.apple);
        mainShaderProgram = ShaderProgramManager.mainShaderProgram;
        appleTexture = TextureManager.appleTexture;
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
        appleModel.bindData(mainShaderProgram);
        
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
        rotateM(matrix.modelMatrix, 0, getRotation(position, PlayerManager.getPosition()), 0, 1, 0);
        rotateM(matrix.modelMatrix, 0, bullet.rotate(), -0.5f, 0, -0.5f);
        scaleM(matrix.modelMatrix, 0, 0.1f, 0.1f, 0.1f);
        matrix.updateMatrix();
        
        mainShaderProgram.setUniforms(matrix.modelMatrix,
                matrix.it_modelMatrix,
                matrix.modelViewProjectionMatrix,
                appleTexture);
        appleModel.draw();
    }
    
    private float getRotation(Geometry.Vector bPosition, Geometry.Vector uPosition) {
        Geometry.Vector direction = Geometry.Vector.sub(uPosition, bPosition);
        double radian = (Math.atan2(direction.getX(), direction.getZ()) - Math.atan2(0, 1));
        return (float) Math.toDegrees(radian);
    }
    
    public void updateBullets() {
        Iterator<Bullet> it = bullets.iterator();
        Bullet bullet;
        while (it.hasNext()) {
            bullet = it.next();
            bullet.update();
            if (Ground.hitDetection(bullet.getPosition())) {
                bullet.setValid(false);
            }
            if (!bullet.isValid()) {
                it.remove();
            }
        }
    }
}
