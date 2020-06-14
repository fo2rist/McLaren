package com.github.fo2rist.mclaren.dagger;

import android.app.Activity;

import com.github.fo2rist.mclaren.mvp.MainScreenContract;
import com.github.fo2rist.mclaren.ui.MainActivity;
import com.github.fo2rist.mclaren.ui.MainPresenter;
import com.github.fo2rist.mclaren.ui.circuitsscreen.CircuitsFragment;
import com.github.fo2rist.mclaren.ui.driversscreen.DriverSubFragment;
import com.github.fo2rist.mclaren.ui.driversscreen.DriversFragment;
import com.github.fo2rist.mclaren.ui.feedscreen.McLarenFeedFragment;
import com.github.fo2rist.mclaren.ui.feedscreen.StoriesFeedFragment;
import com.github.fo2rist.mclaren.ui.feedscreen.TwitterFeedFragment;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = AnalyticsModule.class)
abstract class MainActivityModule {
    @Scopes.PerFragment
    @ContributesAndroidInjector(modules = McLarenFeedFragmentModule.class)
    abstract McLarenFeedFragment mcLarenFeedFragmentInjector();

    @Scopes.PerFragment
    @ContributesAndroidInjector(modules = StoriesFragmentModule.class)
    abstract StoriesFeedFragment storiesFragmentInjector();

    @Scopes.PerFragment
    @ContributesAndroidInjector(modules = TwitterFeedFragmentModule.class)
    abstract TwitterFeedFragment twitterFeedFragmentInjector();

    @Scopes.PerFragment
    @ContributesAndroidInjector
    abstract CircuitsFragment circuitsFragmentInjector();

    @Scopes.PerFragment
    @ContributesAndroidInjector
    abstract DriversFragment driversFragmentInjector();

    @Scopes.PerFragment
    @ContributesAndroidInjector
    abstract DriverSubFragment driverSubFragmentInjector();

    @Binds
    @Scopes.PerActivity
    abstract Activity provideActivity(MainActivity activity);

    @Binds
    @Scopes.PerActivity
    abstract MainScreenContract.View provideView(MainActivity view);

    @Binds
    @Scopes.PerActivity
    abstract MainScreenContract.Presenter providePresenter(MainPresenter presenter);
}
