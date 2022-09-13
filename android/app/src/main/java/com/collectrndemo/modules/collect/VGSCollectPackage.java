package com.collectrndemo.modules.collect;


import androidx.annotation.NonNull;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.verygoodsecurity.vgscollect.view.InputFieldView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VGSCollectPackage implements ReactPackage, VGSCollectOnCreateViewInstanceListener {

    private VGSCollectModule module;

    public VGSCollectOnCreateViewInstanceListener getListener() {
        return this;
    }

    public VGSCollectModule getVGSCollectModule() {
        return module;
    }

    @Override
    public void onCreateViewInstance(InputFieldView inputFieldView) {
        module.bindView(inputFieldView);
    }

    @SuppressWarnings("rawtypes")
    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        module = new VGSCollectModule(reactContext);

        List<NativeModule> modules = new ArrayList<>();

        modules.add(module);

        return modules;
    }
}