package com.github.fo2rist.mclaren.dagger;

import com.github.fo2rist.mclaren.mvp.TransmissionContract;
import com.github.fo2rist.mclaren.ui.transmissionscreen.TransmissionPresenter;
import dagger.Binds;
import dagger.Module;

@Module
abstract class TransmissionActivityModule {
    @Binds
    @Scopes.PerActivity
    abstract TransmissionContract.Presenter providePresenter(TransmissionPresenter presenter);
}
