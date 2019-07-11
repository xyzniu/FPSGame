
package com.xyzniu.fpsgame.objects;


import java.util.Iterator;
import java.util.List;

import static com.xyzniu.fpsgame.objects.Geometry.distanceBetween;
import static com.xyzniu.fpsgame.objects.Sound.*;

public class HitDetection {
    
    public static void hitDetection(List<Bullet> bullets, List<Enemy> enemies) {
        Iterator<Enemy> enemyIterator = enemies.iterator();
        Iterator<Bullet> bulletIterator = bullets.iterator();
        Bullet b;
        Enemy e;
        float minDistance;
        Enemy minDistanceEnemy;
        while (bulletIterator.hasNext()) {
            b = bulletIterator.next();
            if (!b.isValid()) {
                continue;
            }
            minDistance = (float) 0.35;
            minDistanceEnemy = null;
            while (enemyIterator.hasNext()) {
                e = enemyIterator.next();
                if (!e.isValid()) {
                    continue;
                }
                
                float distance = distanceBetween(b.getPosition(), e.getPosition());
                
                if (distance < minDistance) {
                    minDistance = distance;
                    minDistanceEnemy = e;
                }
            }
            if (minDistanceEnemy != null) {
                minDistanceEnemy.hit();
                b.setValid(false);
                soundPool.play(soundMap.get(SCREAM_SOUND), 1, 1, 0, 0, 1);
            }
            enemyIterator = enemies.iterator();
        }
    }
}
