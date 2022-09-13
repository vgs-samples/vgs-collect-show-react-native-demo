package com.collectrndemo.modules.show.field;

import androidx.annotation.NonNull;

import com.collectrndemo.modules.show.VGSShowOnCreateViewInstanceListener;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Collections;
import java.util.List;

public class TextViewPackage implements ReactPackage {

    private final VGSShowOnCreateViewInstanceListener listener;
    private TextViewManager calManager;

    public TextViewPackage(VGSShowOnCreateViewInstanceListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        if (calManager == null) {
            calManager = new TextViewManager(listener);
        }

        return Collections.singletonList(new TextViewModule(reactContext, calManager));
    }

    @SuppressWarnings("rawtypes")
    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        if (calManager == null) {
            calManager = new TextViewManager(listener);
        }

        return Collections.singletonList(calManager);
    }
}