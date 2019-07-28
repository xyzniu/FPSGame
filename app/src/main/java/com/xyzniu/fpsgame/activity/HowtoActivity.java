package com.xyzniu.fpsgame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.listener.NextClickListener;
import com.xyzniu.fpsgame.listener.PrevClickListener;

public class HowtoActivity extends Activity {
    
    public static volatile int num = 0;
    
    public static final int[] imgs = new int[]{
            R.drawable.directions,
            R.drawable.drag,
            R.drawable.attack,
            R.drawable.collections,
            R.drawable.win
    };
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howto);
        ImageView imageView = findViewById(R.id.instruction);
        Button prevBtn = findViewById(R.id.prev_btn);
        Button nextBtn = findViewById(R.id.next_btn);
        prevBtn.setOnClickListener(new PrevClickListener(imageView));
        nextBtn.setOnClickListener(new NextClickListener(imageView));
    }
    
    public void goMainActivity(View view) {
        ActivityHelper.goToActivity(this, MainActivity.class);
    }
}
