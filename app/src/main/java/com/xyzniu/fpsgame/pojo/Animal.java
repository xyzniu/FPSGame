package com.xyzniu.fpsgame.pojo;

import com.xyzniu.fpsgame.util.Geometry;

public class Animal {
    
    private Geometry.Vector position;
    private boolean collected;
    
    public Animal(Geometry.Vector position) {
        this.position = position;
        this.collected = false;
    }
    
    public Geometry.Vector getPosition() {
        return position;
    }
    
    public boolean isCollected() {
        return collected;
    }
    
    public void collect() {
        collected = true;
    }
}
