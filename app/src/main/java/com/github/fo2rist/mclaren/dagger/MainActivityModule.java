package com.github.fo2rist.mclaren.dagger;

import com.github.fo2rist.mclaren.mvp.MainScreenContract;
import com.github.fo2rist.mclaren.ui.MainPresenter;
import com.github.fo2rist.mclaren.ui.circuitsscreen.CircuitsFragment;
import com.github.fo2rist.mclaren.ui.driversscreen.DriverSubFragment;
import com.github.fo2rist.mclaren.ui.feedscreen.McLarenFeedFragment;
import com.github.fo2rist.mclaren.ui.feedscreen.StoriesFeedFragment;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class MainActivityModule {
    @Scopes.PerFragment
    @ContributesAndroidInjector(modules = McLarenFeedFragmentModule.class)
    abstract McLarenFeedFragment mcLarenFeedFragmentInjector();

    @Scopes.PerFragment
    @ContributesAndroidInjector(modules = StoriesFragmentModule.class)
    abstract StoriesFeedFragment storiesFragmentInjector();

    @Scopes.PerFragment
    @ContributesAndroidInjector
    abstract CircuitsFragment circuitsFragmentInjector();

    @Scopes.PerFragment
    @ContributesAndroidInjector
    abstract DriverSubFragment driverSubFragmentInjector();

    @Binds
    @Scopes.PerActivity
    abstract MainScreenContract.Presenter providePresenter(MainPresenter presenter);
}
