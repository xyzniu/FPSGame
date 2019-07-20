package com.xyzniu.fpsgame.listener;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;
import com.xyzniu.fpsgame.manager.BulletManager;

import static com.xyzniu.fpsgame.util.SoundHelper.*;

public class ShootTouchListener implements View.OnTouchListener {
    
    private GLSurfaceView view;
    
    @Deprecated
    public ShootTouchListener(GLSurfaceView view, Context context) {
        this.view = view;
    }
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                BulletManager.addBullet = true;
                view.queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        soundPool.play(soundMap.get(SHOOT_SOUND), 1, 1, 0, 0, 1);
                    }
                });
                break;
            default:
                return false;
        }
        return true;
    }
}
