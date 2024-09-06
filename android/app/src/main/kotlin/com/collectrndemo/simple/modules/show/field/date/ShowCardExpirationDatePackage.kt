package com.collectrndemo.simple.modules.show.field.date

import com.collectrndemo.simple.modules.OnCreateViewInstanceListener
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager
import com.verygoodsecurity.vgsshow.widget.VGSTextView

class ShowCardExpirationDatePackage(
    private val listener: OnCreateViewInstanceListener<VGSTextView>
) : ReactPackage {

    override fun createNativeModules(
        reactContext: ReactApplicationContext
    ): List<NativeModule> = listOf<NativeModule>(CardExpirationDateModule(reactContext))

    override fun createViewManagers(
        reactContext: ReactApplicationContext
    ): List<ViewManager<*, *>> = listOf<ViewManager<*, *>>(CardExpirationDateManager(listener))
}