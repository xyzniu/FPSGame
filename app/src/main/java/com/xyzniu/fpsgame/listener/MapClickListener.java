package com.xyzniu.fpsgame.listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import com.xyzniu.fpsgame.activity.GameActivity;
import com.xyzniu.fpsgame.objects.MapManager;

public class MapClickListener implements View.OnClickListener {
    private static final String TAG = "MapClickListener";
    
    private Context context;
    
    public MapClickListener(Context context) {
        this.context = context;
    }
    
    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String text = String.valueOf(button.getText());
        int count = Integer.parseInt(text.replace("MAP", ""));
        int resourceId = MapManager.getResourceId(count);
        
        Intent game = new Intent();
        game.putExtra("mapResourceId", resourceId);
        game.setClass(context, GameActivity.class);
        context.startActivity(game);
        ((Activity) context).finish();
    }
}
