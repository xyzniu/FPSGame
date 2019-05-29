package com.xyzniu.fpsgame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.xyzniu.fpsgame.R;

public class MainActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void goToGamePage(View view) {
        goToActivity(GameActivity.class);
    }
    
    public void goToAboutPage(View view) {
        goToActivity(AboutActivity.class);
    }
    
    public void goToHowtoPage(View view) {
        goToActivity(HowtoActivity.class);
    }
    
    public void goToSettingsPage(View view) {
        goToActivity(SettingsActivity.class);
    }
    
    private void goToActivity(Class clazz) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivity(intent);
        this.finish();
    }
    
    public void exit(View view) {
        System.exit(0);
    }
}
