package com.collectrndemo.modules.show.field.core;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.collectrndemo.modules.show.VGSShowOnCreateViewInstanceListener;
import com.collectrndemo.modules.util.ResourceUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.verygoodsecurity.vgsshow.widget.VGSTextView;

public abstract class BaseShowView extends ViewGroupManager<VGSTextView> {

    private VGSTextView view;

    private final VGSShowOnCreateViewInstanceListener listener;

    public BaseShowView(VGSShowOnCreateViewInstanceListener listener) {
        this.listener = listener;
    }

    protected abstract VGSTextView createView(@NonNull ThemedReactContext reactContext);

    @ReactProp(name = "contentPath")
    public void setContentPath(VGSTextView view, String text) {
        view.setContentPath(text);
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

    public String getContentPath() {
        return view == null ? null : view.getContentPath();
    }

    @NonNull
    @Override
    protected VGSTextView createViewInstance(@NonNull ThemedReactContext reactContext) {
        view = createView(reactContext);
        listener.onCreateViewInstance(view);
        return view;
    }

    protected int toDp(Context context, int px) {
        return ResourceUtil.convertPxToDp(context, px);
    }
}
