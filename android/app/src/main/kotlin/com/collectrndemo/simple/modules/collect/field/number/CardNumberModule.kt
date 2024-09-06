package com.collectrndemo.simple.modules.collect.field.number

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule

class CardNumberModule(
    reactContext: ReactApplicationContext
) : ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String = "Collect " + this.javaClass.simpleName
}
