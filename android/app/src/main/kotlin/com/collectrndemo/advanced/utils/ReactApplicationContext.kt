package com.collectrndemo.advanced.utils

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.UIManagerModule

fun ReactApplicationContext.uiManager() = this.getNativeModule(UIManagerModule::class.java)
