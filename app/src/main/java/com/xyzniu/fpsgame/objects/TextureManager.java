package com.xyzniu.fpsgame.objects;

import android.content.Context;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.util.TextureHelper;

public class TextureManager {
    
    public static int grassTexture;
    public static int wallTexture;
    public static int soilTexture;
    
    public static int foxTexture;
    public static int houseTexture;
    public static int bulletTexture;
    
    public TextureManager(Context context) {
        grassTexture = TextureHelper.loadTexture(context, R.drawable.grass);
        soilTexture = TextureHelper.loadTexture(context, R.drawable.soil);
        wallTexture = TextureHelper.loadTexture(context, R.drawable.wall);
        
        foxTexture = TextureHelper.loadTexture(context, R.raw.foxuv);
        houseTexture = TextureHelper.loadTexture(context, R.raw.houseuv);
        bulletTexture = TextureHelper.loadTexture(context, R.raw.bulletuv);
    }
}


