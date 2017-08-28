package com.github.fo2rist.mclaren.dagger;

import com.github.fo2rist.mclaren.mvp.NewsfeedContract;
import com.github.fo2rist.mclaren.ui.NewsfeedPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class NewsfeedFragmentModule {
    @Provides
    @Scopes.PerFragment
    NewsfeedContract.Presenter providePresenter(NewsfeedPresenter presenter) {
        return presenter;
    }
}
