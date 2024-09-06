@file:JvmName("ResourceUtils")

package com.collectrndemo.simple.modules.util

import android.content.Context
import android.util.TypedValue

fun Context.convertPxToDp(dip: Int): Int {
    return TypedValue
        .applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip.toFloat(), resources.displayMetrics)
        .toInt()
}
