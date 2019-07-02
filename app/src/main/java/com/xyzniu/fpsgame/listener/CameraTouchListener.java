package com.xyzniu.fpsgame.listener;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;
import com.xyzniu.fpsgame.objects.Camera;

public class CameraTouchListener implements View.OnTouchListener {
    
    private GLSurfaceView view;
    float prevX = 0;
    
    public CameraTouchListener(GLSurfaceView view) {
        this.view = view;
    }
    
    @Override
    public boolean onTouch(View v, final MotionEvent event) {
        
        if (event != null) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                prevX = event.getX();
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                final float deltaX = event.getX() - prevX;
                prevX = event.getX();
                view.queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        Camera.getCamera().dragCamera(deltaX);
                    }
                });
            }
            return true;
        }
        return false;
    }
}
