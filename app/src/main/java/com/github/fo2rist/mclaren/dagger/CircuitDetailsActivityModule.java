package com.github.fo2rist.mclaren.dagger;

import android.app.Activity;

import com.github.fo2rist.mclaren.ui.circuitsscreen.CircuitDetailsActivity;
import dagger.Binds;
import dagger.Module;

@Module(includes = AnalyticsModule.class)
abstract class CircuitDetailsActivityModule {

    @Binds
    @Scopes.PerActivity
    abstract Activity provideActivity(CircuitDetailsActivity activity);
}
