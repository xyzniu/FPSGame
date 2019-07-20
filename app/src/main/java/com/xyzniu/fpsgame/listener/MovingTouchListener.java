package com.xyzniu.fpsgame.listener;

import android.view.MotionEvent;
import android.view.View;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.manager.PlayerManager;

public class MovingTouchListener implements View.OnTouchListener {
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean isHold;
        
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
                PlayerManager.isMovingForward = isHold;
                break;
            case R.id.btn_backward:
                PlayerManager.isMovingBackward = isHold;
                break;
            case R.id.btn_left:
                PlayerManager.isMovingLeft = isHold;
                break;
            case R.id.btn_right:
                PlayerManager.isMovingRight = isHold;
                break;
        }
        
        return true;
    }
}
