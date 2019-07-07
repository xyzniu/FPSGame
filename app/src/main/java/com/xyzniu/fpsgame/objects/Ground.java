package com.xyzniu.fpsgame.objects;

import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.data.BasicShape;
import com.xyzniu.fpsgame.data.VertexArray;
import com.xyzniu.fpsgame.data.VertexData;
import com.xyzniu.fpsgame.programs.EndPointShaderProgram;
import com.xyzniu.fpsgame.programs.MainShaderProgram;
import com.xyzniu.fpsgame.programs.ShaderProgramManager;
import com.xyzniu.fpsgame.util.TextResourceReader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import static android.opengl.GLES20.glDepthMask;
import static android.opengl.Matrix.*;
import static com.xyzniu.fpsgame.util.Constants.*;

public class Ground {
    private static int[][] materials;
    private MainShaderProgram mainShaderProgram;
    private EndPointShaderProgram endPointShaderProgram;
    private BasicShape square;
    private BasicShape cube;
    private Light light = Light.getLight();
    private Camera camera = Camera.getCamera();
    private Matrix matrix = new Matrix();
    private List<Geometry.Vector> mobSpawner = new ArrayList<>();
    private Object house;
    
    public Ground(Context context, int resourceId) {
        String map = TextResourceReader.readTextFileFromResource(context, resourceId);
        map = map.replace("\n", "");
        String[] materials = map.split(",");
        int width = Integer.valueOf(materials[0]);
        int height = Integer.valueOf(materials[1]);
        this.materials = new int[height][width];
        int indexX = 0;
        int indexZ = 0;
        for (int i = 2; i < materials.length; i++) {
            this.materials[indexZ][indexX++] = Integer.valueOf(materials[i]);
            if (indexX >= width) {
                indexX = 0;
                indexZ++;
            }
        }
        mainShaderProgram = ShaderProgramManager.mainShaderProgram;
        endPointShaderProgram = ShaderProgramManager.endPointShaderProgram;
        square = new BasicShape(new VertexArray(VertexData.squareVertexData), 6);
        cube = new BasicShape(new VertexArray(VertexData.cubeWithoutUpAndDownSideVertexData), 24);
        house = new Object(context, R.raw.house);
        
        init();
        
    }
    
    private void init() {
        for (int i = 0; i < materials.length; i++) {
            for (int j = 0; j < materials[0].length; j++) {
                switch (materials[i][j]) {
                    case START_POINT:
                        camera.init();
                        camera.setStartPoint(new Geometry.Vector(j, -0.3f, i));
                        break;
                    case END_POINT:
                        camera.setEndPoint(computeVector(i, j));
                        break;
                    case MOB_SPAWNER:
                        mobSpawner.add(computeVector(i, j));
                        break;
                }
            }
        }
    }
    
    private Geometry.Vector computeVector(int i, int j) {
        return new Geometry.Vector(j, 0, i);
    }
    
    public List<Geometry.Vector> getMobSpawner() {
        return mobSpawner;
    }
    
    public void drawGround() {
        mainShaderProgram.useProgram();
        glDepthMask(true);
        // draw the opaque objects first
        int startX = 0;
        int startZ = 0;
        for (int i = 0; i < materials.length; i++) {
            for (int j = 0; j < materials[0].length; j++) {
                startX = j;
                startZ = i;
                switch (materials[i][j]) {
                    case GRASS:
                        drawGround(startX, startZ, TextureManager.grassTexture);
                        break;
                    case SOIL:
                        drawGround(startX, startZ, TextureManager.soilTexture);
                        break;
                    case WALL:
                        drawWall(startX, startZ, TextureManager.wallTexture);
                        break;
                    case HOUSE:
                        drawHouse(startX, startZ);
                        drawGround(startX, startZ, TextureManager.grassTexture);
                        break;
                    default:
                        drawGround(startX, startZ, TextureManager.grassTexture);
                        break;
                }
            }
        }
        // draw the transparent objects
        glDepthMask(false);
        drawEndPoint();
        glDepthMask(true);
    }
    
    private void drawHouse(int startX, int startZ) {
        house.bindData(mainShaderProgram);
        setIdentityM(matrix.modelMatrix, 0);
        translateM(matrix.modelMatrix, 0, startX, -0.5f, startZ + 0.2f);
        scaleM(matrix.modelMatrix, 0, 0.01f, 0.015f, 0.015f);
        rotateM(matrix.modelMatrix, 0, 90, -1, 0, 0);
        matrix.updateMatrix();
        
        mainShaderProgram.setUniforms(matrix.modelMatrix,
                matrix.it_modelMatrix,
                matrix.modelViewProjectionMatrix,
                light.getLightPosition(),
                light.getLightColor(),
                camera.getPositionVec3(),
                TextureManager.houseTexture);
        house.draw();
    }
    
    private void drawEndPoint() {
        int startX = (int) camera.getEndPoint().getX();
        int startZ = (int) camera.getEndPoint().getZ();
        endPointShaderProgram.useProgram();
        cube.bindData(endPointShaderProgram);
        for (int startY = 0; startY < 5; startY++) {
            setIdentityM(matrix.modelMatrix, 0);
            translateM(matrix.modelMatrix, 0, startX, startY, startZ);
            scaleM(matrix.modelMatrix, 0, 0.9f, 1f, 0.9f);
            matrix.updateMatrix();
            endPointShaderProgram.setUniforms(matrix.modelViewProjectionMatrix);
            cube.draw();
        }
    }
    
    private void drawGround(int startX, int startZ, int texture) {
        square.bindData(mainShaderProgram);
        draw(startX, 0, startZ, texture);
        square.draw();
    }
    
    private void drawWall(int startX, int startZ, int texture) {
        cube.bindData(mainShaderProgram);
        for (int i = 0; i < 5; i++) {
            draw(startX, i, startZ, texture);
            cube.draw();
        }
    }
    
    private void draw(int startX, int startY, int startZ, int texture) {
        setIdentityM(matrix.modelMatrix, 0);
        translateM(matrix.modelMatrix, 0, startX, startY, startZ);
        matrix.updateMatrix();
        
        mainShaderProgram.setUniforms(matrix.modelMatrix,
                matrix.it_modelMatrix,
                matrix.modelViewProjectionMatrix,
                light.getLightPosition(),
                light.getLightColor(),
                camera.getPositionVec3(),
                texture);
    }
    
    public static boolean hitWallDetection(Geometry.Vector position) {
        int x1 = Math.round(position.getX() + 0.1f);
        int x2 = Math.round(position.getX() - 0.1f);
        int z1 = Math.round(position.getZ() + 0.1f);
        int z2 = Math.round(position.getZ() - 0.1f);
        return isWall(x1, z1) || isWall(x1, z2) || isWall(x2, z1) || isWall(x2, z2);
    }
    
    private static boolean isWall(int x, int z) {
        if (z < 0 || z >= materials.length || x < 0 || x >= materials[0].length) {
            return true;
        }
        switch (materials[z][x]) {
            case WALL:
            case HOUSE:
                return true;
            default:
                return false;
        }
    }
    
    
}
