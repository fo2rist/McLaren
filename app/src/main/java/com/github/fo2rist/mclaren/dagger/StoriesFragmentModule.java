package com.github.fo2rist.mclaren.dagger;

import com.github.fo2rist.mclaren.mvp.NewsfeedContract;
import com.github.fo2rist.mclaren.repository.FeedRepositoryPubSubImpl;
import com.github.fo2rist.mclaren.repository.StoryStreamRepositoryImpl;
import com.github.fo2rist.mclaren.ui.presenters.NewsfeedPresenter;
import dagger.Module;
import dagger.Provides;

@Module
class StoriesFragmentModule {
    @Provides
    @Scopes.PerFragment
    NewsfeedContract.Presenter providePresenter(StoryStreamRepositoryImpl repository, FeedRepositoryPubSubImpl pubSub) {
        return new NewsfeedPresenter(repository, pubSub);
    }
}
