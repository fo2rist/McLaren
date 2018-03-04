package com.github.fo2rist.mclaren.dagger;

import com.github.fo2rist.mclaren.ui.NewsfeedFragment;
import com.github.fo2rist.mclaren.ui.StoriesFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {
    @Scopes.PerFragment
    @ContributesAndroidInjector(modules = NewsfeedFragmentModule.class)
    abstract NewsfeedFragment newsfeedFragmentInjector();

    @Scopes.PerFragment
    @ContributesAndroidInjector(modules = StoriesFragmentModule.class)
    abstract StoriesFragment storiesFragmentInjector();
}
