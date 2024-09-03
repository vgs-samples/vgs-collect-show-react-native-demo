package com.collectrndemo.advanced.collect.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.collectrndemo.R
import com.verygoodsecurity.vgscollect.widget.ExpirationDateEditText
import com.verygoodsecurity.vgscollect.widget.VGSCardNumberEditText

class VGSCollectCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    lateinit var cardNumber: VGSCardNumberEditText
    lateinit var expiry: ExpirationDateEditText

    init {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.collect_advanced_view, this)
        orientation = VERTICAL
        cardNumber = findViewById(R.id.vgsEtCardNumber)
        expiry = findViewById(R.id.vgsEtExpiry)
    }
}
