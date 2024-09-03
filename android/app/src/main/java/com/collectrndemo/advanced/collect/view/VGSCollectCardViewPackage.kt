package com.collectrndemo.advanced.collect.view

import android.view.View
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager

class VGSCollectCardViewPackage : ReactPackage {

    override fun createNativeModules(
        context: ReactApplicationContext
    ): MutableList<NativeModule> = mutableListOf(VGSCollectCardViewModule(context))

    override fun createViewManagers(
        context: ReactApplicationContext
    ): MutableList<ViewManager<out View, out ReactShadowNode<*>>> =
        mutableListOf(VGSCollectCardViewManager())
}