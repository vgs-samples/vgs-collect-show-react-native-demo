package com.collectrndemo.simple.modules.show;

import androidx.annotation.NonNull;

import com.collectrndemo.simple.modules.SharedConfig;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.verygoodsecurity.vgsshow.VGSShow;
import com.verygoodsecurity.vgsshow.core.VGSEnvironment;
import com.verygoodsecurity.vgsshow.core.logs.VGSShowLogger;
import com.verygoodsecurity.vgsshow.core.network.client.VGSHttpMethod;
import com.verygoodsecurity.vgsshow.core.network.model.VGSResponse;
import com.verygoodsecurity.vgsshow.widget.VGSTextView;

public class VGSShowModule extends ReactContextBaseJavaModule {

    private static ReactApplicationContext reactContext;

    private VGSShow show;

    private Callback callback;

    VGSShowModule(ReactApplicationContext c) {
        super(c);
        reactContext = c;
    }

    @Override
    public void initialize() {
        super.initialize();
        VGSShowLogger.INSTANCE.setLevel(VGSShowLogger.Level.DEBUG);
        show = new VGSShow.Builder(reactContext, SharedConfig.VAULT_ID)
                .setEnvironment(VGSEnvironment.Companion.toVGSEnvironment(SharedConfig.ENVIRONMENT))
                .build();
        show.addOnResponseListener(this::sendResponse);
    }

    @NonNull
    @Override
    public String getName() {
        return "VGSShowManager";
    }

    public void subscribe(VGSTextView view) {
        show.subscribe(view);
    }

    @ReactMethod
    public void revealData(Callback callback) {
        this.callback = callback;
        show.requestAsync("/post", VGSHttpMethod.POST, SharedConfig.showPayload);
    }

    private void sendResponse(VGSResponse response) {
        StringBuilder responseStr = new StringBuilder("Code: ")
                .append(response.getCode());
        if (response instanceof VGSResponse.Error) {
            responseStr
                    .append("\n")
                    .append("Error: ")
                    .append(((VGSResponse.Error) response).getMessage());
        }
        callback.invoke(responseStr.toString());
    }
}