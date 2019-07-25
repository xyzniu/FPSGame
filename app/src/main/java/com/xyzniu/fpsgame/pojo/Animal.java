package com.xyzniu.fpsgame.pojo;

import com.xyzniu.fpsgame.util.Geometry;

import static com.xyzniu.fpsgame.util.SoundHelper.*;

public class Animal {
    
    private Geometry.Vector position;
    private boolean collected;
    private float transY;
    private int rotateY;
    
    public Animal(Geometry.Vector position) {
        this.position = position;
        this.collected = false;
        transY = 0;
        rotateY = 0;
    }
    
    public Geometry.Vector getPosition() {
        return position;
    }
    
    public boolean isCollected() {
        return collected;
    }
    
    public void collect() {
        soundPool.play(soundMap.get(CHICK_SOUND), 1, 1, 0, 0, 1);
        collected = true;
    }
    
    public float getTransY() {
        if (transY <= 0) {
            transY += 0.005f;
        } else {
            transY = -0.2f;
        }
        return transY;
    }
    
    public float getRotateY() {
        if (rotateY == 360) {
            rotateY = 0;
        }
        return rotateY++;
    }
}
