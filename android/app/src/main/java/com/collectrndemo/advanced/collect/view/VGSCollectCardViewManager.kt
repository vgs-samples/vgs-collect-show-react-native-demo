package com.collectrndemo.advanced.collect.view

import android.widget.LinearLayout
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager

class VGSCollectCardViewManager : ViewGroupManager<LinearLayout>() {

    override fun getName(): String {
        return "VGSCollectCardView"
    }

    override fun createViewInstance(context: ThemedReactContext): LinearLayout {
        return VGSCollectCardView(context)
    }
}