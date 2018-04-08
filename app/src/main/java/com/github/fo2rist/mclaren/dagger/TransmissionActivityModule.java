package com.github.fo2rist.mclaren.dagger;

import com.github.fo2rist.mclaren.mvp.TransmissionContract;
import com.github.fo2rist.mclaren.ui.transmissionscreen.TransmissionPresenter;
import dagger.Module;
import dagger.Provides;

@Module
abstract class TransmissionActivityModule {
    @Provides
    @Scopes.PerActivity
    static TransmissionContract.Presenter providePresenter(TransmissionPresenter presenter) {
        return presenter;
    }
}
