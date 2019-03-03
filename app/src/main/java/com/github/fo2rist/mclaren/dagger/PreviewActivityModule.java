package com.github.fo2rist.mclaren.dagger;

import com.github.fo2rist.mclaren.mvp.PreviewContract;
import com.github.fo2rist.mclaren.ui.previewscreen.ImagePreviewFragment;
import com.github.fo2rist.mclaren.ui.previewscreen.PreviewPresenter;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class PreviewActivityModule {
    @Scopes.PerFragment
    @ContributesAndroidInjector(modules = PreviewFragmentModule.class)
    abstract ImagePreviewFragment imagePreviewFragmentInjector();

    @Binds
    @Scopes.PerActivity
    abstract PreviewContract.Presenter providePresenter(PreviewPresenter presenter);
}
