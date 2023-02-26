package com.collectrndemo.simple.modules.show.field.number;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.collectrndemo.R;
import com.collectrndemo.simple.modules.SharedConfig;
import com.collectrndemo.simple.modules.show.VGSShowOnCreateViewInstanceListener;
import com.collectrndemo.simple.modules.show.field.core.BaseShowView;
import com.collectrndemo.simple.modules.util.ResourceUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.verygoodsecurity.vgsshow.widget.VGSTextView;

import kotlin.text.Regex;

@SuppressWarnings("unused")
public class CardNumberManager extends BaseShowView {

    CardNumberManager(VGSShowOnCreateViewInstanceListener listener) {
        super(listener);
    }

    @Override
    protected VGSTextView createView(@NonNull ThemedReactContext reactContext) {
        VGSTextView view = new VGSTextView(reactContext);
        int padding = ResourceUtil.convertPxToDp(reactContext, 8);
        view.setPadding(padding, padding, padding, padding);
        view.setBackground(ContextCompat.getDrawable(reactContext, R.drawable.border_with_radius));
        view.setContentPath(SharedConfig.CARD_NUMBER_CONTENT_PATH);
        view.setHint("Revealed Card Number");
        view.addTransformationRegex(new Regex("(\\d{4})(\\d{4})(\\d{4})(\\d{4})"), "$1 $2 $3 $4");
        return view;
    }

    @NonNull
    @Override
    public String getName() {
        return "VGSCardLabel";
    }
}