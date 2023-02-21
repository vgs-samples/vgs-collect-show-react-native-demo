package com.collectrndemo.modules.collect.field.core;

import android.content.Context;
import android.util.TypedValue;

import androidx.annotation.NonNull;

import com.collectrndemo.modules.collect.VGSCollectOnCreateViewInstanceListener;
import com.collectrndemo.modules.util.ResourceUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.verygoodsecurity.vgscollect.view.InputFieldView;
import com.verygoodsecurity.vgscollect.widget.VGSTextInputLayout;

public abstract class BaseCollectView<T extends InputFieldView> extends ViewGroupManager<VGSTextInputLayout> {

    private static final int DEFAULT_CORNER_RADIUS = 8;

    private final VGSCollectOnCreateViewInstanceListener listener;

    protected VGSTextInputLayout layout;

    protected T input;

    public BaseCollectView(VGSCollectOnCreateViewInstanceListener listener) {
        this.listener = listener;
    }

    protected abstract String getHint();

    protected abstract T createInput(ThemedReactContext reactContext);

    @NonNull
    @Override
    protected VGSTextInputLayout createViewInstance(@NonNull ThemedReactContext reactContext) {
        layout = createLayout(reactContext);
        input = createInput(reactContext);
        layout.addView(input);
        listener.onCreateViewInstance(input);
        return layout;
    }

    @ReactProp(name = "fiendName")
    public void setFieldName(VGSTextInputLayout view, String text) {
        input.setFieldName(text);
    }

    @ReactProp(name = "fontSize")
    public void setFontSize(VGSTextInputLayout view, int size) {
        input.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
    }

    @ReactProp(name = "padding")
    public void setPadding(VGSTextInputLayout view, int padding) {
        int paddingDp = ResourceUtil.convertPxToDp(view.getContext(), padding);
        input.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
    }

    @ReactProp(name = "hint")
    public void setHint(VGSTextInputLayout view, String text) {
        view.setHint(text);
    }

    @ReactProp(name = "corners", defaultInt = 0)
    public void setBoxCornerRadius(VGSTextInputLayout view, int radius) {
        view.setBoxCornerRadius(radius, radius, radius, radius);
    }

    public String getFieldName() {
        return input == null ? null : input.getFieldName();
    }

    protected int toDp(Context context, int px) {
        return ResourceUtil.convertPxToDp(context, px);
    }

    private VGSTextInputLayout createLayout(ThemedReactContext reactContext) {
        VGSTextInputLayout layout = new VGSTextInputLayout(reactContext);
        layout.setBoxCornerRadius(DEFAULT_CORNER_RADIUS, DEFAULT_CORNER_RADIUS, DEFAULT_CORNER_RADIUS, DEFAULT_CORNER_RADIUS);
        layout.setHint(getHint());
        return layout;
    }
}