package com.collectrndemo.simple.modules.show.field.core

import com.collectrndemo.simple.modules.OnCreateViewInstanceListener
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.verygoodsecurity.vgsshow.widget.VGSTextView

/**
 * @noinspection unused
 */
abstract class BaseShowView(
    private val listener: OnCreateViewInstanceListener<VGSTextView>
) : ViewGroupManager<VGSTextView?>() {

    private lateinit var view: VGSTextView

    protected abstract fun createView(reactContext: ThemedReactContext): VGSTextView

    override fun createViewInstance(reactContext: ThemedReactContext): VGSTextView {
        view = createView(reactContext)
        listener.onCreateViewInstance(view)
        return view
    }
}