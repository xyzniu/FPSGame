package com.xyzniu.fpsgame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.xyzniu.fpsgame.R;

public class HowtoActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howto);
    }
    
    public void goMainActivity(View view) {
        ActivityHelper.goToActivity(this, MainActivity.class);
    }
}
