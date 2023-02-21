package com.collectrndemo.modules.show.field.date;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

public class CardExpirationDateModule extends ReactContextBaseJavaModule {

    public CardExpirationDateModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return "Show " + this.getClass().getSimpleName();
    }
}