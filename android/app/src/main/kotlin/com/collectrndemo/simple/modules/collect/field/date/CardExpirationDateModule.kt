package com.collectrndemo.simple.modules.collect.field.date

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule

class CardExpirationDateModule(
    reactContext: ReactApplicationContext
) : ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String = "Collect " + this.javaClass.simpleName
}
