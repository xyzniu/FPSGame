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
    private Matrix matrix = new Matrix();
    private int animalTexture;
    private List<Animal> animals;
    private MainShaderProgram program;
    private int collected;
    
    public AnimalManager(Context context) {
        animalModel = new Model(context, R.raw.chick);
        animalTexture = TextureManager.chickTexture;
        animals = new LinkedList<>();
        program = ShaderProgramManager.mainShaderProgram;
        collected = 0;
    }
    
    public void draw() {
        Geometry.Vector uPosition = PlayerManager.getPosition();
        animalModel.bindData(program);
        program.useProgram();
        Iterator<Animal> it = animals.iterator();
        while (it.hasNext()) {
            Animal animal = it.next();
            
            if (distanceBetween(uPosition, animal.getPosition()) < 0.5) {
                animal.collect();
                collected++;
            }
            
            if (!animal.isCollected()) {
                drawAnimal(animal);
            }
        }
    }
    
    private void drawAnimal(Animal animal) {
        Geometry.Vector position = animal.getPosition();
        setIdentityM(matrix.modelMatrix, 0);
        translateM(matrix.modelMatrix, 0, position.getX(), -0.5f, position.getZ());
        scaleM(matrix.modelMatrix, 0, 0.5f, 0.5f, 0.5f);
        matrix.updateMatrix();
        
        program.setUniforms(matrix.modelMatrix,
                matrix.it_modelMatrix,
                matrix.modelViewProjectionMatrix,
                animalTexture);
        
        animalModel.draw();
    }
    
    public void addAnimal(Geometry.Vector position) {
        Animal animal = new Animal(position);
        animals.add(animal);
    }
    
    public boolean hasCollectedAll() {
        return collected >= animals.size();
    }
    
}
