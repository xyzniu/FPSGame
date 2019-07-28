package com.xyzniu.fpsgame.util;

import android.content.Context;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.util.TextureHelper;

public class TextureManager {
    
    public static int grassTexture;
    public static int wallTexture;
    public static int soilTexture;
    
    public static int foxTexture;
    public static int houseTexture;
    public static int appleTexture;
    public static int rubyTexture;
    
    public static int tree1Texture;
    public static int tree2Texture;
    public static int tree3Texture;
    
    public static int chickTexture;
    public static int arrowTexture;
    
    
    public static void init(Context context) {
        grassTexture = TextureHelper.loadTexture(context, R.drawable.grass);
        soilTexture = TextureHelper.loadTexture(context, R.drawable.soil);
        wallTexture = TextureHelper.loadTexture(context, R.drawable.wall);
        
        foxTexture = TextureHelper.loadTexture(context, R.raw.foxuv);
        houseTexture = TextureHelper.loadTexture(context, R.raw.houseuv);
        appleTexture = TextureHelper.loadTexture(context, R.raw.appleuv);
        rubyTexture = TextureHelper.loadTexture(context, R.raw.rubyuv);
        
        tree1Texture = TextureHelper.loadTexture(context, R.raw.tree1uv);
        tree2Texture = TextureHelper.loadTexture(context, R.raw.tree2uv);
        tree3Texture = TextureHelper.loadTexture(context, R.raw.tree3uv);
        
        chickTexture = TextureHelper.loadTexture(context, R.raw.chickuv);
        arrowTexture = TextureHelper.loadTexture(context, R.raw.arrowuv);
    }
    
}



