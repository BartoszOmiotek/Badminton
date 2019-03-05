package com.example.bartek.badminton.Utils;

import android.content.Context;
import android.content.Intent;

public class ShowActivity {
    public static void show(Context context,Class activityClass){
        Intent intent=new Intent(context, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
