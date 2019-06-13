package com.xyzniu.fpsgame.objects;

import java.util.ArrayList;
import java.util.List;

public class BulletBag {
    
    private static List<Bullet> bullets = new ArrayList<>();
    
    public static List<Bullet> getBullets() {
        return bullets;
    }
}
