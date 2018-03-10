package com.github.fo2rist.mclaren.dagger;

import com.github.fo2rist.mclaren.mvp.FeedContract;
import com.github.fo2rist.mclaren.repository.FeedRepositoryPubSubImpl;
import com.github.fo2rist.mclaren.repository.StoryStreamRepositoryImpl;
import com.github.fo2rist.mclaren.ui.presenters.FeedPresenter;
import dagger.Module;
import dagger.Provides;

@Module
class StoriesFragmentModule {
    @Provides
    @Scopes.PerFragment
    FeedContract.Presenter providePresenter(StoryStreamRepositoryImpl repository, FeedRepositoryPubSubImpl pubSub) {
        return new FeedPresenter(repository, pubSub);
    }
}
