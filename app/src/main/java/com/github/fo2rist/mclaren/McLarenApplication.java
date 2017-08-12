package com.github.fo2rist.mclaren;


import android.app.Application;

import timber.log.Timber;

public class McLarenApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Setup logger
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
