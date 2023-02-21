package com.collectrndemo.modules.show.field.number;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

import java.util.UUID;

public class CardNumberModule extends ReactContextBaseJavaModule {

    public CardNumberModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return "Show " + this.getClass().getSimpleName();
    }
}