package com.xyzniu.fpsgame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.xyzniu.fpsgame.R;

public class AboutActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
    
    public void goToHomePage(View view) {
        Intent home = new Intent();
        home.setClass(this, MainActivity.class);
        startActivity(home);
        this.finish();
    }
}
