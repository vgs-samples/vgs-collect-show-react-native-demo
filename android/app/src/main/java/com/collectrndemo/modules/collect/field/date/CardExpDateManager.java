package com.collectrndemo.modules.collect.field.date;

import android.util.TypedValue;

import androidx.annotation.NonNull;

import com.collectrndemo.modules.collect.VGSCollectOnCreateViewInstanceListener;
import com.collectrndemo.modules.util.ResourceUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.verygoodsecurity.vgscollect.view.date.DatePickerMode;
import com.verygoodsecurity.vgscollect.widget.ExpirationDateEditText;
import com.verygoodsecurity.vgscollect.widget.VGSTextInputLayout;

@SuppressWarnings("unused")
public class CardExpDateManager extends ViewGroupManager<VGSTextInputLayout> {
    private ExpirationDateEditText editText;
    private VGSTextInputLayout vgsTextInputLayout;

    private final VGSCollectOnCreateViewInstanceListener listener;

    CardExpDateManager(VGSCollectOnCreateViewInstanceListener listener) {
        super();
        this.listener = listener;
    }

    @NonNull
    @Override
    public String getName() {
        return "CardExpDateLayout";
    }

    @NonNull
    @Override
    protected VGSTextInputLayout createViewInstance(@NonNull ThemedReactContext reactContext) {
        createVGSTextInputLayout(reactContext);
        createExpirationDateEditText(reactContext);

        return vgsTextInputLayout;
    }

    private void createVGSTextInputLayout(ThemedReactContext reactContext) {
        vgsTextInputLayout = new VGSTextInputLayout(reactContext);
        vgsTextInputLayout.setHint("Exp Date");
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

    @ReactProp(name = "corners", defaultInt = 0)
    public void setBoxCornerRadius(VGSTextInputLayout view, int radius) {
        view.setBoxCornerRadius(radius, radius, radius, radius);
    }

    private void createExpirationDateEditText(ThemedReactContext reactContext) {
        editText = new ExpirationDateEditText(reactContext);
        editText.setIsRequired(true);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        editText.setDateRegex("MM/yy");
        editText.setDatePickerMode(DatePickerMode.SPINNER);

        vgsTextInputLayout.addView(editText);

        listener.onCreateViewInstance(editText);
    }

    public ExpirationDateEditText getEditTextInstance() { // <-- returns the View instance
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