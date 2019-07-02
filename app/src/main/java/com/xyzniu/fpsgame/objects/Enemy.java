package com.xyzniu.fpsgame.objects;


import com.xyzniu.fpsgame.pojo.Geometry;

import static com.xyzniu.fpsgame.util.Constants.*;

public class Enemy {
    
    private int hp;
    private boolean valid;
    private Geometry.Vector position;
    private Geometry.Vector direction;
    
    public Enemy(Geometry.Vector position, Geometry.Vector userPosition) {
        this.position = position;
        this.direction = Geometry.Vector.sub(userPosition, position);
        this.direction.normalize();
        valid = true;
        hp = 10;
    }
    
    public void update(Geometry.Vector userPosition) {
        if (hp <= 0) {
            valid = false;
            return;
        }
        direction.normalize();
        direction.scale(ENEMY_STEP_LENGTH);
        position.add(direction);
        direction = Geometry.Vector.sub(userPosition, position);
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
}
