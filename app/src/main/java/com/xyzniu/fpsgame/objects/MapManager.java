package com.xyzniu.fpsgame.objects;

import android.content.Context;
import android.util.Log;
import com.xyzniu.fpsgame.R;
import com.xyzniu.fpsgame.util.TextResourceReader;

import java.util.ArrayList;
import java.util.List;

public class MapManager {
    private static final String TAG = "MapManager";
    
    private static boolean load = false;
    private static List<Integer> mapManager;
    
    public static void loadMap(Context context) {
        mapManager = new ArrayList<>();
        String mapFile = TextResourceReader.readTextFileFromResource(context, R.raw.map_all);
        String[] maps = mapFile.replace("\n", "").split(",");
        for (String map : maps) {
            int resourceId = context.getResources().getIdentifier(map, "raw", context.getPackageName());
            if (resourceId != 0) {
                mapManager.add(resourceId);
            }
        }
        load = true;
    }
    
    
    public static boolean isLoad() {
        return load;
    }
    
    public static int getResourceId(int count) {
        return mapManager.get(count);
    }
    
    public static int getSize() {
        return mapManager.size();
    }
}
