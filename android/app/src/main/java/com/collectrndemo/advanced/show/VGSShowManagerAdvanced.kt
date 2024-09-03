package com.collectrndemo.advanced.show

import android.util.Log
import com.collectrndemo.advanced.show.view.VGSShowCardView
import com.collectrndemo.advanced.utils.uiManager
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableMap
import com.verygoodsecurity.vgsshow.VGSShow
import com.verygoodsecurity.vgsshow.core.network.client.VGSHttpMethod
import com.verygoodsecurity.vgsshow.core.network.model.VGSResponse
import com.verygoodsecurity.vgsshow.widget.VGSTextView
import java.util.concurrent.Executors
import java.util.concurrent.Future

private const val ARG_ENV = "environment"
private const val ARG_VAULT_ID = "vaultId"
private const val ARG_EXPIRY_CONTENT_PATH = "expDateFieldName"
private const val ARG_CARD_NUMBER_CONTENT_PATH = "cardNumberFieldName"

@Suppress("unused")
class VGSShowManagerAdvanced(context: ReactApplicationContext) :
    ReactContextBaseJavaModule(context) {

    private lateinit var show: VGSShow

    private var view: VGSShowCardView? = null

    private val executor = Executors.newSingleThreadExecutor()
    private var task: Future<*>? = null

    override fun getName(): String = "VGSShowManagerAdvanced"

    @ReactMethod
    fun setupVGSShow(arguments: ReadableMap, callback: Callback) {
        try {
            initializeShow(
                arguments.getString(ARG_ENV), arguments.getString(ARG_VAULT_ID)
            )
            callback.invoke("Show Status: VGSShow setup success.")
        } catch (e: IllegalArgumentException) {
            callback.invoke("Show Status: VGSShow setup failed.")
        }
    }

    @ReactMethod
    fun setupShowViewFromManager(viewId: Int, arguments: ReadableMap, callback: Callback) {
        reactApplicationContext.uiManager()?.resolveView(viewId)?.let {
            val cardNumberContentPath = arguments.getString(ARG_CARD_NUMBER_CONTENT_PATH)
            val expiryContentPath = arguments.getString(ARG_EXPIRY_CONTENT_PATH)
            view = it as VGSShowCardView
            view?.cardNumber?.run {
                setContentPath(cardNumberContentPath)
                show.subscribe(this)
            }
            view?.expiry?.run {
                setContentPath(expiryContentPath)
                show.subscribe(this)
            }
            callback.invoke("Show Status: VGSView found successfully.")
        } ?: callback.invoke("Show Status: VGSView not found.")
    }

    @ReactMethod
    fun revealData(payload: ReadableMap, callback: Callback) {
        Log.d("TEST:DD", """
            payload = $payload
            payment_card_expiration_date = ${payload.getString("payment_card_expiration_date")}
            payment_card_number = ${payload.getString("payment_card_expiration_date")}
            payload formated = ${payload.toHashMap()}
        """.trimIndent())
        task = executor.submit {
            val response = show.request(
                path = "/post",
                method = VGSHttpMethod.POST,
                payload = payload.toHashMap()
            )
            when (response) {
                is VGSResponse.Success -> callback.invoke("Show Status: reveal success.")
                is VGSResponse.Error -> callback.invoke("Show Status: reveal error, code = ${response.code}.")
            }
        }
    }

    @ReactMethod
    fun copyCardNumber() {
        view?.cardNumber?.copyToClipboard(VGSTextView.CopyTextFormat.FORMATTED)
    }

    @Throws(IllegalArgumentException::class)
    private fun initializeShow(environment: String?, vault: String?) {
        if (environment.isNullOrEmpty() || vault.isNullOrEmpty()) throw IllegalArgumentException()
        show = VGSShow(reactApplicationContext, vault, environment)
    }
}
