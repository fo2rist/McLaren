package com.github.fo2rist.mclaren.dagger;

import com.github.fo2rist.mclaren.mvp.ImagePreviewContract;
import com.github.fo2rist.mclaren.ui.presenters.ImagePreviewPresenter;
import dagger.Module;
import dagger.Provides;

@Module
class PreviewFragmentModule {
    @Provides
    @Scopes.PerFragment
    ImagePreviewContract.Presenter providePresenter(ImagePreviewPresenter presenter) {
        return presenter;
    }
}
