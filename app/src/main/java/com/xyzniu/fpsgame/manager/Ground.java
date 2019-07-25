package com.xyzniu.fpsgame.manager;

import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.data.BasicShape;
import com.xyzniu.fpsgame.data.VertexArray;
import com.xyzniu.fpsgame.data.VertexData;
import com.xyzniu.fpsgame.programs.EndPointShaderProgram;
import com.xyzniu.fpsgame.programs.MainShaderProgram;
import com.xyzniu.fpsgame.programs.ShaderProgramManager;
import com.xyzniu.fpsgame.util.*;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import static android.opengl.GLES20.glDepthMask;
import static android.opengl.Matrix.*;
import static com.xyzniu.fpsgame.config.Constants.*;

public class Ground {
    
    private static final String TAG = "Ground";
    private static int[][] materials;
    private MainShaderProgram mainShaderProgram;
    private EndPointShaderProgram endPointShaderProgram;
    private BasicShape square;
    private BasicShape cube;
    private Matrix matrix = new Matrix();
    private List<Geometry.Vector> mobSpawner = new ArrayList<>();
    private Model house;
    private Geometry.Vector endPoint;
    private TreeManager treeManager;
    private AnimalManager animalManager;
    
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
        house = new Model(context, R.raw.house);
        
        treeManager = new TreeManager(context);
        animalManager = new AnimalManager(context);
        init();
        
    }
    
    private void init() {
        Geometry.Vector startPoint = new Geometry.Vector(0, -0.3f, 0);
        for (int i = 0; i < materials.length; i++) {
            for (int j = 0; j < materials[0].length; j++) {
                switch (materials[i][j]) {
                    case START_POINT:
                        startPoint = new Geometry.Vector(j, -0.3f, i);
                        break;
                    case END_POINT:
                        endPoint = computeVector(i, j);
                        break;
                    case MOB_SPAWNER:
                        mobSpawner.add(computeVector(i, j));
                        break;
                    case ANIMAL:
                        animalManager.addAnimal(computeVector(i, j));
                        break;
                }
            }
        }
        PlayerManager.setStartPoint(startPoint);
        PlayerManager.setEndPoint(endPoint);
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
        // draw the opaque first
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
                        // drawWall(startX, startZ, TextureManager.grassTexture);
                        break;
                    case HOUSE:
                        drawHouse(startX, startZ);
                        drawGround(startX, startZ, TextureManager.grassTexture);
                        break;
                    case TREE_1:
                        treeManager.draw(TREE_1, startX, startZ);
                        drawGround(startX, startZ, TextureManager.soilTexture);
                        break;
                    case TREE_2:
                        treeManager.draw(TREE_2, startX, startZ);
                        drawGround(startX, startZ, TextureManager.soilTexture);
                        break;
                    case TREE_3:
                        treeManager.draw(TREE_3, startX, startZ);
                        drawGround(startX, startZ, TextureManager.soilTexture);
                        break;
                    case NOTHING:
                        break;
                    default:
                        drawGround(startX, startZ, TextureManager.grassTexture);
                        break;
                }
            }
        }
        animalManager.draw();
        // draw the transparent
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
                TextureManager.houseTexture);
        house.draw();
    }
    
    private void drawEndPoint() {
        int startX = (int) endPoint.getX();
        int startZ = (int) endPoint.getZ();
        endPointShaderProgram.useProgram();
        cube.bindData(endPointShaderProgram);
        for (int startY = 0; startY < 5; startY++) {
            setIdentityM(matrix.modelMatrix, 0);
            translateM(matrix.modelMatrix, 0, startX, startY, startZ);
            scaleM(matrix.modelMatrix, 0, 0.9f, 1f, 0.9f);
            matrix.updateMatrix();
            endPointShaderProgram.setUniforms(matrix.modelViewProjectionMatrix, animalManager.hasCollectedAll());
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
                texture);
    }
    
    public static boolean hitDetection(Geometry.Vector position, float distance) {
        int x1 = Math.round(position.getX() + 0.1f);
        int x2 = Math.round(position.getX() - 0.1f);
        int z1 = Math.round(position.getZ() + 0.1f);
        int z2 = Math.round(position.getZ() - 0.1f);
        
        return detectWallOrTree(position, x1, z1, distance) || detectWallOrTree(position, x1, z2, distance)
                || detectWallOrTree(position, x2, z1, distance) || detectWallOrTree(position, x2, z2, distance);
        
    }
    
    private static boolean detectWallOrTree(Geometry.Vector position, int x, int z, float mDistance) {
        if (z < 0 || z >= materials.length || x < 0 || x >= materials[0].length) {
            return true;
        }
        
        switch (materials[z][x]) {
            case WALL:
            case HOUSE:
            case TREE_3:
            case NOTHING:
                return true;
            case TREE_1:
            case TREE_2:
                float xx = position.getX();
                float zz = position.getZ();
                
                double distance = Math.sqrt(Math.pow(xx - x, 2) + Math.pow(zz - z, 2));
                if (distance > mDistance) {
                    return false;
                } else {
                    return true;
                }
            default:
                return false;
        }
        
    }
    
    public static void swampDetection(Geometry.Vector position) {
        
        int x1 = Math.round(position.getX() + 0.1f);
        int x2 = Math.round(position.getX() - 0.1f);
        int z1 = Math.round(position.getZ() + 0.1f);
        int z2 = Math.round(position.getZ() - 0.1f);
        
        if (isSwamp(x1, z1) || isSwamp(x1, z2) || isSwamp(x2, z1) || isSwamp(x2, z2)) {
            PlayerManager.setStepLength(STEP_LENGTH_SLOW);
        } else {
            PlayerManager.setStepLength(STEP_LENGTH_NORMAL);
        }
        return;
    }
    
    private static boolean isSwamp(int x, int z) {
        if (z < 0 || z >= materials.length || x < 0 || x >= materials[0].length) {
            return true;
        }
        
        switch (materials[z][x]) {
            case SWAMP:
                return true;
            default:
                return false;
        }
    }
    
    
    public boolean openEndPoint() {
        return animalManager.hasCollectedAll();
    }
    
}
