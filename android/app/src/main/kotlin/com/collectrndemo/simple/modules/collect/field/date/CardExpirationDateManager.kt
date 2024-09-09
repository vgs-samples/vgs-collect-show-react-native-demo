package com.collectrndemo.simple.modules.collect.field.date

import android.graphics.Color
import android.util.TypedValue
import com.collectrndemo.simple.modules.OnCreateViewInstanceListener
import com.collectrndemo.simple.modules.SharedConfig
import com.collectrndemo.simple.modules.collect.field.core.BaseCollectView
import com.collectrndemo.simple.modules.util.convertPxToDp
import com.facebook.react.uimanager.ThemedReactContext
import com.verygoodsecurity.vgscollect.view.InputFieldView
import com.verygoodsecurity.vgscollect.view.date.DatePickerMode
import com.verygoodsecurity.vgscollect.widget.ExpirationDateEditText

class CardExpirationDateManager(
    listener: OnCreateViewInstanceListener<InputFieldView>
) : BaseCollectView<ExpirationDateEditText>(listener) {

    override val hint: String = "Expiration Date"

    override fun getName(): String = "VGSExpDateTextField"

    override fun createInput(reactContext: ThemedReactContext?): ExpirationDateEditText {
        val input = ExpirationDateEditText(reactContext!!)
        val padding = reactContext.convertPxToDp(8)
        input.setPadding(padding, input.paddingTop, padding, input.paddingBottom)
        input.setTextColor(Color.BLACK)
        input.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f)
        input.setDatePickerMode(DatePickerMode.SPINNER)
        input.setDateRegex("MM/yy")
        input.setFieldName(SharedConfig.EXPIRATION_DATE_FIELD_NAME)
        input.setIsRequired(true)
        return input
    }
}
