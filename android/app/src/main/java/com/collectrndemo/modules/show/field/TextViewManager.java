package com.collectrndemo.modules.show.field;

import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.collectrndemo.modules.show.VGSShowOnCreateViewInstanceListener;
import com.collectrndemo.modules.util.ResourceUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.verygoodsecurity.vgsshow.widget.VGSTextView;

@SuppressWarnings("unused")
public class TextViewManager extends ViewGroupManager<VGSTextView> {

    private VGSTextView textView;

    private final VGSShowOnCreateViewInstanceListener listener;

    TextViewManager(VGSShowOnCreateViewInstanceListener listener) {
        super();
        this.listener = listener;
    }

    @NonNull
    @Override
    public String getName() {
        return "VGSTextView";
    }

    @ReactProp(name = "contentPath")
    public void setContentPath(VGSTextView view, String text) {
        view.setContentPath(text);
        listener.onViewContentPathUpdated(view);
    }

    @ReactProp(name = "padding")
    public void setPadding(VGSTextView view, int padding) {
        int paddingDp = ResourceUtil.convertPxToDp(view.getContext(), padding);
        view.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
    }

    @ReactProp(name = "fontSize")
    public void setFontSize(VGSTextView view, int size) {
        view.setTextSize(size * Resources.getSystem().getDisplayMetrics().density);
    }

    @ReactProp(name = "hint")
    public void setHint(VGSTextView view, String text) {
        view.setHint(text);
    }

    @NonNull
    @Override
    protected VGSTextView createViewInstance(@NonNull ThemedReactContext reactContext) {
        textView = new VGSTextView(reactContext);

        listener.onCreateViewInstance(textView);

        return textView;
    }

    public String getContentPath() {
        if (textView == null) {
            return "";
        } else {
            return textView.getContentPath();
        }
    }
}