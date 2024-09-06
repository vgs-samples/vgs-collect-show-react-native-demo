package com.collectrndemo.simple.modules.show

import com.collectrndemo.simple.modules.OnCreateViewInstanceListener
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager
import com.verygoodsecurity.vgsshow.widget.VGSTextView

class VGSShowPackage : ReactPackage, OnCreateViewInstanceListener<VGSTextView> {

    private var module: VGSShowModule? = null


    override fun onCreateViewInstance(view: VGSTextView) {
        module?.subscribe(view)
    }

    override fun createViewManagers(
        reactContext: ReactApplicationContext
    ): MutableList<ViewManager<*, *>> = mutableListOf()

    override fun createNativeModules(
        reactContext: ReactApplicationContext
    ): List<NativeModule> = mutableListOf(VGSShowModule(reactContext).also { module = it })
}