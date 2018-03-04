package com.github.fo2rist.mclaren.dagger;

import com.github.fo2rist.mclaren.mvp.NewsfeedContract;
import com.github.fo2rist.mclaren.repository.FeedRepositoryPubSubImpl;
import com.github.fo2rist.mclaren.repository.McLarenFeedRepositoryImpl;
import com.github.fo2rist.mclaren.ui.presenters.NewsfeedPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class NewsfeedFragmentModule {
    @Provides
    @Scopes.PerFragment
    NewsfeedContract.Presenter providePresenter(McLarenFeedRepositoryImpl repository, FeedRepositoryPubSubImpl pubSub) {
        return new NewsfeedPresenter(repository, pubSub);
    }
}
