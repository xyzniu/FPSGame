package com.xyzniu.fpsgame.activity;

import android.os.Handler;
import android.widget.TextView;
import com.xyzniu.fpsgame.objects.PlayerManager;
import com.xyzniu.fpsgame.renderer.Renderer;


public class KillCountRunnable implements Runnable {
    private TextView killCountView;
    private Handler handler;
    
    
    public KillCountRunnable(TextView killCountView, android.os.Handler killCountHandler) {
        this.killCountView = killCountView;
        this.handler = killCountHandler;
    }
    
    @Override
    public void run() {
        if (!Renderer.renderSet) {
            killCountView.setText(String.format("Kill: %d", 0));
        } else {
            killCountView.setText(String.format("Kill: %d", PlayerManager.getKillCount()));
        }
        handler.postDelayed(this, 500);
    }
}
