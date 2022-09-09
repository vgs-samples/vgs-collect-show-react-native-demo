package com.collectrndemo.modules.collect.field.number;

import androidx.annotation.NonNull;

import com.collectrndemo.modules.collect.VGSCollectOnCreateViewInstanceListener;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Collections;
import java.util.List;

public class CardNumberPackage implements ReactPackage {

    private final VGSCollectOnCreateViewInstanceListener listener;
    private CardNumberManager calManager;

    public CardNumberPackage(VGSCollectOnCreateViewInstanceListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        if (calManager == null) {
            calManager = new CardNumberManager(listener);
        }
        return Collections.singletonList(
                new CardNumberModule(reactContext, calManager)
        );
    }

    @NonNull
    @SuppressWarnings("rawtypes")
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        if (calManager == null) {
            calManager = new CardNumberManager(listener);
        }
        return Collections.singletonList(
                calManager
        );
    }
}