package com.collectrndemo.simple.modules.collect.scanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.verygoodsecurity.api.cardio.ScanActivity;

import java.util.HashMap;
import java.util.Map;

public class ScanModule extends ReactContextBaseJavaModule {

    public static final int IMAGE_PICKER_REQUEST = 467081;

    private HashMap<String, Integer> scanSettings = new HashMap();

    ScanModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return "ScanActivity";
    }

    @ReactMethod
    public void setItem(String key, int value) {
        scanSettings.put(key, value);
    }

    @ReactMethod
    public void clearBundle() {
        scanSettings.clear();
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("cardNumber", ScanActivity.CARD_NUMBER);

        return constants;
    }

    @ReactMethod
    public void startActivityForResult() {
        Bundle bndl = new Bundle();
        bndl.putSerializable(ScanActivity.SCAN_CONFIGURATION, scanSettings);

        ReactApplicationContext context = getReactApplicationContext();
        Intent intent = new Intent(context, ScanActivity.class);
        intent.putExtra(ScanActivity.SCAN_CONFIGURATION, scanSettings);
        context.startActivityForResult(intent, 0x3, bndl);
    }
}
