package com.collectrndemo.modules.collect.field.date;

import androidx.annotation.NonNull;

import com.collectrndemo.modules.collect.VGSCollectOnCreateViewInstanceListener;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Collections;
import java.util.List;

public class CardExpDatePackage implements ReactPackage {

    private final VGSCollectOnCreateViewInstanceListener listener;
    private CardExpDateManager calManager;

    public CardExpDatePackage(VGSCollectOnCreateViewInstanceListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        if (calManager == null) {
            calManager = new CardExpDateManager(listener);
        }
        return Collections.singletonList(
                new CardExpDateModule(reactContext, calManager)
        );
    }

    @SuppressWarnings("rawtypes")
    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        if (calManager == null) {
            calManager = new CardExpDateManager(listener);
        }
        return Collections.singletonList(
                calManager
        );
    }
}