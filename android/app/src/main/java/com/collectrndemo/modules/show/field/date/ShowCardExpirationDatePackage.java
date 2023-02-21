package com.collectrndemo.modules.show.field.date;

import androidx.annotation.NonNull;

import com.collectrndemo.modules.show.VGSShowOnCreateViewInstanceListener;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Collections;
import java.util.List;

public class ShowCardExpirationDatePackage implements ReactPackage {

    private final VGSShowOnCreateViewInstanceListener listener;

    public ShowCardExpirationDatePackage(VGSShowOnCreateViewInstanceListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        return Collections.singletonList(new CardExpirationDateModule(reactContext));
    }

    @SuppressWarnings("rawtypes")
    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        return Collections.singletonList(new CardExpirationDateManager(listener));
    }
}