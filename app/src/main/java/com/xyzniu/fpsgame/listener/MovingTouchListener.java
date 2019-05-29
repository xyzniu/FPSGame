package com.xyzniu.fpsgame.listener;

import android.view.MotionEvent;
import android.view.View;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.pojo.Camera;

public class MovingTouchListener implements View.OnTouchListener {
    private Camera camera = Camera.getCamera();
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean isHold = false;
        
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isHold = true;
                break;
            case MotionEvent.ACTION_UP:
                isHold = false;
                break;
            default:
                return false;
        }
        
        switch (v.getId()) {
            case R.id.btn_forward:
                camera.isMovingForward = isHold;
                break;
            case R.id.btn_backward:
                camera.isMovingBackward = isHold;
                break;
            case R.id.btn_left:
                camera.isMovingLeft = isHold;
                break;
            case R.id.btn_right:
                camera.isMovingRight = isHold;
                break;
        }
        
        return true;
    }
}
