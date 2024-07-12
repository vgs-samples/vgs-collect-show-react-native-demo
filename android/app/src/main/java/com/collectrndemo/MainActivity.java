package com.collectrndemo;

import android.content.Intent;

import com.collectrndemo.simple.modules.collect.VGSCollectModule;
import com.collectrndemo.simple.modules.collect.VGSCollectPackage;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint;
import com.facebook.react.defaults.DefaultReactActivityDelegate;

import java.util.List;

public class MainActivity extends ReactActivity {

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ReactInstanceManager m = getReactInstanceManager();
        List<ReactPackage> l = m.getPackages();
        for (int i = 0; i < l.size(); i++) {
            ReactPackage rp = l.get(i);
            if (rp instanceof VGSCollectPackage) {
                VGSCollectModule module = ((VGSCollectPackage) rp).getVGSCollectModule();
                module.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    /**
     * Returns the name of the main component registered from JavaScript. This is used to schedule
     * rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "AwesomeProject";
    }

    /**
     * Returns the instance of the {@link ReactActivityDelegate}. Here we use a util class {@link
     * DefaultReactActivityDelegate} which allows you to easily enable Fabric and Concurrent React
     * (aka React 18) with two boolean flags.
     */
    @Override
    protected ReactActivityDelegate createReactActivityDelegate() {
        return new DefaultReactActivityDelegate(
                this,
                getMainComponentName(),
                // If you opted-in for the New Architecture, we enable the Fabric Renderer.
                DefaultNewArchitectureEntryPoint.getFabricEnabled()
        );
    }
}
