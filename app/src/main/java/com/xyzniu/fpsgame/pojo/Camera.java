package com.xyzniu.fpsgame.pojo;

import com.xyzniu.fpsgame.util.Constants;
import com.xyzniu.fpsgame.util.MatrixHelper;

import static android.opengl.Matrix.setIdentityM;

public class Camera {
    
    private static Camera camera = new Camera();
    public volatile boolean isMovingForward = false;
    public volatile boolean isMovingBackward = false;
    public volatile boolean isMovingLeft = false;
    public volatile boolean isMovingRight = false;
    
    public static Camera getCamera() {
        return camera;
    }
    
    private volatile float[] viewMatrix = new float[16];
    private Geometry.Vector position;
    private Geometry.Vector direction;
    private final Geometry.Vector UP = new Geometry.Vector(0, 1, 0);
    
    
    private Camera() {
        init();
    }
    
    public void init() {
        position = new Geometry.Vector(0, -1.5f, -6f);
        direction = new Geometry.Vector(0, 0, 1);
        updateViewMatrix();
    }
    
    private void updateViewMatrix() {
        setIdentityM(viewMatrix, 0);
        MatrixHelper.translateMatrix(viewMatrix, 0, position);
    }
    
    public float[] getViewMatrix() {
        return viewMatrix;
    }
    
    private void moveForward() {
        movePosition(direction);
    }
    
    private void moveBackward() {
        Geometry.Vector reverse = direction.reverse();
        movePosition(reverse);
    }
    
    private void moveLeft() {
        Geometry.Vector right = getRightDirection();
        Geometry.Vector left = right.reverse();
        movePosition(left);
    }
    
    private void moveRight() {
        Geometry.Vector right = getRightDirection();
        movePosition(right);
    }
    
    private void movePosition(Geometry.Vector direction) {
        direction.normalize();
        direction.scale(Constants.STEP_LENGTH);
        position.add(direction);
    }
    
    private Geometry.Vector getRightDirection() {
        return direction.crossProduct(UP);
    }
    
    
    public void updateCamera() {
        if (isMovingForward) {
            moveForward();
        }
        if (isMovingBackward) {
            moveBackward();
        }
        if (isMovingLeft) {
            moveLeft();
        }
        if (isMovingRight) {
            moveRight();
        }
        updateViewMatrix();
    }
}
