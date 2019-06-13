package com.xyzniu.fpsgame.objects;

import com.xyzniu.fpsgame.pojo.Geometry;

import static com.xyzniu.fpsgame.pojo.Geometry.distanceBetween;

public class Bullet {
    
    private Geometry.Vector position;
    private Geometry.Vector direction;
    private boolean isValid;
    private boolean isHit;
    
    public Bullet(Geometry.Vector position, Geometry.Vector direction, boolean isValid, boolean isHit) {
        this.position = new Geometry.Vector(position);
        this.direction = new Geometry.Vector(direction);
        this.isValid = isValid;
        this.isHit = isHit;
    }
    
    public void update() {
        position.add(direction);
        direction.scale(0.99f);
        if (direction.length() < 0.5f) {
            isValid = false;
        }
    }
    
    public Geometry.Vector getPosition() {
        return position;
    }
    
    public void collisionDetect(Geometry.Vector objPosition, float radius) {
        float distance = distanceBetween(position, objPosition);
        if (distance < radius) {
            isHit = true;
            isValid = false;
        }
    }
    
    public boolean isHit() {
        return isHit;
    }
    
    public boolean isValid() {
        return isValid;
    }
    
    public void setValid(boolean valid) {
        isValid = valid;
    }
}
