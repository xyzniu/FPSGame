package com.xyzniu.fpsgame.listener;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.activity.GameActivity;
import com.xyzniu.fpsgame.objects.BulletBag;

import java.util.HashMap;

public class ShootTouchListener implements View.OnTouchListener {
    
    private SoundPool soundPool;
    public HashMap<Integer, Integer> soundMap;
    private GLSurfaceView view;
    
    public ShootTouchListener(GLSurfaceView view, Context context) {
        this.view = view;
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundMap = new HashMap<>();
        soundMap.put(0, soundPool.load(context, R.raw.shoot_sound, 1));
    }
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                BulletBag.addBullet = true;
                view.queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        soundPool.play(soundMap.get(0), 1, 1, 0, 0, 1);
                    }
                });
                break;
            default:
                return false;
        }
        return true;
    }
}
