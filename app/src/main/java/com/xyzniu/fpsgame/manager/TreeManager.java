package com.xyzniu.fpsgame.manager;

import android.content.Context;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.pojo.Tree;
import com.xyzniu.fpsgame.util.Matrix;
import com.xyzniu.fpsgame.util.Model;
import com.xyzniu.fpsgame.util.TextureManager;

import static com.xyzniu.fpsgame.config.Constants.*;

public class TreeManager {
    
    private Tree tree1;
    private Tree tree2;
    private Tree tree3;
    private Matrix matrix = new Matrix();
    
    public TreeManager(Context context) {
        tree1 = new Tree(new Model(context, R.raw.tree1), TextureManager.tree1Texture, matrix,
                -0.5f, 0.2f, 0.2f, 0.2f);
        tree2 = new Tree(new Model(context, R.raw.tree2), TextureManager.tree2Texture, matrix,
                0.25f, 0.02f, 0.02f, 0.02f);
        tree3 = new Tree(new Model(context, R.raw.tree3), TextureManager.tree3Texture, matrix,
                0f, 0.05f, 0.05f, 0.05f);
    }
    
    public void draw(int treeType, int startX, int startZ) {
        switch (treeType) {
            case TREE_1:
                tree1.draw(startX, startZ);
                break;
            case TREE_2:
                tree2.draw(startX, startZ);
                break;
            case TREE_3:
                tree3.draw(startX, startZ);
                break;
        }
    }
    
    
}
