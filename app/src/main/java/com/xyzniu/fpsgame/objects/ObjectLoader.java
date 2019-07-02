package com.xyzniu.fpsgame.objects;
import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * FROM: https://stackoverflow.com/questions/41012719/how-to-load-and-display-obj-file-in-android-with-opengl-es-2
 */
public final class ObjectLoader {
    
    public final int numFaces;
    
    public final float[] normals;
    public final float[] textureCoordinates;
    public final float[] positions;
    
    public ObjectLoader(Context context, int file) {
        
        Vector<Float> vertices = new Vector<>();
        Vector<Float> normals = new Vector<>();
        Vector<Float> textures = new Vector<>();
        Vector<String> faces = new Vector<>();
        
        BufferedReader reader = null;
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(file));
            //new InputStreamReader(context.getAssets().open(file));
            reader = new BufferedReader(in);
            
            // read file until EOF
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                switch (parts[0]) {
                    case "v":
                        // vertices
                        vertices.add(Float.valueOf(parts[1]));
                        vertices.add(Float.valueOf(parts[2]));
                        vertices.add(Float.valueOf(parts[3]));
                        break;
                    case "vt":
                        // textures
                        textures.add(Float.valueOf(parts[1]));
                        textures.add(Float.valueOf(parts[2]));
                        break;
                    case "vn":
                        // normals
                        normals.add(Float.valueOf(parts[1]));
                        normals.add(Float.valueOf(parts[2]));
                        normals.add(Float.valueOf(parts[3]));
                        break;
                    case "f":
                        // faces: vertex/texture/normal
                        faces.add(parts[1]);
                        faces.add(parts[2]);
                        faces.add(parts[3]);
                        break;
                }
            }
        } catch (IOException e) {
            // cannot load or read file
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        
        numFaces = faces.size();
        this.normals = new float[numFaces * 3];
        textureCoordinates = new float[numFaces * 2];
        positions = new float[numFaces * 3];
        int positionIndex = 0;
        int normalIndex = 0;
        int textureIndex = 0;
        for (String face : faces) {
            String[] parts = face.split("/");
            
            int index = 3 * (Integer.valueOf(parts[0]) - 1);
            positions[positionIndex++] = vertices.get(index++);
            positions[positionIndex++] = vertices.get(index++);
            positions[positionIndex++] = vertices.get(index);
            
            index = 2 * (Integer.valueOf(parts[1]) - 1);
            textureCoordinates[textureIndex++] = textures.get(index++);
            // NOTE: Bitmap gets y-inverted
            // textureCoordinates[textureIndex++] = textures.get(index);
            textureCoordinates[textureIndex++] = 1 - textures.get(index);
            
            index = 3 * (Integer.valueOf(parts[2]) - 1);
            this.normals[normalIndex++] = normals.get(index++);
            this.normals[normalIndex++] = normals.get(index++);
            this.normals[normalIndex++] = normals.get(index);
        }
    }
}
