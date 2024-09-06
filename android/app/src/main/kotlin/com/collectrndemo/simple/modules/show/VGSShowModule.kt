@file:Suppress("unused")

package com.collectrndemo.simple.modules.show

import com.collectrndemo.simple.modules.SharedConfig
import com.collectrndemo.simple.modules.SharedConfig.showPayload
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.verygoodsecurity.vgsshow.VGSShow
import com.verygoodsecurity.vgsshow.core.VGSEnvironment.Companion.toVGSEnvironment
import com.verygoodsecurity.vgsshow.core.listener.VGSOnResponseListener
import com.verygoodsecurity.vgsshow.core.logs.VGSShowLogger
import com.verygoodsecurity.vgsshow.core.logs.VGSShowLogger.level
import com.verygoodsecurity.vgsshow.core.network.client.VGSHttpMethod
import com.verygoodsecurity.vgsshow.core.network.model.VGSResponse
import com.verygoodsecurity.vgsshow.widget.VGSTextView

/** @noinspection unused
 */
class VGSShowModule internal constructor(
    reactContext: ReactApplicationContext
) : ReactContextBaseJavaModule(reactContext), VGSOnResponseListener {

    private lateinit var show: VGSShow

    private var callback: Callback? = null

    override fun initialize() {
        super.initialize()
        level = VGSShowLogger.Level.DEBUG
        show = VGSShow.Builder(reactApplicationContext, SharedConfig.VAULT_ID)
            .setEnvironment(SharedConfig.ENVIRONMENT.toVGSEnvironment())
            .build()
        show.addOnResponseListener(this)
    }

    override fun getName(): String = "VGSShowManager"

    override fun onResponse(response: VGSResponse) {
        sendResponse(response)
    }

    fun subscribe(view: VGSTextView) {
        show.subscribe(view)
    }

    @ReactMethod
    fun revealData(callback: Callback?) {
        this.callback = callback
        show.requestAsync("/post", VGSHttpMethod.POST, showPayload)
    }

    private fun sendResponse(response: VGSResponse) {
        val responseStr = StringBuilder("Code: ")
            .append(response.code)
        if (response is VGSResponse.Error) {
            responseStr
                .append("\n")
                .append("Error: ")
                .append(response.message)
        }
        callback!!.invoke(responseStr.toString())
    }
}
