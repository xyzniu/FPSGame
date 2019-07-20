package com.xyzniu.fpsgame.pojo;

import com.xyzniu.fpsgame.util.Model;
import com.xyzniu.fpsgame.programs.MainShaderProgram;
import com.xyzniu.fpsgame.programs.ShaderProgramManager;
import com.xyzniu.fpsgame.util.Matrix;

import static android.opengl.Matrix.*;

public class Tree {
    
    private Model model;
    private int texture;
    private float translateY;
    private float scaleX;
    private float scaleY;
    private float scaleZ;
    private Matrix matrix;
    
    public Tree(Model model, int texture, Matrix matrix,
                float translateY, float scaleX, float scaleY, float scaleZ) {
        this.model = model;
        this.texture = texture;
        this.matrix = matrix;
        this.translateY = translateY;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
    }
    
    public void draw(int startX, int startZ) {
        MainShaderProgram program = ShaderProgramManager.mainShaderProgram;
        model.bindData(program);
        program.useProgram();
        setIdentityM(matrix.modelMatrix, 0);
        translateM(matrix.modelMatrix, 0, startX, translateY, startZ);
        scaleM(matrix.modelMatrix, 0, scaleX, scaleY, scaleZ);
        
        matrix.updateMatrix();
        
        program.setUniforms(matrix.modelMatrix,
                matrix.it_modelMatrix,
                matrix.modelViewProjectionMatrix,
                texture);
        
        model.draw();
    }
}
