package com.collectrndemo.modules.show.field.number;

import androidx.annotation.NonNull;

import com.collectrndemo.modules.show.VGSShowOnCreateViewInstanceListener;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Collections;
import java.util.List;

public class ShowCardNumberPackage implements ReactPackage {

    private final VGSShowOnCreateViewInstanceListener listener;
    private CardNumberManager calManager;

    public ShowCardNumberPackage(VGSShowOnCreateViewInstanceListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        if (calManager == null) {
            calManager = new CardNumberManager(listener);
        }

        return Collections.singletonList(new CardNumberModule(reactContext));
    }

    @SuppressWarnings("rawtypes")
    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        if (calManager == null) {
            calManager = new CardNumberManager(listener);
        }

        return Collections.singletonList(calManager);
    }
}