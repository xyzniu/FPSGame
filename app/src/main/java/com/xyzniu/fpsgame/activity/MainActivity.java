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
    
    public void goMapActivity(View view) {
        ActivityHelper.goToActivity(this, MapActivity.class);
    }
    
    public void goAboutActivity(View view) {
        ActivityHelper.goToActivity(this, AboutActivity.class);
    }
    
    public void goHowtoActivity(View view) {
        ActivityHelper.goToActivity(this, HowtoActivity.class);
    }
    
    public void goSettingsActivity(View view) {
        ActivityHelper.goToActivity(this, SettingsActivity.class);
    }
    
    public void exit(View view) {
        System.exit(0);
    }
}
