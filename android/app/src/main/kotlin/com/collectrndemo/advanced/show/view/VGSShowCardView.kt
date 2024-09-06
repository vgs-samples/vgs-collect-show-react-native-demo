package com.collectrndemo.advanced.show.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.collectrndemo.R
import com.verygoodsecurity.vgsshow.widget.VGSTextView

class VGSShowCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    lateinit var cardNumber: VGSTextView
    lateinit var expiry: VGSTextView

    init {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.show_advanced_view, this)
        orientation = VERTICAL
        cardNumber = findViewById(R.id.vgsTvCardNumber)
        expiry = findViewById(R.id.vgsTvExpiry)
    }
}
