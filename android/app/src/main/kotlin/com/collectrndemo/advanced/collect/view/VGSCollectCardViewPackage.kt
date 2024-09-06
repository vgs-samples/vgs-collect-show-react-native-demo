package com.collectrndemo.advanced.collect.view

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager

class VGSCollectCardViewPackage : ReactPackage {

    override fun createNativeModules(
        context: ReactApplicationContext
    ): List<NativeModule> = listOf(VGSCollectCardViewModule(context))

    override fun createViewManagers(
        context: ReactApplicationContext
    ): List<ViewManager<*, *>> = listOf(VGSCollectCardViewManager())
}
