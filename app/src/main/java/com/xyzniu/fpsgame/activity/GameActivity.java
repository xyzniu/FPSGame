package com.xyzniu.fpsgame.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.xyzniu.fpsgame.listener.MovingTouchListener;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.renderer.Renderer;
import com.xyzniu.fpsgame.pojo.Camera;

public class GameActivity extends Activity {
    
    private GLSurfaceView glSurfaceView;
    private boolean rendererSet = false;
    final Renderer objectRenderer = new Renderer(this);
    final Camera camera = Camera.getCamera();
    private Button forwardBtn;
    private Button backwardBtn;
    private Button leftBtn;
    private Button rightBtn;
    private Button shootBtn;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // check if the system actually supports OpenGL ES 2.0
        super.onCreate(savedInstanceState);
        glSurfaceView = new GLSurfaceView(this);
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")));
        if (supportsEs2) {
            glSurfaceView.setEGLContextClientVersion(2);
            // assign our renderer
            glSurfaceView.setRenderer(objectRenderer);
            rendererSet = true;
        } else {
            Toast.makeText(this, "This device does not support",
                    Toast.LENGTH_LONG).show();
            return;
        }
        
        addContentView(glSurfaceView,
                new ActionBar.LayoutParams(glSurfaceView.getLayoutParams().WRAP_CONTENT,
                        glSurfaceView.getLayoutParams().WRAP_CONTENT));
        
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View view = layoutInflater.inflate(R.layout.activity_game, null);
        
        addContentView(view, new ActionBar.LayoutParams(glSurfaceView.getLayoutParams().WRAP_CONTENT,
                glSurfaceView.getLayoutParams().WRAP_CONTENT));
        
        initButtons();
        
    }
    
    private void initButtons() {
        forwardBtn = findViewById(R.id.btn_forward);
        forwardBtn.setOnTouchListener(new MovingTouchListener());
        backwardBtn = findViewById(R.id.btn_backward);
        backwardBtn.setOnTouchListener(new MovingTouchListener());
        leftBtn = findViewById(R.id.btn_left);
        leftBtn.setOnTouchListener(new MovingTouchListener());
        rightBtn = findViewById(R.id.btn_right);
        rightBtn.setOnTouchListener(new MovingTouchListener());
    }
    
    
    public void goToHomePage(View view) {
        Intent home = new Intent();
        home.setClass(this, MainActivity.class);
        startActivity(home);
        this.finish();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        if (rendererSet) {
            glSurfaceView.onPause();
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (rendererSet) {
            glSurfaceView.onResume();
        }
    }
    
}
