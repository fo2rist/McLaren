package com.github.fo2rist.mclaren;

import android.app.Application;

import com.github.fo2rist.mclaren.dagger.DaggerAppComponent;
import com.github.fo2rist.mclaren.web.remoteconfig.RemoteConfigService;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import javax.inject.Inject;
import timber.log.Timber;

public class McLarenApplication extends Application implements HasAndroidInjector {
    @Inject
    protected RemoteConfigService remoteConfigService;

    @Inject
    DispatchingAndroidInjector<Object> androidInjector;

    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }

    /**
     * Unit Tests flag.
     * Descendants to override that to app know tests are executed.
     */
    protected boolean isInTestMode() {
        return false;
    }

    @Override
    public void onCreate() {
        DaggerAppComponent.builder().create(this).inject(this);
        super.onCreate();

        if (!isInTestMode()) {
            //Setup logger and crashlytics
            if (BuildConfig.DEBUG) {
                Timber.plant(new Timber.DebugTree());

                FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false);
                FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(false);
            }
            remoteConfigService.fetchConfig();
        }
    }
}
