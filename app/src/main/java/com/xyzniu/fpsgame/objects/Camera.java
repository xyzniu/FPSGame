package com.xyzniu.fpsgame.objects;

import android.util.Log;
import com.xyzniu.fpsgame.config.Constants;

import static android.opengl.Matrix.*;
import static com.xyzniu.fpsgame.objects.Geometry.distanceBetween;
import static com.xyzniu.fpsgame.objects.PlayerManager.*;
import static com.xyzniu.fpsgame.renderer.Renderer.delta;

public class Camera {
    
    private volatile float[] viewMatrix = new float[16];
    private Geometry.Vector position;
    private Geometry.Vector direction;
    private final Geometry.Vector UP = new Geometry.Vector(0, 1, 0);
    private float rotation = 0;
    private Geometry.Vector endPoint;
    
    public Camera() {
        position = new Geometry.Vector(0, -0.3f, -6f);
        direction = new Geometry.Vector(0, 0, 1);
    }
    
    public void setStartPoint(Geometry.Vector startPoint) {
        this.position = new Geometry.Vector(startPoint);
    }
    
    public void setEndPoint(Geometry.Vector endPoint) {
        this.endPoint = endPoint;
    }
    
    public Geometry.Vector getEndPoint() {
        return endPoint;
    }
    
    public Geometry.Vector getPosition() {
        return position;
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
        direction.scale(Constants.STEP_LENGTH * delta);
        Geometry.Vector newPosition = Geometry.Vector.add(position, direction);
        if (!HitDetection.hitWallDetection(newPosition)) {
            position = newPosition;
        }
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
    }
    
    public void dragCamera(float deltaX) {
        rotation += deltaX / 16f;
        double radians = Math.toRadians(-rotation);
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
    
    
}