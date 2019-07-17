package com.xyzniu.fpsgame.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ActivityHelper {
    
    public static void goToActivity(Context context, Class<?> cls) {
        Intent activity = new Intent();
        activity.setClass(context, cls);
        context.startActivity(activity);
        ((Activity) context).finish();
    }
}
