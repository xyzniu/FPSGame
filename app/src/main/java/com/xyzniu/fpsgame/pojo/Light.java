package com.xyzniu.fpsgame.pojo;

import android.util.Log;

public class Light {
    
    private static Light light = new Light();
    private Camera camera;
    private final Geometry.Vector lightToCameraPosition = new Geometry.Vector(0, 2, 2);
    
    private Light() {
        camera = Camera.getCamera();
    }
    
    public static Light getLight() {
        return light;
    }
    
    public float[] getVectorToLight(Geometry.Vector position) {
        Geometry.Vector cameraPosition = camera.getPosition();
        Geometry.Vector vectorToLight = new Geometry.Vector(cameraPosition);
        vectorToLight.add(lightToCameraPosition);
        vectorToLight.sub(position);
        return vectorToLight.toArray4();
    }
    
    public float[] getPointLightPosition() {
        Geometry.Vector lightPosition = new Geometry.Vector(camera.getPosition());
        lightPosition.add(lightToCameraPosition);
        // Log.w("LightPosition: ", lightPosition.toString());
        return lightPosition.toArray4();
    }
    
}
