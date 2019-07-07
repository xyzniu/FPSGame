package com.xyzniu.fpsgame.objects;


import static com.xyzniu.fpsgame.objects.Geometry.distanceBetween;
import static com.xyzniu.fpsgame.util.Constants.*;

public class Enemy {
    
    private int hp;
    private volatile boolean valid;
    private Geometry.Vector position;
    private Geometry.Vector direction;
    private static Camera camera = Camera.getCamera();
    
    public Enemy(Geometry.Vector position) {
        this.position = position;
        this.direction = new Geometry.Vector(0, 0, 1);
        valid = true;
        hp = 3;
    }
    
    public void update() {
        if (distanceBetween(camera.getPosition(), position) > 1) {
            direction.normalize();
            direction.scale(ENEMY_STEP_LENGTH);
            position.add(direction);
        }
    }
    
    public boolean isValid() {
        return valid;
    }
    
    public Geometry.Vector getPosition() {
        return position;
    }
    
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    public void hit() {
        hp -= 1;
        if (hp <= 0) {
            valid = false;
        }
    }
    
    public Geometry.Vector getDirection() {
        return direction;
    }
    
    public void setDirection(Geometry.Vector diretion) {
        this.direction = diretion;
    }
}
