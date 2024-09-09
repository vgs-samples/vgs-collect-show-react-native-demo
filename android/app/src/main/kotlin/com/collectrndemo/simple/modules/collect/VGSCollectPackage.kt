package com.collectrndemo.simple.modules.collect

import android.view.View
import com.collectrndemo.simple.modules.OnCreateViewInstanceListener
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager
import com.verygoodsecurity.vgscollect.view.InputFieldView

@Suppress("MemberVisibilityCanBePrivate")
class VGSCollectPackage : ReactPackage, OnCreateViewInstanceListener<InputFieldView> {

    var module: VGSCollectModule? = null

    override fun onCreateViewInstance(view: InputFieldView) {
        module?.bindView(view)
    }

    override fun createViewManagers(
        context: ReactApplicationContext
    ): MutableList<ViewManager<View, ReactShadowNode<*>>> = mutableListOf()

    override fun createNativeModules(
        reactContext: ReactApplicationContext
    ): MutableList<NativeModule> =
        mutableListOf(VGSCollectModule(reactContext).also { module = it })
}