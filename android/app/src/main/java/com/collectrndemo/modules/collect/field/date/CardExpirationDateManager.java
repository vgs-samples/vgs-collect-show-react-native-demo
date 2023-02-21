package com.collectrndemo.modules.collect.field.date;

import android.graphics.Color;
import android.util.TypedValue;

import androidx.annotation.NonNull;

import com.collectrndemo.modules.collect.VGSCollectOnCreateViewInstanceListener;
import com.collectrndemo.modules.collect.field.core.BaseCollectView;
import com.facebook.react.uimanager.ThemedReactContext;
import com.verygoodsecurity.vgscollect.view.date.DatePickerMode;
import com.verygoodsecurity.vgscollect.widget.ExpirationDateEditText;

public class CardExpirationDateManager extends BaseCollectView<ExpirationDateEditText> {

    CardExpirationDateManager(VGSCollectOnCreateViewInstanceListener listener) {
        super(listener);
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
        int padding = toDp(reactContext, 8);
        input.setPadding(padding, input.getPaddingTop(), padding, input.getPaddingBottom());
        input.setTextColor(Color.BLACK);
        input.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        input.setDatePickerMode(DatePickerMode.SPINNER);
        input.setDateRegex("MM/yy");
        input.setFieldName("card_expirationDate");
        input.setIsRequired(true);
        return input;
    }
}