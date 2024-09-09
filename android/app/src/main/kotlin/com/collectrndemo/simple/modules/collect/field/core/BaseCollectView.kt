package com.collectrndemo.simple.modules.collect.field.core

import com.collectrndemo.simple.modules.OnCreateViewInstanceListener
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.verygoodsecurity.vgscollect.view.InputFieldView
import com.verygoodsecurity.vgscollect.widget.VGSTextInputLayout

private const val DEFAULT_CORNER_RADIUS = 8

/**
 * @noinspection unused
 */
abstract class BaseCollectView<T : InputFieldView>(
    private val listener: OnCreateViewInstanceListener<InputFieldView>
) : ViewGroupManager<VGSTextInputLayout?>() {

    private lateinit var layout: VGSTextInputLayout

    protected abstract val hint: String?

    protected abstract fun createInput(reactContext: ThemedReactContext?): T

    override fun createViewInstance(reactContext: ThemedReactContext): VGSTextInputLayout {
        layout = createLayout(reactContext)
        val input = createInput(reactContext)
        layout.addView(input)
        listener.onCreateViewInstance(input)
        return layout
    }

    private fun createLayout(reactContext: ThemedReactContext): VGSTextInputLayout {
        val layout = VGSTextInputLayout(reactContext)
        layout.setBoxCornerRadius(
            DEFAULT_CORNER_RADIUS.toFloat(),
            DEFAULT_CORNER_RADIUS.toFloat(),
            DEFAULT_CORNER_RADIUS.toFloat(),
            DEFAULT_CORNER_RADIUS.toFloat()
        )
        layout.setHint(hint)
        return layout
    }
}
