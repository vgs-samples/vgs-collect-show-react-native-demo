package com.collectrndemo.modules.util;

import android.util.DisplayMetrics;
import android.content.Context;
import android.util.TypedValue;

public class ResourceUtil {

    public static int convertPxToDp(Context context, int dip) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, displayMetrics);
    }

}