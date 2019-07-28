package com.xyzniu.fpsgame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xyzniu.fpsgame.R;

public class ResultActivity extends Activity {
    
    public static final String WIN = "WIN";
    public static final String TIME = "TIME";
    public static final String KILL = "KILL";
    private static final String YOUWIN = "YOU WIN!";
    private static final String YOULOSE = "YOU LOSE!";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setContent();
    }
    
    private void setContent() {
        Intent prevActivity = getIntent();
        boolean win = prevActivity.getBooleanExtra(WIN, true);
        String time = prevActivity.getStringExtra(TIME);
        int kill = prevActivity.getIntExtra(KILL, 0);
        
        TextView winOrLoseView = findViewById(R.id.win_or_lose);
        TextView killView = findViewById(R.id.result_kill);
        TextView timeView = findViewById(R.id.result_time);
        ImageView awardView = findViewById(R.id.award);
        
        timeView.setText(time);
        killView.setText(String.format("Kill: %d", kill));
        if (win) {
            winOrLoseView.setText(YOUWIN);
            awardView.setImageResource(R.drawable.award);
        } else {
            winOrLoseView.setText(YOULOSE);
            awardView.setImageResource(R.drawable.skeleton);
        }
    }
    
    public void goMapActivity(View view) {
        ActivityHelper.goToActivity(this, MapActivity.class);
    }
}
