package com.xyzniu.fpsgame.programs;

import android.content.Context;

public class ShaderProgramManager {
    
    public static MainShaderProgram mainShaderProgram;
    
    public static void init(Context context) {
        mainShaderProgram = new MainShaderProgram(context);
    }
    
}
