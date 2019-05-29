package com.xyzniu.fpsgame.pojo;

public class Geometry {
    
    
    public static class Point {
        public final float x, y, z;
        
        public Point(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        public Point translateY(float distance) {
            return new Point(x, y + distance, z);
        }
        
        public Point translate(Vector vector) {
            return new Point(x + vector.x, y + vector.y, z + vector.z);
        }
    }
    
    public static class Vector {
        private float x, y, z;
        
        public Vector(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        public Vector(Vector other) {
            this.x = other.x;
            this.y = other.y;
            this.z = other.z;
        }
        
        public float length() {
            return (float) Math.sqrt(x * x + y * y + z * z);
        }
        
        public Vector crossProduct(Vector other) {
            return new Vector(
                    (y * other.z) - (z * other.y),
                    (z * other.x) - (x * other.z),
                    (x * other.y) - (y * other.x));
        }
        
        public float dotProduct(Vector other) {
            return x * other.x + y * other.y + z * other.z;
        }
        
        public void scale(float f) {
            x *= f;
            y *= f;
            z *= f;
        }
        
        public void normalize() {
            scale(1f / length());
        }
        
        public void add(Vector other) {
            x += other.x;
            y += other.y;
            z += other.z;
        }
        
        public void sub(Vector other) {
            x -= other.x;
            y -= other.y;
            z -= other.z;
        }
        
        public float getX() {
            return x;
        }
        
        public float getY() {
            return y;
        }
        
        public float getZ() {
            return z;
        }
        
        public Vector reverse() {
            return new Vector(-x, -y, -z);
            
        }
        
        public float[] toArray4() {
            float[] vector = new float[4];
            vector[0] = x;
            vector[1] = y;
            vector[2] = z;
            vector[3] = 0;
            return vector;
        }
        
        @Override
        public String toString() {
            return "(" + x + ":" + y + ":" + z + ")";
        }
    }
    
    public static Vector vectorBetween(Point from, Point to) {
        return new Vector(to.x - from.x, to.y - from.y, to.z - from.z);
    }
    
    
    public static class Ray {
        public final Point point;
        public final Vector vector;
        
        public Ray(Point point, Vector vector) {
            this.point = point;
            this.vector = vector;
        }
    }
    
    public static boolean intersects(Sphere sphere, Ray ray) {
        return distanceBetween(sphere.center, ray) < sphere.radius;
    }
    
    private static float distanceBetween(Point point, Ray ray) {
        Vector p1ToPoint = vectorBetween(ray.point, point);
        Vector p2ToPoint = vectorBetween(ray.point.translate(ray.vector), point);
        float areaOfTriangleTimesTwo = p1ToPoint.crossProduct(p2ToPoint).length();
        float lengthOfBase = ray.vector.length();
        float distanceFromPointToRay = areaOfTriangleTimesTwo / lengthOfBase;
        return distanceFromPointToRay;
    }
    
    public static class Sphere {
        public final Point center;
        public final float radius;
        
        public Sphere(Point center, float radius) {
            this.center = center;
            this.radius = radius;
        }
    }
}
