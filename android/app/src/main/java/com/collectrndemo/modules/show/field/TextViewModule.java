package com.collectrndemo.modules.show.field;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class TextViewModule extends ReactContextBaseJavaModule {

    private final TextViewManager manager;

    public TextViewModule(ReactApplicationContext reactContext, TextViewManager manager) {
        super(reactContext);
        this.manager = manager;
    }

    @NonNull
    @Override
    public String getName() {
        return manager.getContentPath();
    }

    @ReactMethod
    public void getContentPath(Callback successCallback) {
//        try {
//            successCallback.invoke(fieldName);
//        } catch (IllegalViewOperationException e) { }
    }

}