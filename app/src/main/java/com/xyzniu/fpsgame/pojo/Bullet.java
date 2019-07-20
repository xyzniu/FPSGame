package com.xyzniu.fpsgame.pojo;

import com.xyzniu.fpsgame.util.Geometry;

public class Bullet {
    
    private Geometry.Vector position;
    private Geometry.Vector direction;
    // if hit the wall => invalid
    // if hit the enemy => invalid
    private volatile boolean isValid;
    
    public Bullet(Geometry.Vector position, Geometry.Vector direction, boolean isValid) {
        this.position = new Geometry.Vector(position);
        this.direction = new Geometry.Vector(direction);
        this.direction.normalize();
        this.direction.scale(0.3f);
        this.position.add(this.direction);
        this.direction.scale(0.3f);
        this.isValid = isValid;
    }
    
    public Geometry.Vector getPosition() {
        return position;
    }
    
    public boolean isValid() {
        return isValid;
    }
    
    public void setValid(boolean valid) {
        isValid = valid;
    }
    
    public void update() {
        position.add(direction);
        direction.scale(0.999f);
        if (direction.length() < 0.01f) {
            isValid = false;
        }
    }
    
}
