package com.xyzniu.fpsgame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.listener.MapClickListener;
import com.xyzniu.fpsgame.objects.MapManager;

public class MapActivity extends Activity {
    private static final String TAG = "MapActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initMap();
        int mapSize = MapManager.getSize();
        MapClickListener mapListener = new MapClickListener(this);
        LinearLayout layout = findViewById(R.id.map_view);
        for (int i = 0; i < mapSize; i++) {
            Button button = new Button(this);
            button.setText("MAP" + i);
            button.setOnClickListener(mapListener);
            layout.addView(button);
        }
    }
    
    private void initMap() {
        if (!MapManager.isLoad()) {
            MapManager.loadMap(this);
        }
    }
    
    public void goMainActivity(View view) {
        ActivityHelper.goToActivity(this, MainActivity.class);
    }
}
