package com.collectrndemo.simple.modules.show.field.date;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.collectrndemo.R;
import com.collectrndemo.simple.modules.SharedConfig;
import com.collectrndemo.simple.modules.show.VGSShowOnCreateViewInstanceListener;
import com.collectrndemo.simple.modules.show.field.core.BaseShowView;
import com.collectrndemo.simple.modules.util.ResourceUtil;
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
        int padding = ResourceUtil.convertPxToDp(reactContext, 8);
        view.setPadding(padding, padding, padding, padding);
        view.setBackground(ContextCompat.getDrawable(reactContext, R.drawable.border_with_radius));
        view.setContentPath(SharedConfig.EXPIRATION_DATE_CONTENT_PATH);
        view.setHint("Revealed Expiration Number");
        return view;
    }

    @NonNull
    @Override
    public String getName() {
        return "VGSExpDateLabel";
    }
}