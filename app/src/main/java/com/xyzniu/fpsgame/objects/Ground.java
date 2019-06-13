package com.xyzniu.fpsgame.objects;

import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.pojo.Camera;
import com.xyzniu.fpsgame.pojo.Light;
import com.xyzniu.fpsgame.programs.MainShaderProgram;
import com.xyzniu.fpsgame.programs.ShaderProgramManager;
import com.xyzniu.fpsgame.util.TextResourceReader;
import android.content.Context;
import com.xyzniu.fpsgame.util.TextureHelper;

import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

public class Ground {
    private int[] materials;
    private int width;
    private int height;
    private int grassTexture;
    private int soilTexture;
    private MainShaderProgram program;
    private Grass grass;
    private Light light = Light.getLight();
    private Camera camera = Camera.getCamera();
    private Matrix matrix = new Matrix();
    
    public Ground(Context context, int resourceId) {
        String map = TextResourceReader.readTextFileFromResource(context, resourceId);
        map = map.replace("\n", "");
        String[] materials = map.split(",");
        width = Integer.valueOf(materials[0]);
        height = Integer.valueOf(materials[1]);
        this.materials = new int[materials.length - 2];
        for (int i = 2; i < materials.length; i++) {
            this.materials[i - 2] = Integer.valueOf(materials[i]);
        }
        grassTexture = TextureHelper.loadTexture(context, R.drawable.grass);
        soilTexture = TextureHelper.loadTexture(context, R.drawable.soil);
        program = ShaderProgramManager.mainShaderProgram;
        grass = new Grass(context);
    }
    
    public void drawGround() {
        int startX = -1 * height;
        int startZ = -1 * width;
        
        program.useProgram();
        grass.bindData(program);
        int index = 0;
        int texture = 0;
        
        while (startX < height) {
            startZ = -1 * width;
            while (startZ < width) {
                switch (materials[index++]) {
                    case 1:
                        texture = grassTexture;
                        break;
                    case 2:
                        texture = soilTexture;
                        break;
                }
                setIdentityM(matrix.modelMatrix, 0);
                translateM(matrix.modelMatrix, 0, startX, 0, startZ);
                matrix.updateMatrix();
                
                program.setUniforms(matrix.modelMatrix,
                        matrix.it_modelMatrix,
                        matrix.modelViewProjectionMatrix,
                        light.getLightPosition(),
                        light.getLightColor(),
                        camera.getPositionVec3(),
                        texture);
                
                grass.draw();
                startZ += 2;
            }
            startX += 2;
        }
    }
    
}
