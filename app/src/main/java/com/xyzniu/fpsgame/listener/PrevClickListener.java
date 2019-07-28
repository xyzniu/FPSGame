package com.xyzniu.fpsgame.listener;

import android.view.View;
import android.widget.ImageView;

import static com.xyzniu.fpsgame.activity.HowtoActivity.imgs;
import static com.xyzniu.fpsgame.activity.HowtoActivity.num;

public class PrevClickListener implements View.OnClickListener {
    
    private ImageView imageView;
    
    public PrevClickListener(ImageView imageView) {
        this.imageView = imageView;
    }
    
    @Override
    public void onClick(View v) {
        num--;
        if (num < 0) {
            num = imgs.length - 1;
        }
        imageView.setImageResource(imgs[num]);
    }
}
