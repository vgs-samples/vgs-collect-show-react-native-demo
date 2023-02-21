package com.collectrndemo.modules.collect.field.number;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;

import androidx.annotation.NonNull;

import com.collectrndemo.modules.collect.VGSCollectOnCreateViewInstanceListener;
import com.collectrndemo.modules.collect.field.core.BaseCollectView;
import com.facebook.react.uimanager.ThemedReactContext;
import com.verygoodsecurity.vgscollect.widget.VGSCardNumberEditText;

public class CardNumberManager extends BaseCollectView<VGSCardNumberEditText> {

    CardNumberManager(VGSCollectOnCreateViewInstanceListener listener) {
        super(listener);
    }

    @NonNull
    @Override
    public String getName() {
        return "VGSCardTextField";
    }

    @Override
    protected String getHint() {
        return "Card Number";
    }

    @Override
    protected VGSCardNumberEditText createInput(ThemedReactContext reactContext) {
        VGSCardNumberEditText input = new VGSCardNumberEditText(reactContext);
        int padding = toDp(reactContext, 8);
        input.setPadding(padding, input.getPaddingTop(), padding, input.getPaddingBottom());
        input.setTextColor(Color.BLACK);
        input.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        input.setDivider(' ');
        input.setCardBrandIconGravity(Gravity.END);
        input.setFieldName("card_number");
        input.setIsRequired(true);
        return input;
    }
}