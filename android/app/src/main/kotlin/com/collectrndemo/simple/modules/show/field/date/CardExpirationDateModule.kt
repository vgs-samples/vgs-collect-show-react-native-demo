package com.collectrndemo.simple.modules.show.field.date

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule

class CardExpirationDateModule(reactContext: ReactApplicationContext?) :
    ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String = "Show " + this.javaClass.simpleName
}
