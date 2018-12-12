package com.techradge.fabler.utils;

import android.content.Context;
import android.widget.Toast;

public class MyToast {

    public static void show(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
