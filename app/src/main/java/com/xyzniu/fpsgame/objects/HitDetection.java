
package com.xyzniu.fpsgame.objects;

import android.util.Log;

import java.util.Iterator;
import java.util.List;

import static com.xyzniu.fpsgame.objects.Geometry.distanceBetween;

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
            minDistance = 1;
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
                Log.w("Hit", minDistanceEnemy.getHP() + minDistanceEnemy.isValid());
                b.setValid(false);
            }
            enemyIterator = enemies.iterator();
        }
    }
}
