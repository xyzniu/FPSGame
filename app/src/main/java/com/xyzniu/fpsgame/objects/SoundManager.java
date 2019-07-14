package com.xyzniu.fpsgame.objects;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import com.xyzniu.fpsgame.R;

import java.util.HashMap;

public class SoundManager {
    
    public static SoundPool soundPool;
    public static HashMap<Integer, Integer> soundMap;
    public static final int SHOOT_SOUND = 0;
    public static final int SCREAM_SOUND = 1;
    public static final int CRUNCH_SOUND = 2;
    
    
    @Deprecated
    public SoundManager(Context context) {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        soundMap = new HashMap<>();
        soundMap.put(SHOOT_SOUND, soundPool.load(context, R.raw.shoot_sound, 1));
        soundMap.put(SCREAM_SOUND, soundPool.load(context, R.raw.male_scream, 1));
        soundMap.put(CRUNCH_SOUND, soundPool.load(context, R.raw.crunch, 1));
    }
}
