package com.xyzniu.fpsgame.listener;

import android.view.MotionEvent;
import android.view.View;
import com.xyzniu.fpsgame.objects.Bullet;
import com.xyzniu.fpsgame.objects.BulletBag;
import com.xyzniu.fpsgame.pojo.Camera;

import java.util.List;

public class ShootTouchListener implements View.OnTouchListener {
    private static Camera camera = Camera.getCamera();
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        List<Bullet> bullets = BulletBag.getBullets();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                bullets.add(new Bullet(camera.getPosition(), camera.getDirection(), true, false));
                break;
            default:
                return false;
        }
        return true;
    }
}
