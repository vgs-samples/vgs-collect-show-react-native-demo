package com.collectrndemo.simple.modules.collect.field.date;

import android.graphics.Color;
import android.util.TypedValue;

import androidx.annotation.NonNull;

import com.collectrndemo.simple.modules.SharedConfig;
import com.collectrndemo.simple.modules.collect.VGSCollectOnCreateViewInstanceListener;
import com.collectrndemo.simple.modules.collect.field.core.BaseCollectView;
import com.collectrndemo.simple.modules.util.ResourceUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.verygoodsecurity.api.cardio.ScanActivity;
import com.verygoodsecurity.vgscollect.view.date.DatePickerMode;
import com.verygoodsecurity.vgscollect.widget.ExpirationDateEditText;

public class CardExpirationDateManager extends BaseCollectView<ExpirationDateEditText> {

    CardExpirationDateManager(VGSCollectOnCreateViewInstanceListener listener) {
        super(listener);
    }

    @Override
    protected int scanType() {
        return ScanActivity.CARD_EXP_DATE;
    }

    @NonNull
    @Override
    public String getName() {
        return "VGSExpDateTextField";
    }

    @Override
    protected String getHint() {
        return "Expiration Date";
    }

    @Override
    protected ExpirationDateEditText createInput(ThemedReactContext reactContext) {
        ExpirationDateEditText input = new ExpirationDateEditText(reactContext);
        int padding = ResourceUtil.convertPxToDp(reactContext, 8);
        input.setPadding(padding, input.getPaddingTop(), padding, input.getPaddingBottom());
        input.setTextColor(Color.BLACK);
        input.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        input.setDatePickerMode(DatePickerMode.SPINNER);
        input.setDateRegex("MM/yy");
        input.setFieldName(SharedConfig.EXPIRATION_DATE_FIELD_NAME);
        input.setIsRequired(true);
        return input;
    }
}