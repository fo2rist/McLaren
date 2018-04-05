package com.github.fo2rist.mclaren.dagger;

import com.github.fo2rist.mclaren.ui.ImagePreviewFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class PreviewActivityModule {
    @Scopes.PerFragment
    @ContributesAndroidInjector(modules = PreviewFragmentModule.class)
    abstract ImagePreviewFragment imagePreviewFragmentInjector();
}
