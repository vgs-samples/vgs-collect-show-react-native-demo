package com.collectrndemo.advanced.show.view

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager

class VGSShowCardViewPackage : ReactPackage {

    override fun createNativeModules(
        context: ReactApplicationContext
    ): List<NativeModule> = listOf(VGSShowCardViewModule(context))

    override fun createViewManagers(
        context: ReactApplicationContext
    ): List<ViewManager<*, *>> = listOf(VGSShowCardViewManager())
}
