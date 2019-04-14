package com.github.fo2rist.mclaren.dagger;

import com.github.fo2rist.mclaren.mvp.ImagePreviewContract;
import com.github.fo2rist.mclaren.ui.previewscreen.ImagePreviewFragment;
import com.github.fo2rist.mclaren.ui.previewscreen.ImagePreviewPresenter;
import dagger.Binds;
import dagger.Module;

@Module
abstract class PreviewFragmentModule {

    @Binds
    @Scopes.PerFragment
    abstract ImagePreviewContract.View provideView(ImagePreviewFragment view);

    @Binds
    @Scopes.PerFragment
    abstract ImagePreviewContract.Presenter providePresenter(ImagePreviewPresenter presenter);
}
