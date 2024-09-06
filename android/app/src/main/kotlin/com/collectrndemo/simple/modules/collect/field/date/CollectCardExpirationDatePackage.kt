package com.collectrndemo.simple.modules.collect.field.date

import com.collectrndemo.simple.modules.OnCreateViewInstanceListener
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager
import com.verygoodsecurity.vgscollect.view.InputFieldView

class CollectCardExpirationDatePackage(
    private val listener: OnCreateViewInstanceListener<InputFieldView>
) : ReactPackage {

    override fun createNativeModules(
        reactContext: ReactApplicationContext
    ): List<NativeModule> = listOf<NativeModule>(CardExpirationDateModule(reactContext))

    override fun createViewManagers(
        reactContext: ReactApplicationContext
    ): List<ViewManager<*, *>> = listOf<ViewManager<*, *>>(CardExpirationDateManager(listener))
}
