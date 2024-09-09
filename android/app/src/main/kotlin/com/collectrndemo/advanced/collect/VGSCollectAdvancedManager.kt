package com.collectrndemo.advanced.collect

import com.collectrndemo.advanced.collect.view.VGSCollectCardView
import com.collectrndemo.advanced.utils.uiManager
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableMap
import com.verygoodsecurity.vgscollect.core.HTTPMethod
import com.verygoodsecurity.vgscollect.core.VGSCollect
import com.verygoodsecurity.vgscollect.core.model.network.VGSRequest
import com.verygoodsecurity.vgscollect.core.model.network.VGSResponse
import org.json.JSONObject
import java.util.concurrent.Executors
import java.util.concurrent.Future

private const val ARG_ENV = "environment"
private const val ARG_VAULT_ID = "vaultId"
private const val ARG_EXPIRY_FIELD_NAME = "expDateFieldName"
private const val ARG_CARD_NUMBER_FIELD_NAME = "cardNumberFieldName"

@Suppress("unused")
class VGSCollectAdvancedManager(c: ReactApplicationContext) : ReactContextBaseJavaModule(c) {

    private lateinit var collect: VGSCollect

    private var view: VGSCollectCardView? = null

    private val executor = Executors.newSingleThreadExecutor()
    private var task: Future<*>? = null

    override fun getName(): String = "VGSCollectAdvancedManager"

    @ReactMethod
    fun setupVGSCollect(arguments: ReadableMap, callback: Callback) {
        try {
            initializeCollect(
                arguments.getString(ARG_ENV), arguments.getString(ARG_VAULT_ID)
            )
            callback.invoke("Show Status: VGSShow setup success.")
        } catch (e: IllegalArgumentException) {
            callback.invoke("Show Status: VGSShow setup failed.")
        }
    }

    @ReactMethod
    fun setupCollectViewFromManager(viewId: Int, arguments: ReadableMap, callback: Callback) {
        reactApplicationContext.uiManager()?.resolveView(viewId)?.let {
            val cardNumberContentPath = arguments.getString(ARG_CARD_NUMBER_FIELD_NAME)
            val expiryContentPath = arguments.getString(ARG_EXPIRY_FIELD_NAME)
            view = it as VGSCollectCardView
            view?.cardNumber?.setFieldName(cardNumberContentPath)
            view?.expiry?.setFieldName(expiryContentPath)
            collect.bindView(view?.cardNumber, view?.expiry)
            callback.invoke("Collect Status: VGSView found successfully.")
        } ?: callback.invoke("Collect Status: VGSView not found.")
    }

    @ReactMethod
    fun showKeyboardOnCardNumber() {
        view?.post {
            view?.cardNumber?.requestFocus()
            view?.cardNumber?.showKeyboard()
        }
    }

    @ReactMethod
    fun hideKeyboard() {
        if (view?.cardNumber?.isFocused == true) {
            view?.cardNumber?.hideKeyboard()
        } else {
            view?.expiry?.hideKeyboard()
        }
    }

    @ReactMethod
    fun isFormValid(callback: Callback) {
        val isValid = collect.getAllStates().all { it.isValid }
        callback.invoke(Arguments.createMap().apply { putBoolean("isValid", isValid) })
    }

    @ReactMethod
    fun submitData(callback: Callback) {
        task = executor.submit {
            val response = collect.submit(
                VGSRequest.VGSRequestBuilder().setMethod(HTTPMethod.POST).setPath("/post")
                    .setCustomData(mapOf("extraData" to "Some extra value")).build()
            )
            when (response) {
                is VGSResponse.SuccessResponse -> {
                    response.body?.let { body ->
                        try {
                            val jsonResponse = JSONObject(body).getJSONObject("json")
                            val cardAlias = jsonResponse.getString("cardNumber")
                            val expiryAlias = jsonResponse.getString("expDate")
                            callback.invoke(Arguments.createMap().apply {
                                putMap("json", Arguments.createMap().apply {
                                    putString("cardNumber", cardAlias)
                                    putString("expDate", expiryAlias)
                                })
                            })
                        } catch (e: Exception) {
                            callback.invoke("Something went wrong.")
                        }
                    } ?: callback.invoke("Something went wrong. Body is null.")
                }

                is VGSResponse.ErrorResponse -> {
                    callback.invoke("Something went wrong. Code: ${response.code}")
                }
            }
        }
    }

    @ReactMethod
    fun unregisterAllTextFields() {
        collect.unbindView(view?.cardNumber, view?.expiry)
        task?.cancel(true)
    }

    @Throws(IllegalArgumentException::class)
    private fun initializeCollect(environment: String?, vault: String?) {
        if (environment.isNullOrEmpty() || vault.isNullOrEmpty()) throw IllegalArgumentException()
        collect = VGSCollect(reactApplicationContext, vault, environment)
    }
}
