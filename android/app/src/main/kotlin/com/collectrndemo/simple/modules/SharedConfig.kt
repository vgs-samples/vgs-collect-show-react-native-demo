package com.collectrndemo.simple.modules

object SharedConfig {

    const val VAULT_ID: String = "tntgsbvmics"

    const val ENVIRONMENT: String = "sandbox"

    const val CARD_NUMBER_FIELD_NAME: String = "cardNumber"
    const val EXPIRATION_DATE_FIELD_NAME: String = "expDate"
    const val CARD_NUMBER_CONTENT_PATH: String = "json.payment_card_number"
    const val EXPIRATION_DATE_CONTENT_PATH: String = "json.payment_card_expiration_date"
    const val CARD_NUMBER_PAYLOAD_KEY: String = "payment_card_number"
    const val EXPIRATION_DATE_PAYLOAD_KEY: String = "payment_card_expiration_date"

    val showPayload: MutableMap<String, String> = HashMap()
}