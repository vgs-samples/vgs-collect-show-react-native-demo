package com.collectrndemo.modules.show;

import com.verygoodsecurity.vgsshow.widget.VGSTextView;

public interface VGSShowOnCreateViewInstanceListener {
    void onCreateViewInstance(VGSTextView view);

    void onViewContentPathUpdated(VGSTextView view);
}