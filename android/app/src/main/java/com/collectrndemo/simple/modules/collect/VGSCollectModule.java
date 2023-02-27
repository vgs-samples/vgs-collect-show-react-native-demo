package com.collectrndemo.simple.modules.collect;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.collectrndemo.simple.modules.SharedConfig;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.verygoodsecurity.api.cardio.ScanActivity;
import com.verygoodsecurity.vgscollect.VGSCollectLogger;
import com.verygoodsecurity.vgscollect.core.Environment;
import com.verygoodsecurity.vgscollect.core.HTTPMethod;
import com.verygoodsecurity.vgscollect.core.VGSCollect;
import com.verygoodsecurity.vgscollect.core.model.network.VGSResponse;
import com.verygoodsecurity.vgscollect.view.InputFieldView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

@SuppressWarnings({"unused", "ConstantConditions"})
public class VGSCollectModule extends ReactContextBaseJavaModule {

    private static final String RESPONSE_EVENT_NAME = "VGSCollectOnVGSResponse";

    private static ReactApplicationContext reactContext;

    private VGSCollect collect;

    private final HashMap<String, Integer> scanParams = new HashMap<>();

    private Callback callback;

    VGSCollectModule(ReactApplicationContext c) {
        super(c);
        reactContext = c;
    }

    @Override
    public void initialize() {
        super.initialize();
        VGSCollectLogger.INSTANCE.setLogLevel(VGSCollectLogger.Level.DEBUG);

        Activity activity = reactContext.getCurrentActivity();
        collect = new VGSCollect.Builder(activity, SharedConfig.VAULT_ID)
                .setEnvironment(Environment.SANDBOX)
                .create();

        initListeners();
    }

    private void initListeners() {
        collect.addOnResponseListeners(this::handleResponse);
    }

    @NonNull
    @Override
    public String getName() {
        return "VGSCollectManager";
    }

    @ReactMethod
    public void submitData(Callback callback) {
        this.callback = callback;
        collect.asyncSubmit("/post", HTTPMethod.POST);
    }

    @ReactMethod
    public void presentCardIO() {
        Intent intent = new Intent(getReactApplicationContext(), ScanActivity.class);
        intent.putExtra(ScanActivity.SCAN_CONFIGURATION, scanParams);
        getReactApplicationContext().startActivityForResult(intent, 0, null);
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        collect.onActivityResult(requestCode, resultCode, data);
    }

    public void bindView(InputFieldView inputFieldView, int scanType) {
        collect.bindView(inputFieldView);
        scanParams.put(inputFieldView.getFieldName(), scanType);
    }

    private void handleResponse(VGSResponse response) {
        // Read response data and update show payload
        if (response instanceof VGSResponse.SuccessResponse) {
            updateShowPayload((VGSResponse.SuccessResponse) response);
        }

        // Publish update to react native
        StringBuilder responseStr = new StringBuilder("Code: ")
                .append(response.getCode())
                .append("\n")
                .append("Body: ")
                .append(response.getBody());
        if (response instanceof VGSResponse.ErrorResponse) {
            responseStr
                    .append("\n")
                    .append("Error: ")
                    .append(((VGSResponse.ErrorResponse) response).getLocalizeMessage());
        }
        callback.invoke(responseStr.toString());
    }

    private void updateShowPayload(VGSResponse.SuccessResponse response) {
        try {
            JSONObject data = new JSONObject(response.getBody()).getJSONObject("json");
            String cardNumberAlias = data.getString(SharedConfig.CARD_NUMBER_FIELD_NAME);
            String expirationDateAlias = data.getString(SharedConfig.EXPIRATION_DATE_FIELD_NAME);
            SharedConfig.showPayload.put(SharedConfig.CARD_NUMBER_PAYLOAD_KEY, cardNumberAlias);
            SharedConfig.showPayload.put(SharedConfig.EXPIRATION_DATE_PAYLOAD_KEY, expirationDateAlias);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}