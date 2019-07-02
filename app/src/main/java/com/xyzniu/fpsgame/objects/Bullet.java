package com.xyzniu.fpsgame.objects;

public class Bullet {
    
    private Geometry.Vector position;
    private Geometry.Vector direction;
    // if hit the wall => invalid
    // if hit the enemy => invalid
    private boolean isValid;
    private boolean isHit;
    
    public Bullet(Geometry.Vector position, Geometry.Vector direction, boolean isValid, boolean isHit) {
        this.position = new Geometry.Vector(position);
        this.direction = new Geometry.Vector(direction);
        this.direction.normalize();
        this.direction.scale(0.3f);
        this.position.add(this.direction);
        this.direction.scale(0.3f);
        this.isValid = isValid;
        this.isHit = isHit;
    }
    
    public Geometry.Vector getPosition() {
        return position;
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
    
    public void setHit(boolean isHit) {
        this.isHit = isHit;
    }
    
    public void update() {
        position.add(direction);
        direction.scale(0.999f);
        if (direction.length() < 0.01f) {
            isValid = false;
        }
    }
    
    public Geometry.Vector getDirection() {
        return direction;
    }
    
}
