package com.github.fo2rist.mclaren.dagger;

import com.github.fo2rist.mclaren.ui.McLarenFeedFragment;
import com.github.fo2rist.mclaren.ui.StoriesFeedFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {
    @Scopes.PerFragment
    @ContributesAndroidInjector(modules = McLarenFeedFragmentModule.class)
    abstract McLarenFeedFragment newsfeedFragmentInjector();

    @Scopes.PerFragment
    @ContributesAndroidInjector(modules = StoriesFragmentModule.class)
    abstract StoriesFeedFragment storiesFragmentInjector();
}
