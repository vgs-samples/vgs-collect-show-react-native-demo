package com.collectrndemo.modules.collect.field.number;

import android.util.TypedValue;
import android.view.Gravity;

import androidx.annotation.NonNull;

import com.collectrndemo.modules.collect.VGSCollectOnCreateViewInstanceListener;
import com.collectrndemo.modules.util.ResourceUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.verygoodsecurity.vgscollect.widget.VGSCardNumberEditText;
import com.verygoodsecurity.vgscollect.widget.VGSTextInputLayout;

@SuppressWarnings("unused")
public class CardNumberManager extends ViewGroupManager<VGSTextInputLayout> {
    private VGSCardNumberEditText editText;
    private VGSTextInputLayout vgsTextInputLayout;

    private final VGSCollectOnCreateViewInstanceListener listener;

    CardNumberManager(VGSCollectOnCreateViewInstanceListener listener) {
        super();
        this.listener = listener;
    }

    @NonNull
    @Override
    public String getName() {
        return "CardNumberLayout";
    }

    @NonNull
    @Override
    public VGSTextInputLayout createViewInstance(@NonNull ThemedReactContext reactContext) {
        createVGSTextInputLayout(reactContext);
        createVGSCardNumberEditText(reactContext);

        return vgsTextInputLayout;
    }

    private void createVGSTextInputLayout(ThemedReactContext reactContext) {
        vgsTextInputLayout = new VGSTextInputLayout(reactContext);
    }

    @ReactProp(name = "padding")
    public void setPadding(VGSTextInputLayout view, int padding) {
        int paddingDp = ResourceUtil.convertPxToDp(view.getContext(), padding);
        editText.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
    }

    @ReactProp(name = "fontSize")
    public void setFontSize(VGSTextInputLayout view, int size) {
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
    }

    @ReactProp(name = "hint")
    public void setHint(VGSTextInputLayout view, String text) {
        view.setHint(text);
    }

    @ReactProp(name = "fiendName")
    public void setFieldName(VGSTextInputLayout view, String text) {
        editText.setFieldName(text);
    }

    @ReactProp(name = "corners")
    public void setBoxCornerRadius(VGSTextInputLayout view, int radius) {
        view.setBoxCornerRadius(radius, radius, radius, radius);
    }

    private void createVGSCardNumberEditText(ThemedReactContext reactContext) {
        editText = new VGSCardNumberEditText(reactContext);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        editText.setIsRequired(true);
        editText.setDivider('-');
        editText.setCardBrandIconGravity(Gravity.END);

        vgsTextInputLayout.addView(editText);

        listener.onCreateViewInstance(editText);
    }

    public VGSCardNumberEditText getEditTextInstance() { // <-- returns the View instance
        return editText;
    }

    public String getFieldName() {
        if (editText == null) {
            return "";
        } else {
            return editText.getFieldName();
        }
    }

}