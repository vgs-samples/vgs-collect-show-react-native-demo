package com.collectrndemo.advanced.collect.view

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule

class VGSCollectCardViewModule(context: ReactApplicationContext) :
    ReactContextBaseJavaModule(context) {

    override fun getName(): String = this::class.simpleName.toString()
}