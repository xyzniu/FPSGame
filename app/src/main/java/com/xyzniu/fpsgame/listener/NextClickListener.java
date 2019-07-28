package com.xyzniu.fpsgame.listener;

import android.view.View;
import android.widget.ImageView;

import static com.xyzniu.fpsgame.activity.HowtoActivity.imgs;
import static com.xyzniu.fpsgame.activity.HowtoActivity.num;

public class NextClickListener implements View.OnClickListener {
    
    private ImageView imageView;
    
    public NextClickListener(ImageView imageView) {
        this.imageView = imageView;
    }
    
    @Override
    public void onClick(View v) {
        num++;
        if (num >= imgs.length) {
            num = 0;
        }
        imageView.setImageResource(imgs[num]);
    }
}
