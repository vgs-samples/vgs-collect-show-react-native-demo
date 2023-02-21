package com.collectrndemo.modules.collect.field.date;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.IllegalViewOperationException;

public class CardExpDateModule extends ReactContextBaseJavaModule {

    private String fieldName;

    public CardExpDateModule(ReactApplicationContext reactContext, CardExpirationDateManager calManager) {
        super(reactContext);
        if (calManager != null) {
            fieldName = calManager.getFieldName();
        }
    }

    @NonNull
    @Override
    public String getName() {
        return "ExpDateEditText";
    }

    @ReactMethod
    public void getFieldName(Callback successCallback) {
        try {
            successCallback.invoke(fieldName);
        } catch (IllegalViewOperationException e) {
            e.printStackTrace();
        }
    }
}