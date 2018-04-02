package com.github.fo2rist.mclaren.dagger;

import com.github.fo2rist.mclaren.mvp.MainScreenContract;
import com.github.fo2rist.mclaren.ui.McLarenFeedFragment;
import com.github.fo2rist.mclaren.ui.StoriesFeedFragment;
import com.github.fo2rist.mclaren.ui.presenters.MainPresenter;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class MainActivityModule {
    @Scopes.PerFragment
    @ContributesAndroidInjector(modules = McLarenFeedFragmentModule.class)
    abstract McLarenFeedFragment mcLarenFeedFragmentInjector();

    @Scopes.PerFragment
    @ContributesAndroidInjector(modules = StoriesFragmentModule.class)
    abstract StoriesFeedFragment storiesFragmentInjector();

    @Provides
    @Scopes.PerActivity
    static MainScreenContract.Presenter providePresenter(MainPresenter presenter) {
        return presenter;
    }
}
