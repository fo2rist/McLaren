package com.github.fo2rist.mclaren;


import android.app.Activity;
import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.github.fo2rist.mclaren.dagger.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;
import timber.log.Timber;

public class McLarenApplication extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }

    @Override
    public void onCreate() {
        DaggerAppComponent.builder().create(this).inject(this);
        super.onCreate();

        //Setup logger and crashlytics
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Fabric.with(this,
                    new Crashlytics(),
                    new Answers());
        }
    }
}
