package com.xyzniu.fpsgame.pojo;

import com.xyzniu.fpsgame.util.Geometry;
import com.xyzniu.fpsgame.manager.Ground;
import com.xyzniu.fpsgame.manager.PlayerManager;

import static com.xyzniu.fpsgame.util.Geometry.distanceBetween;
import static com.xyzniu.fpsgame.util.SoundHelper.*;
import static com.xyzniu.fpsgame.renderer.Renderer.delta;
import static com.xyzniu.fpsgame.config.Constants.*;

public class Enemy {
    
    private int hp;
    private volatile boolean valid;
    private Geometry.Vector position;
    private Geometry.Vector direction;
    private int rotation;
    private int wait;
    
    public Enemy(Geometry.Vector position) {
        this.position = new Geometry.Vector(position);
        this.direction = new Geometry.Vector(0, 0, 1);
        valid = true;
        hp = 3;
        rotation = 0;
        wait = 0;
    }
    
    public void update() {
        if (Ground.hitWallDetection(position)) {
            return;
        }
        if (distanceBetween(PlayerManager.getPosition(), position) < 1) {
            bite();
            return;
        }
        
        direction.normalize();
        direction.scale(ENEMY_STEP_LENGTH * delta);
        position.add(direction);
        
    }
    
    public boolean isValid() {
        return valid;
    }
    
    public Geometry.Vector getPosition() {
        return position;
    }
    
    public boolean hit() {
        hp -= 1;
        if (hp <= 0) {
            valid = false;
            return true;
        }
        return false;
    }
    
    public void setDirection(Geometry.Vector diretion) {
        this.direction = diretion;
    }
    
    public int getRotation() {
        return rotation;
    }
    
    public void rotationDec() {
        rotation--;
    }
    
    public void bite() {
        if (rotation == 0 && wait <= 0) {
            rotation = 30;
            wait = 30;
            soundPool.play(soundMap.get(CRUNCH_SOUND), 1, 1, 0, 0, 1);
            PlayerManager.bitePlayer();
        } else {
            if (wait > 0) {
                wait--;
            }
        }
    }
}
