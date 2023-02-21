package com.collectrndemo.newarchitecture;

import android.app.Application;

import androidx.annotation.NonNull;

import com.collectrndemo.BuildConfig;
import com.collectrndemo.modules.collect.VGSCollectOnCreateViewInstanceListener;
import com.collectrndemo.modules.collect.VGSCollectPackage;
import com.collectrndemo.modules.collect.field.date.CollectCardExpirationDatePackage;
import com.collectrndemo.modules.collect.field.number.CollectCardNumberPackage;
import com.collectrndemo.modules.collect.scanner.ScanPackage;
import com.collectrndemo.modules.show.VGSShowOnCreateViewInstanceListener;
import com.collectrndemo.modules.show.VGSShowPackage;
import com.collectrndemo.modules.show.field.date.ShowCardExpirationDatePackage;
import com.collectrndemo.modules.show.field.number.ShowCardNumberPackage;
import com.collectrndemo.newarchitecture.components.MainComponentsRegistry;
import com.collectrndemo.newarchitecture.modules.MainApplicationTurboModuleManagerDelegate;
import com.facebook.react.PackageList;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactPackageTurboModuleManagerDelegate;
import com.facebook.react.bridge.JSIModulePackage;
import com.facebook.react.bridge.JSIModuleProvider;
import com.facebook.react.bridge.JSIModuleSpec;
import com.facebook.react.bridge.JSIModuleType;
import com.facebook.react.bridge.JavaScriptContextHolder;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.UIManager;
import com.facebook.react.fabric.ComponentFactory;
import com.facebook.react.fabric.CoreComponentsRegistry;
import com.facebook.react.fabric.FabricJSIModuleProvider;
import com.facebook.react.fabric.ReactNativeConfig;
import com.facebook.react.uimanager.ViewManagerRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A {@link ReactNativeHost} that helps you load everything needed for the New Architecture, both
 * TurboModule delegates and the Fabric Renderer.
 *
 * <p>Please note that this class is used ONLY if you opt-in for the New Architecture (see the
 * `newArchEnabled` property). Is ignored otherwise.
 */
public class MainApplicationReactNativeHost extends ReactNativeHost {
    public MainApplicationReactNativeHost(Application application) {
        super(application);
    }

    @Override
    public boolean getUseDeveloperSupport() {
        return BuildConfig.DEBUG;
    }

    @Override
    protected List<ReactPackage> getPackages() {
        List<ReactPackage> packages = new PackageList(this).getPackages();

        getCollectPackages(packages);
        getShowPackages(packages);

        return packages;
    }

    @Override
    protected String getJSMainModuleName() {
        return "index";
    }

    @NonNull
    @Override
    protected ReactPackageTurboModuleManagerDelegate.Builder
    getReactPackageTurboModuleManagerDelegateBuilder() {
        // Here we provide the ReactPackageTurboModuleManagerDelegate Builder. This is necessary
        // for the new architecture and to use TurboModules correctly.
        return new MainApplicationTurboModuleManagerDelegate.Builder();
    }

    @Override
    protected JSIModulePackage getJSIModulePackage() {
        return new JSIModulePackage() {
            @Override
            public List<JSIModuleSpec> getJSIModules(
                    final ReactApplicationContext reactApplicationContext,
                    final JavaScriptContextHolder jsContext) {
                final List<JSIModuleSpec> specs = new ArrayList<>();

                // Here we provide a new JSIModuleSpec that will be responsible of providing the
                // custom Fabric Components.
                specs.add(
                        new JSIModuleSpec() {
                            @Override
                            public JSIModuleType getJSIModuleType() {
                                return JSIModuleType.UIManager;
                            }

                            @Override
                            public JSIModuleProvider<UIManager> getJSIModuleProvider() {
                                final ComponentFactory componentFactory = new ComponentFactory();
                                CoreComponentsRegistry.register(componentFactory);

                                // Here we register a Components Registry.
                                // The one that is generated with the template contains no components
                                // and just provides you the one from React Native core.
                                MainComponentsRegistry.register(componentFactory);

                                final ReactInstanceManager reactInstanceManager = getReactInstanceManager();

                                ViewManagerRegistry viewManagerRegistry =
                                        new ViewManagerRegistry(
                                                reactInstanceManager.getOrCreateViewManagers(reactApplicationContext));

                                return new FabricJSIModuleProvider(
                                        reactApplicationContext,
                                        componentFactory,
                                        ReactNativeConfig.DEFAULT_CONFIG,
                                        viewManagerRegistry);
                            }
                        });
                return specs;
            }
        };
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
