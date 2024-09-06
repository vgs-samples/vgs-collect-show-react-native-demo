package com.collectrndemo.simple.modules.collect.field.number;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

public class CardNumberModule extends ReactContextBaseJavaModule {

    public CardNumberModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return "Collect " + this.getClass().getSimpleName();
    }
}