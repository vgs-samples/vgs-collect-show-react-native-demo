package com.collectrndemo.simple.modules.show.field.date

import androidx.core.content.ContextCompat
import com.collectrndemo.R
import com.collectrndemo.simple.modules.OnCreateViewInstanceListener
import com.collectrndemo.simple.modules.SharedConfig
import com.collectrndemo.simple.modules.show.field.core.BaseShowView
import com.collectrndemo.simple.modules.util.convertPxToDp
import com.facebook.react.uimanager.ThemedReactContext
import com.verygoodsecurity.vgsshow.widget.VGSTextView

@Suppress("unused")
class CardExpirationDateManager(
    listener: OnCreateViewInstanceListener<VGSTextView>
) : BaseShowView(listener) {

    override fun getName(): String = "VGSExpDateLabel"

    override fun createView(reactContext: ThemedReactContext): VGSTextView {
        val view = VGSTextView(reactContext)
        val padding = reactContext.convertPxToDp(8)
        view.setPadding(padding, padding, padding, padding)
        view.background = ContextCompat.getDrawable(reactContext, R.drawable.border_with_radius)
        view.setContentPath(SharedConfig.EXPIRATION_DATE_CONTENT_PATH)
        view.setHint("Revealed Expiration Number")
        return view
    }
}