package com.xyzniu.fpsgame.manager;

import android.content.Context;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.pojo.Animal;
import com.xyzniu.fpsgame.programs.MainShaderProgram;
import com.xyzniu.fpsgame.programs.ShaderProgramManager;
import com.xyzniu.fpsgame.util.Geometry;
import com.xyzniu.fpsgame.util.Matrix;
import com.xyzniu.fpsgame.util.Model;
import com.xyzniu.fpsgame.util.TextureManager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static android.opengl.Matrix.*;
import static com.xyzniu.fpsgame.util.Geometry.distanceBetween;

public class AnimalManager {
    
    private Model animalModel;
    private Model arrowModel;
    private Matrix matrix = new Matrix();
    private int arrowTexture;
    private List<Animal> animals;
    private MainShaderProgram program;
    private int collected;
    private static double atan2 = Math.atan2(1, 0);
    
    public AnimalManager(Context context) {
        animalModel = new Model(context, R.raw.chick);
        arrowModel = new Model(context, R.raw.arrow);
        arrowTexture = TextureManager.arrowTexture;
        animals = new LinkedList<>();
        program = ShaderProgramManager.mainShaderProgram;
        collected = 0;
    }
    
    public void drawAnimals() {
        Geometry.Vector uPosition = PlayerManager.getPosition();
        
        program.useProgram();
        Iterator<Animal> it = animals.iterator();
        while (it.hasNext()) {
            Animal animal = it.next();
            
            if (!animal.isCollected() && distanceBetween(uPosition, animal.getPosition()) < 0.5) {
                animal.collect();
                collected++;
            }
            
            if (!animal.isCollected()) {
                drawAnimal(animal);
            }
        }
        
    }
    
    private void drawAnimal(Animal animal) {
        animalModel.bindData(program);
        Geometry.Vector position = animal.getPosition();
        setIdentityM(matrix.modelMatrix, 0);
        translateM(matrix.modelMatrix, 0, position.getX(), -0.5f, position.getZ());
        rotateM(matrix.modelMatrix, 0, getRotation(position, PlayerManager.getPosition()) + 90, 0f, 1f, 0f);
        scaleM(matrix.modelMatrix, 0, 0.6f, 0.6f, 0.6f);
        matrix.updateMatrix();
        
        program.setUniforms(matrix.modelMatrix,
                matrix.it_modelMatrix,
                matrix.modelViewProjectionMatrix,
                TextureManager.chickTexture);
        
        animalModel.draw();
        
        
        arrowModel.bindData(program);
        
        setIdentityM(matrix.modelMatrix, 0);
        translateM(matrix.modelMatrix, 0, position.getX(), animal.getTransY(), position.getZ());
        rotateM(matrix.modelMatrix, 0, animal.getRotateY(), 0f, 1f, 0f);
        scaleM(matrix.modelMatrix, 0, 0.2f, 0.2f, 0.2f);
        matrix.updateMatrix();
        
        program.setUniforms(matrix.modelMatrix,
                matrix.it_modelMatrix,
                matrix.modelViewProjectionMatrix,
                arrowTexture);
        arrowModel.draw();
    }
    
    public void addAnimal(Geometry.Vector position) {
        Animal animal = new Animal(position);
        animals.add(animal);
    }
    
    public boolean hasCollectedAll() {
        return collected >= animals.size();
    }
    
    private float getRotation(Geometry.Vector position, Geometry.Vector uPosition) {
        Geometry.Vector direction = Geometry.Vector.sub(uPosition, position);
        double radian = (atan2 - Math.atan2(direction.getZ(), direction.getX()));
        return (float) Math.toDegrees(radian);
    }
    
}
