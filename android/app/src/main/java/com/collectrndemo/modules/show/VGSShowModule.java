package com.collectrndemo.modules.show;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.verygoodsecurity.vgsshow.VGSShow;
import com.verygoodsecurity.vgsshow.core.VGSEnvironment;
import com.verygoodsecurity.vgsshow.core.listener.VGSOnResponseListener;
import com.verygoodsecurity.vgsshow.core.logs.VGSShowLogger;
import com.verygoodsecurity.vgsshow.core.network.client.VGSHttpMethod;
import com.verygoodsecurity.vgsshow.core.network.model.VGSResponse;
import com.verygoodsecurity.vgsshow.widget.VGSTextView;

import java.util.HashMap;
import java.util.Map;

public class VGSShowModule extends ReactContextBaseJavaModule {

    private static final String RESPONSE_EVENT_NAME = "VGSShowOnVGSResponse";
    private static final String VAULT_ID = "<VAULT_ID>";
    private static final String MODULE_NAME = "VGSShow";
    private static final String PATH = "/post";

    private static ReactApplicationContext reactContext;
    private VGSShow show;

    private Map<String, VGSTextView> views = new HashMap<>();

    VGSShowModule(ReactApplicationContext c) {
        super(c);
        reactContext = c;
    }

    @Override
    public void initialize() {
        super.initialize();
        VGSShowLogger.INSTANCE.setLevel(VGSShowLogger.Level.DEBUG);

        Activity activity = reactContext.getCurrentActivity();

        show = new VGSShow.Builder(activity, VAULT_ID)
                .setEnvironment(new VGSEnvironment.Sandbox())
                .build();

        initListeners();
    }

    public void subscribe(VGSTextView view) {
        Log.d("VGSShowModule", "bindView");
        show.subscribe(view);
    }

    public void contentPathUpdated(VGSTextView view) {
        Log.d("VGSShowModule", "contentPath " + view.getContentPath());
        views.put(view.getContentPath(), view);
    }

    private void initListeners() {
        show.addOnResponseListener(new VGSOnResponseListener() {
            @Override
            public void onResponse(VGSResponse response) {
//                sendResponse(response);
                Log.d("VGSShowModule", "submitAsync" + response.toString());
            }
        });
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void submitAsync(ReadableMap rnMap) {
        String key11 = rnMap.hasKey("payment_card_number") ? rnMap.getString("payment_card_number") : "empty";
        String key12 = rnMap.hasKey("payment_card_expiration_date") ? rnMap.getString("payment_card_expiration_date") : "empty";
        Log.d("VGSShowModule", "key1: " + key11);
        Log.d("VGSShowModule", "key2: " + key12);

        Map<String, Object> map = recursivelyDeconstructReadableMap(rnMap);
        convertWithIteration(map);
        show.requestAsync(PATH, VGSHttpMethod.POST, map);
    }

    @ReactMethod
    public void copyToClipboard(String contentPath, String format) {
        VGSTextView view = views.get(contentPath);
        if (view != null) {
            view.copyToClipboard(mapCopyTextFormat(format));
            Toast.makeText(view.getContext(), "Copy", Toast.LENGTH_SHORT).show();
        }
    }

    public void convertWithIteration(Map<String, ?> map) {
        StringBuilder mapAsString = new StringBuilder();
        for (String key : map.keySet()) {
            mapAsString.append(key + "=" + map.get(key) + ", ");
        }

        Log.d("VGSShowModule", "map: " + mapAsString);
    }

    private Map<String, Object> recursivelyDeconstructReadableMap(ReadableMap readableMap) {
        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
        Map<String, Object> deconstructedMap = new HashMap<>();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            ReadableType type = readableMap.getType(key);
            switch (type) {
                case Null:
                    break;
                case Boolean:
                    deconstructedMap.put(key, readableMap.getBoolean(key));
                    break;
                case Number:
                    deconstructedMap.put(key, readableMap.getDouble(key));
                    break;
                case String:
                    deconstructedMap.put(key, readableMap.getString(key));
                    break;
            }

        }
        return deconstructedMap;
    }

    private VGSTextView.CopyTextFormat mapCopyTextFormat(String format) {
        switch (format) {
            case "FORMATTED":
                return VGSTextView.CopyTextFormat.FORMATTED;
            case "RAW":
                return VGSTextView.CopyTextFormat.RAW;
            default:
                throw new IllegalArgumentException("Not implemented or invalid format");
        }
    }

//    private void sendResponse(VGSResponse response) {
//        String responseStr = " ";
//
//        if(response instanceof VGSResponse.SuccessResponse ) {
//            parseNumberAlias(response);
//            parseDateAlias(response);
//
//            responseStr = "Code: " + ((VGSResponse.SuccessResponse) response).getSuccessCode();
//        } else if(response instanceof VGSResponse.ErrorResponse ) {
//            responseStr = new StringBuilder("Code: ")
//                    .append(response.getCode())
//                    .append(" \n ")
//                    .append(((VGSResponse.ErrorResponse) response).getLocalizeMessage())
//                    .toString();
//        }
//
//        this.getReactApplicationContext()
//                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                .emit(RESPONSE_EVENT_NAME, responseStr);
//
//    }
//
//    private void parseNumberAlias(VGSResponse response) {
//        String token = JSONObjectUtil.getValue(response.getBody(), "cardNumber");
//        this.getReactApplicationContext()
//                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                .emit("cardNumberToken", token);
//    }
//
//    private void parseDateAlias(VGSResponse response) {
//        String token = JSONObjectUtil.getValue(response.getBody(), "expDate");
//        this.getReactApplicationContext()
//                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                .emit("expirationDateToken", token);
//    }

}