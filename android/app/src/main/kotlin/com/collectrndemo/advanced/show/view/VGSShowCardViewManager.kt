package com.collectrndemo.advanced.show.view

import android.widget.LinearLayout
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager

class VGSShowCardViewManager : ViewGroupManager<LinearLayout>() {

    override fun getName(): String {
        return "VGSShowCardView"
    }

    override fun createViewInstance(context: ThemedReactContext): LinearLayout {
        return VGSShowCardView(context)
    }
}
