package com.collectrndemo;

import android.app.Application;
import android.content.Context;

import com.collectrndemo.simple.modules.collect.VGSCollectOnCreateViewInstanceListener;
import com.collectrndemo.simple.modules.collect.VGSCollectPackage;
import com.collectrndemo.simple.modules.collect.field.date.CollectCardExpirationDatePackage;
import com.collectrndemo.simple.modules.collect.field.number.CollectCardNumberPackage;
import com.collectrndemo.simple.modules.collect.scanner.ScanPackage;
import com.collectrndemo.simple.modules.show.VGSShowOnCreateViewInstanceListener;
import com.collectrndemo.simple.modules.show.VGSShowPackage;
import com.collectrndemo.simple.modules.show.field.date.ShowCardExpirationDatePackage;
import com.collectrndemo.simple.modules.show.field.number.ShowCardNumberPackage;
import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint;
import com.facebook.react.defaults.DefaultReactNativeHost;
import com.facebook.soloader.SoLoader;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {

    private final ReactNativeHost mReactNativeHost =
            new DefaultReactNativeHost(this) {
                @Override
                public boolean getUseDeveloperSupport() {
                    return BuildConfig.DEBUG;
                }

                @Override
                protected List<ReactPackage> getPackages() {
                    @SuppressWarnings("UnnecessaryLocalVariable")
                    List<ReactPackage> packages = new PackageList(this).getPackages();

                    getCollectPackages(packages);
                    getShowPackages(packages);

                    return packages;
                }

                @Override
                protected String getJSMainModuleName() {
                    return "index";
                }

                @Override
                protected boolean isNewArchEnabled() {
                    return BuildConfig.IS_NEW_ARCHITECTURE_ENABLED;
                }

                @Override
                protected Boolean isHermesEnabled() {
                    return BuildConfig.IS_HERMES_ENABLED;
                }
            };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);

        if (BuildConfig.IS_NEW_ARCHITECTURE_ENABLED) {
            // If you opted-in for the New Architecture, we load the native entry point for this app.
            DefaultNewArchitectureEntryPoint.load();
        }
        ReactNativeFlipper.initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
    }

    private void getShowPackages(List<ReactPackage> packages) {
        // here we bind VGS secure fields with VGSShow
        VGSShowPackage show = new VGSShowPackage();
        VGSShowOnCreateViewInstanceListener listener = show.getListener();

        ReactPackage[] array = new ReactPackage[]{
                new ShowCardNumberPackage(listener),
                new ShowCardExpirationDatePackage(listener),
                show
        };

        packages.addAll(Arrays.asList(array));
    }

    private void getCollectPackages(List<ReactPackage> packages) {
        // here we bind VGS secure fields with VGSCollect
        VGSCollectPackage collect = new VGSCollectPackage();
        VGSCollectOnCreateViewInstanceListener listener = collect.getListener();

        ReactPackage[] array = new ReactPackage[]{
                new ScanPackage(),
                new CollectCardNumberPackage(listener),
                new CollectCardExpirationDatePackage(listener),
                collect
        };

        packages.addAll(Arrays.asList(array));
    }
}
