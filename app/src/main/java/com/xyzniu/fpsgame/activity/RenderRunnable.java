package com.xyzniu.fpsgame.activity;

import android.os.Handler;
import com.xyzniu.fpsgame.objects.BulletBag;
import com.xyzniu.fpsgame.pojo.Camera;
import com.xyzniu.fpsgame.renderer.Renderer;


/**
 * camera move update
 * bullet update
 * enemy update
 */
public class RenderRunnable implements Runnable {
    
    private Camera camera = Camera.getCamera();
    private Handler renderHandler;
    
    
    public RenderRunnable(android.os.Handler renderHandler) {
        this.renderHandler = renderHandler;
    }
    
    @Override
    public void run() {
        if (Renderer.renderSet) {
            camera.updateCamera();
            BulletBag.updateBullets();
        }
        renderHandler.postDelayed(this, 20);
    }
}
