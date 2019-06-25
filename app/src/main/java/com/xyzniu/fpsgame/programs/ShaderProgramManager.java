package com.xyzniu.fpsgame.programs;

import android.content.Context;

public class ShaderProgramManager {
    
    public static MainShaderProgram mainShaderProgram;
    public static EndPointShaderProgram endPointShaderProgram;
    
    public ShaderProgramManager(Context context) {
        mainShaderProgram = new MainShaderProgram(context);
        endPointShaderProgram = new EndPointShaderProgram(context);
    }
    
}
