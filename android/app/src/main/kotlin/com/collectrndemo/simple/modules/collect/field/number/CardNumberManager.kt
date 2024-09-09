package com.collectrndemo.simple.modules.collect.field.number

import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import com.collectrndemo.simple.modules.OnCreateViewInstanceListener
import com.collectrndemo.simple.modules.SharedConfig
import com.collectrndemo.simple.modules.collect.field.core.BaseCollectView
import com.collectrndemo.simple.modules.util.convertPxToDp
import com.facebook.react.uimanager.ThemedReactContext
import com.verygoodsecurity.vgscollect.view.InputFieldView
import com.verygoodsecurity.vgscollect.widget.VGSCardNumberEditText

class CardNumberManager(
    listener: OnCreateViewInstanceListener<InputFieldView>
) : BaseCollectView<VGSCardNumberEditText>(listener) {

    override val hint: String = "Card Number"

    override fun getName(): String = "VGSCardTextField"

    override fun createInput(reactContext: ThemedReactContext?): VGSCardNumberEditText {
        val input = VGSCardNumberEditText(reactContext!!)
        val padding = reactContext.convertPxToDp(8)
        input.setPadding(padding, input.paddingTop, padding, input.paddingBottom)
        input.setTextColor(Color.BLACK)
        input.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f)
        input.setDivider(' ')
        input.setCardBrandIconGravity(Gravity.END)
        input.setFieldName(SharedConfig.CARD_NUMBER_FIELD_NAME)
        input.setIsRequired(true)
        return input
    }
}
