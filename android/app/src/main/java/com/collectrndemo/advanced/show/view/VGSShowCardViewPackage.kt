package com.collectrndemo.advanced.show.view

import android.view.View
import com.collectrndemo.advanced.show.view.VGSShowCardViewManager
import com.collectrndemo.advanced.show.view.VGSShowCardViewModule
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager

class VGSShowCardViewPackage : ReactPackage {

    override fun createNativeModules(
        context: ReactApplicationContext
    ): MutableList<NativeModule> = mutableListOf(VGSShowCardViewModule(context))

    override fun createViewManagers(
        context: ReactApplicationContext
    ): MutableList<ViewManager<out View, out ReactShadowNode<*>>> =
        mutableListOf(VGSShowCardViewManager())
}