package com.xyzniu.fpsgame.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import com.xyzniu.fpsgame.config.Configuration;
import com.xyzniu.fpsgame.R;

public class SettingsActivity extends Activity {
    
    private AudioManager audioManager;
    private int maxVolume;
    private int currentVolume;
    private CompoundButton soundEffectBtn;
    private SeekBar volumeBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumeBar = super.findViewById(R.id.volume_bar);
        
        initSoundEffect();
        initVolume();
        
    }
    
    private void initVolume() {
        volumeBar.setMax(maxVolume);
        volumeBar.setProgress(currentVolume);
        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                seekBar.setProgress(currentVolume);
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            
            }
            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            
            }
        });
        
    }
    
    private void initSoundEffect() {
        soundEffectBtn.setChecked(Configuration.openSoundEffect);
        soundEffectBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Configuration.openSoundEffect = isChecked;
            }
        });
    }
    
    public void goToHomePage(View view) {
        Intent home = new Intent();
        home.setClass(this, MainActivity.class);
        startActivity(home);
        this.finish();
    }
    
    
}
