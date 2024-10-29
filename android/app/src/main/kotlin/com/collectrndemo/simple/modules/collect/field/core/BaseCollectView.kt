package com.collectrndemo.simple.modules.collect.field.core

import android.R.attr.radius
import android.content.res.ColorStateList
import android.graphics.Color
import com.collectrndemo.simple.modules.OnCreateViewInstanceListener
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.uimanager.annotations.ReactProp
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

    @ReactProp(name = "borderColor")
    fun setBorderColor(view: VGSTextInputLayout, value: String) {
        layout.setBoxStrokeColor(Color.parseColor(value))
        view.invalidate()
    }

    @ReactProp(name = "borderColors")
    fun setBorderColors(view: VGSTextInputLayout, value: ReadableArray) {
        val states = arrayOf(
            intArrayOf(android.R.attr.state_focused), // focused
            intArrayOf(), // unfocused
        )
        val colors = intArrayOf(
            Color.parseColor(value.getString(0)),
            Color.parseColor(value.getString(1)),
        )
        layout.setBoxStrokeColorStateList(ColorStateList(states, colors))
        view.invalidate()
    }

    private fun createLayout(reactContext: ThemedReactContext): VGSTextInputLayout {
        val layout = VGSTextInputLayout(reactContext)
        layout.setBoxCornerRadius(
            DEFAULT_CORNER_RADIUS.toFloat(),
            DEFAULT_CORNER_RADIUS.toFloat(),
            DEFAULT_CORNER_RADIUS.toFloat(),
            DEFAULT_CORNER_RADIUS.toFloat()
        )
        layout.setHintEnabled(false)
        return layout
    }
}
