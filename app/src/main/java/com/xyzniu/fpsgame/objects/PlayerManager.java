package com.xyzniu.fpsgame.objects;

public class PlayerManager {
    
    private static Player player;
    private static Camera camera;
    
    public static volatile boolean isMovingForward = false;
    public static volatile boolean isMovingBackward = false;
    public static volatile boolean isMovingLeft = false;
    public static volatile boolean isMovingRight = false;
    
    public static void setPlayer(Player player, Camera camera) {
        PlayerManager.player = player;
        PlayerManager.camera = camera;
    }
    
    public static void hitPlayer() {
        player.hit();
    }
    
    public static void killEnemy() {
        player.killEnemy();
    }
    
    
    public static Geometry.Vector getPosition() {
        return camera.getPosition();
    }
    
    public static float[] getPositionVec3() {
        return camera.getPosition().toArray3();
    }
    
    public static Geometry.Vector getDirection() {
        return camera.getDirection();
    }
    
    public static void dragCamera(float deltaX) {
        camera.dragCamera(deltaX);
    }
    
    public static float[] getLightColor() {
        return new float[]{1.0f, 1.0f, 1.0f};
    }
    
    public static float[] getViewMatrix() {
        return camera.getViewMatrix();
    }
    
    public static void updateCamera() {
        camera.updateCamera();
    }
    
    public static boolean atEndPoint() {
        return camera.atEndPoint();
    }
    
    public static void setEndPoint(Geometry.Vector endPoint) {
        camera.setEndPoint(endPoint);
    }
    
    public static void setStartPoint(Geometry.Vector startPoint) {
        camera.setStartPoint(startPoint);
    }
}