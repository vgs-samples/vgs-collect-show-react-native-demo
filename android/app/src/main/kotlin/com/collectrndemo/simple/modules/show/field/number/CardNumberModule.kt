package com.collectrndemo.simple.modules.show.field.number

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule

class CardNumberModule(reactContext: ReactApplicationContext?) :
    ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String = "Show " + this.javaClass.simpleName
}
