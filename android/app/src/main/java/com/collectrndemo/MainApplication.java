package com.collectrndemo;

import android.app.Application;
import android.content.Context;

import com.collectrndemo.modules.collect.VGSCollectOnCreateViewInstanceListener;
import com.collectrndemo.modules.collect.VGSCollectPackage;
import com.collectrndemo.modules.collect.field.date.CardExpDatePackage;
import com.collectrndemo.modules.collect.field.number.CardNumberPackage;
import com.collectrndemo.modules.collect.scanner.ScanPackage;
import com.collectrndemo.modules.show.VGSShowOnCreateViewInstanceListener;
import com.collectrndemo.modules.show.VGSShowPackage;
import com.collectrndemo.modules.show.field.TextViewPackage;
import com.collectrndemo.newarchitecture.MainApplicationReactNativeHost;
import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.config.ReactFeatureFlags;
import com.facebook.soloader.SoLoader;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {

    private final ReactNativeHost mReactNativeHost =
            new ReactNativeHost(this) {
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
            };

    private final ReactNativeHost mNewArchitectureNativeHost =
            new MainApplicationReactNativeHost(this);

    @Override
    public ReactNativeHost getReactNativeHost() {
        if (BuildConfig.IS_NEW_ARCHITECTURE_ENABLED) {
            return mNewArchitectureNativeHost;
        } else {
            return mReactNativeHost;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // If you opted-in for the New Architecture, we enable the TurboModule system
        ReactFeatureFlags.useTurboModules = BuildConfig.IS_NEW_ARCHITECTURE_ENABLED;
        SoLoader.init(this, /* native exopackage */ false);
        initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
    }

    /**
     * Loads Flipper in React Native templates. Call this in the onCreate method with something like
     * initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
     *
     * @param context
     * @param reactInstanceManager
     */
    private static void initializeFlipper(
            Context context, ReactInstanceManager reactInstanceManager) {
        if (BuildConfig.DEBUG) {
            try {
        /*
         We use reflection here to pick up the class that initializes Flipper,
        since Flipper library is not available in release mode
        */
                Class<?> aClass = Class.forName("com.collectrndemo.ReactNativeFlipper");
                aClass
                        .getMethod("initializeFlipper", Context.class, ReactInstanceManager.class)
                        .invoke(null, context, reactInstanceManager);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void getShowPackages(List<ReactPackage> packages) {
        // here we bind VGS secure fields with VGSShow
        VGSShowPackage show = new VGSShowPackage();
        VGSShowOnCreateViewInstanceListener listener = show.getListener();

        ReactPackage[] array = new ReactPackage[]{
                new TextViewPackage(listener),
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
                new CardNumberPackage(listener),
                new CardExpDatePackage(listener),
                collect
        };

        packages.addAll(Arrays.asList(array));
    }
}
