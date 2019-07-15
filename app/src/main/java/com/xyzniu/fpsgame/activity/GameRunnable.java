package com.xyzniu.fpsgame.activity;

import android.os.Handler;
import android.os.SystemClock;
import android.widget.ImageView;
import android.widget.TextView;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.objects.PlayerManager;
import com.xyzniu.fpsgame.renderer.Renderer;

public class GameRunnable implements Runnable {
    
    private TextView killCountTextView;
    private TextView timerTextView;
    private Handler handler;
    private Long startTime;
    private ImageView[] imageViews;
    private int hp;
    
    public GameRunnable(TextView killCountTextView, TextView timerTextView, Handler handler, ImageView[] imageViews) {
        this.killCountTextView = killCountTextView;
        this.timerTextView = timerTextView;
        this.handler = handler;
        startTime = SystemClock.elapsedRealtime();
        this.imageViews = imageViews;
        hp = 3;
    }
    
    @Override
    public void run() {
        long millis = SystemClock.elapsedRealtime() - startTime;
        int seconds = (int) millis / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        timerTextView.setText(String.format("Time: %02d:%02d", minutes, seconds));
        
        if (Renderer.renderSet) {
            killCountTextView.setText(String.format("Kill: %d", PlayerManager.getKillCount()));
            if (hp != PlayerManager.getHp()) {
                hp = PlayerManager.getHp();
                for (int i = imageViews.length - 1; i >= hp; i--) {
                    imageViews[i].setImageResource(R.drawable.heart_white);
                }
            }
        }
        
        handler.postDelayed(this, 500);
    }
}
