@file:Suppress("unused")

package com.collectrndemo.simple.modules.collect

import android.content.Intent
import com.collectrndemo.simple.modules.SharedConfig
import com.collectrndemo.simple.modules.SharedConfig.showPayload
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.verygoodsecurity.api.cardio.ScanActivity
import com.verygoodsecurity.vgscollect.VGSCollectLogger
import com.verygoodsecurity.vgscollect.VGSCollectLogger.logLevel
import com.verygoodsecurity.vgscollect.core.Environment
import com.verygoodsecurity.vgscollect.core.HTTPMethod
import com.verygoodsecurity.vgscollect.core.VGSCollect
import com.verygoodsecurity.vgscollect.core.VgsCollectResponseListener
import com.verygoodsecurity.vgscollect.core.model.network.VGSResponse
import com.verygoodsecurity.vgscollect.view.InputFieldView
import com.verygoodsecurity.vgscollect.widget.ExpirationDateEditText
import com.verygoodsecurity.vgscollect.widget.VGSCardNumberEditText
import org.json.JSONException
import org.json.JSONObject

class VGSCollectModule internal constructor(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {

    private lateinit var collect: VGSCollect

    private val scanParams = HashMap<String?, Int>()

    private var callback: Callback? = null

    override fun initialize() {
        super.initialize()
        logLevel = VGSCollectLogger.Level.DEBUG
        collect = VGSCollect.Builder(reactApplicationContext.currentActivity!!, SharedConfig.VAULT_ID)
            .setEnvironment(Environment.SANDBOX)
            .create()
        initListeners()
    }

    private fun initListeners() {
        collect.addOnResponseListeners(object : VgsCollectResponseListener {

            override fun onResponse(response: VGSResponse?) {
                response?.let { handleResponse(it) }
            }
        })
    }

    override fun getName(): String {
        return "VGSCollectManager"
    }

    @ReactMethod
    fun submitData(callback: Callback?) {
        this.callback = callback
        collect.asyncSubmit("/post", HTTPMethod.POST)
    }

    @ReactMethod
    fun presentCardIO() {
        val intent = Intent(
            reactApplicationContext, ScanActivity::class.java
        )
        intent.putExtra(ScanActivity.SCAN_CONFIGURATION, scanParams)
        reactApplicationContext.startActivityForResult(intent, 0, null)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        collect.onActivityResult(requestCode, resultCode, data)
    }

    fun bindView(inputFieldView: InputFieldView) {
        collect.bindView(inputFieldView)
        scanParams[inputFieldView.getFieldName()] = getScanType(inputFieldView)
    }

    private fun handleResponse(response: VGSResponse) {
        // Read response data and update show payload
        if (response is VGSResponse.SuccessResponse) {
            response.body?.let { updateShowPayload(it) }
        }

        // Publish update to react native
        val result = StringBuilder("Code: ")
            .append(response.code)
            .append("\n")
            .append("Body: ")
            .append(response.body)

        if (response is VGSResponse.ErrorResponse) {
            result
                .append("\n")
                .append("Error: ")
                .append(response.localizeMessage)
        }
        callback?.invoke(result.toString())
    }

    private fun updateShowPayload(responseBody: String) {
        try {
            val data: JSONObject = JSONObject(responseBody).getJSONObject("json")
            val cardNumberAlias = data.getString(SharedConfig.CARD_NUMBER_FIELD_NAME)
            val expirationDateAlias = data.getString(SharedConfig.EXPIRATION_DATE_FIELD_NAME)
            showPayload[SharedConfig.CARD_NUMBER_PAYLOAD_KEY] = cardNumberAlias
            showPayload[SharedConfig.EXPIRATION_DATE_PAYLOAD_KEY] = expirationDateAlias
        } catch (e: JSONException) {
            throw RuntimeException(e)
        }
    }

    private fun getScanType(view: InputFieldView): Int {
        return when (view) {
            is VGSCardNumberEditText -> ScanActivity.CARD_NUMBER
            is ExpirationDateEditText -> ScanActivity.CARD_EXP_DATE
            else -> -1
        }
    }
}