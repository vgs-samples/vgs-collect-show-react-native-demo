package com.collectrndemo.simple.modules.show;

import androidx.annotation.NonNull;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.verygoodsecurity.vgsshow.widget.VGSTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VGSShowPackage implements ReactPackage, VGSShowOnCreateViewInstanceListener {

    private VGSShowModule module;

    public VGSShowOnCreateViewInstanceListener getListener() {
        return this;
    }

    @Override
    public void onCreateViewInstance(VGSTextView view) {
        module.subscribe(view);
    }

    @NonNull
    @SuppressWarnings("rawtypes")
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        module = new VGSShowModule(reactContext);

        List<NativeModule> modules = new ArrayList<>();

        modules.add(module);

        return modules;
    }
}