package com.collectrndemo.modules.show.field.date;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.collectrndemo.R;
import com.collectrndemo.modules.show.VGSShowOnCreateViewInstanceListener;
import com.collectrndemo.modules.show.field.core.BaseShowView;
import com.facebook.react.uimanager.ThemedReactContext;
import com.verygoodsecurity.vgsshow.widget.VGSTextView;

@SuppressWarnings("unused")
public class CardExpirationDateManager extends BaseShowView {

    CardExpirationDateManager(VGSShowOnCreateViewInstanceListener listener) {
        super(listener);
    }

    @Override
    protected VGSTextView createView(@NonNull ThemedReactContext reactContext) {
        VGSTextView view = new VGSTextView(reactContext);
        int padding = toDp(reactContext, 8);
        view.setPadding(padding, padding, padding, padding);
        view.setBackground(ContextCompat.getDrawable(reactContext, R.drawable.border_with_radius));
        view.setContentPath("json.payment_card_expiration_date");
        view.setHint("Revealed Expiration Number");
        return view;
    }

    @NonNull
    @Override
    public String getName() {
        return "VGSExpDateLabel";
    }
}