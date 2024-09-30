package com.collectrndemo

import android.app.Application
import com.collectrndemo.advanced.collect.VGSCollectAdvancedPackage
import com.collectrndemo.advanced.collect.view.VGSCollectCardViewPackage
import com.collectrndemo.advanced.show.VGSShowPackageAdvanced
import com.collectrndemo.advanced.show.view.VGSShowCardViewPackage
import com.collectrndemo.simple.modules.SharedConfig
import com.collectrndemo.simple.modules.collect.VGSCollectPackage
import com.collectrndemo.simple.modules.collect.field.date.CollectCardExpirationDatePackage
import com.collectrndemo.simple.modules.collect.field.number.CollectCardNumberPackage
import com.collectrndemo.simple.modules.show.VGSShowPackage
import com.collectrndemo.simple.modules.show.field.date.ShowCardExpirationDatePackage
import com.collectrndemo.simple.modules.show.field.number.ShowCardNumberPackage
import com.facebook.react.PackageList
import com.facebook.react.ReactApplication
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.load
import com.facebook.react.defaults.DefaultReactNativeHost
import com.facebook.soloader.SoLoader
import com.microblink.blinkcard.MicroblinkSDK

class MainApplication : Application(), ReactApplication {

    override val reactNativeHost: ReactNativeHost = object : DefaultReactNativeHost(this) {

        override val isNewArchEnabled: Boolean
            get() = BuildConfig.IS_NEW_ARCHITECTURE_ENABLED

        override val isHermesEnabled: Boolean
            get() = BuildConfig.IS_HERMES_ENABLED

        override fun getUseDeveloperSupport(): Boolean {
            return BuildConfig.DEBUG
        }

        override fun getJSMainModuleName(): String {
            return "index"
        }

        override fun getPackages(): List<ReactPackage> {
            val packages: MutableList<ReactPackage> = PackageList(this).packages
            getSimpleExamplePackages(packages)
            getAdvancedExamplePackages(packages)
            return packages
        }
    }

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)
        if (BuildConfig.IS_NEW_ARCHITECTURE_ENABLED) {
            // If you opted-in for the New Architecture, we load the native entry point for this app.
            load()
        }
        if (SharedConfig.SCANNER_API_KEY.isNotEmpty()) {
            MicroblinkSDK.setLicenseKey(SharedConfig.SCANNER_API_KEY, this)
        }
    }

    private fun getSimpleExamplePackages(packages: MutableList<ReactPackage>) {
        val collect = VGSCollectPackage()
        val show = VGSShowPackage()
        packages.addAll(
            listOf(
                CollectCardNumberPackage(collect),
                CollectCardExpirationDatePackage(collect),
                collect,
                ShowCardNumberPackage(show),
                ShowCardExpirationDatePackage(show),
                show
            )
        )
    }

    private fun getAdvancedExamplePackages(packages: MutableList<ReactPackage>) {
        packages.addAll(
            listOf(
                VGSCollectCardViewPackage(),
                VGSCollectAdvancedPackage(),
                VGSShowCardViewPackage(),
                VGSShowPackageAdvanced()
            )
        )
    }
}
