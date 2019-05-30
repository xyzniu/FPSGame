package com.xyzniu.fpsgame.activity;

import android.os.Handler;
import android.widget.TextView;

public class TimerRunnable implements Runnable {
    
    private long startTime;
    private TextView timerView;
    private Handler timerHandler;
    
    public TimerRunnable(long startTime, TextView timerView, Handler timerHandler) {
        this.startTime = startTime;
        this.timerView = timerView;
        this.timerHandler = timerHandler;
    }
    
    @Override
    public void run() {
        long millis = System.currentTimeMillis() - startTime;
        int seconds = (int) millis / 1000;
        int minutes = seconds / 60;
        timerView.setText(String.format("Time: %02d:%02d", minutes, seconds));
        timerHandler.postDelayed(this, 500);
    }
}
