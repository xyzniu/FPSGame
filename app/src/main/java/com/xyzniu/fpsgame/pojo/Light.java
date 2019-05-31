package com.xyzniu.fpsgame.pojo;


import android.util.Log;

public class Light {
    
    private static Light light = new Light();
    private Camera camera;
    private final Geometry.Vector lightToCameraPosition = new Geometry.Vector(0, 0, 0);
    
    private Light() {
        camera = Camera.getCamera();
    }
    
    public static Light getLight() {
        return light;
    }
    
    
    public float[] getLightPosition() {
        Geometry.Vector lightPosition = new Geometry.Vector(camera.getPosition());
        lightPosition.add(lightToCameraPosition);
        Log.w("lightPosition", lightPosition.toString());
        return lightPosition.toArray3();
    }
    
    public float[] getLightColor() {
        return new float[]{1.0f, 1.0f, 1.0f};
    }
    
}
