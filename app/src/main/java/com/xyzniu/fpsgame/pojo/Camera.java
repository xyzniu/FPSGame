package com.xyzniu.fpsgame.pojo;

import com.xyzniu.fpsgame.config.Constants;
import com.xyzniu.fpsgame.manager.Ground;
import com.xyzniu.fpsgame.util.Geometry;

import static android.opengl.Matrix.setLookAtM;
import static com.xyzniu.fpsgame.config.Configuration.FLIP_HORIZONTAL;
import static com.xyzniu.fpsgame.util.Geometry.distanceBetween;
import static com.xyzniu.fpsgame.manager.PlayerManager.*;
import static com.xyzniu.fpsgame.manager.PlayerManager.isMovingRight;
import static com.xyzniu.fpsgame.renderer.GameRenderer.delta;

public class Camera {
    
    private volatile float[] viewMatrix = new float[16];
    private Geometry.Vector position;
    private volatile Geometry.Vector direction;
    private final Geometry.Vector UP = new Geometry.Vector(0, 1, 0);
    private float rotation = 0;
    private Geometry.Vector endPoint;
    private float stepLength;
    
    public Camera() {
        position = new Geometry.Vector(0, -0.3f, 0f);
        direction = new Geometry.Vector(0, 0, 1);
        stepLength = Constants.STEP_LENGTH_NORMAL;
    }
    
    public void setStartPoint(Geometry.Vector startPoint) {
        this.position = new Geometry.Vector(startPoint);
    }
    
    public void setEndPoint(Geometry.Vector endPoint) {
        this.endPoint = endPoint;
    }
    
    public Geometry.Vector getPosition() {
        return position;
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
        Ground.swampDetection(position);
        direction.normalize();
        direction.scale(stepLength * delta);
        Geometry.Vector newPosition = Geometry.Vector.add(position, direction);
        if (!Ground.hitDetection(newPosition)) {
            position = newPosition;
        }
    }
    
    private Geometry.Vector getRightDirection() {
        return direction.crossProduct(UP);
    }
    
    
    public void dragCamera(float deltaX) {
        rotation += deltaX / 16f;
        double radians = Math.toRadians(rotation);
        if (FLIP_HORIZONTAL) {
            radians = -radians;
        }
        direction = new Geometry.Vector(-(float) Math.sin(radians), 0, (float) Math.cos(radians));
    }
    
    public float[] getViewMatrix() {
        Geometry.Vector center = new Geometry.Vector(position);
        center.add(direction);
        setLookAtM(viewMatrix, 0,
                position.getX(), position.getY(), position.getZ(),
                center.getX(), center.getY(), center.getZ(),
                UP.getX(), UP.getY(), UP.getZ());
        return viewMatrix;
    }
    
    public Geometry.Vector getDirection() {
        return direction;
    }
    
    
    public boolean atEndPoint() {
        float distance = distanceBetween(endPoint, position);
        if (distance < 1) {
            return true;
        } else {
            return false;
        }
    }
    
    public void setStepLength(float stepLength) {
        this.stepLength = stepLength;
    }
}
