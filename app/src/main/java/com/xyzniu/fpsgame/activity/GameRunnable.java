package com.xyzniu.fpsgame.activity;

import android.os.Handler;
import android.os.SystemClock;
import android.widget.ImageView;
import android.widget.TextView;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.manager.PlayerManager;
import com.xyzniu.fpsgame.renderer.GameRenderer;

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
        timerTextView.setText(getTime());
        
        if (GameRenderer.renderSet) {
            killCountTextView.setText(String.format("Kill: %d", PlayerManager.getKillCount()));
            if (hp != PlayerManager.getHp()) {
                hp = PlayerManager.getHp();
                for (int i = 0; i < hp; i++) {
                    imageViews[i].setImageResource(R.drawable.heart_color);
                }
                for (int i = imageViews.length - 1; i >= hp; i--) {
                    imageViews[i].setImageResource(R.drawable.heart_noncolor);
                }
            }
        }
        
        handler.postDelayed(this, 500);
    }
    
    public int[] getTimeArray(){
        long millis = SystemClock.elapsedRealtime() - startTime;
        int seconds = (int) millis / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        int[] rst = new int[2];
        rst[0] = minutes;
        rst[1] = seconds;
        return rst;
    }
    
    public String getTime() {
        int[] rst = getTimeArray();
        return String.format("Time: %02d:%02d", rst[0], rst[1]);
    }
}
