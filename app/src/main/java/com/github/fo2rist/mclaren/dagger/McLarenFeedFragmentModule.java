package com.github.fo2rist.mclaren.dagger;

import com.github.fo2rist.mclaren.analytics.EventsLoggerImpl;
import com.github.fo2rist.mclaren.mvp.FeedContract;
import com.github.fo2rist.mclaren.repository.RepositoryPubSubImpl;
import com.github.fo2rist.mclaren.repository.McLarenFeedRepositoryImpl;
import com.github.fo2rist.mclaren.ui.feedscreen.FeedPresenter;
import dagger.Module;
import dagger.Provides;

@Module
class McLarenFeedFragmentModule {
    @Provides
    @Scopes.PerFragment
    FeedContract.Presenter providePresenter(McLarenFeedRepositoryImpl repository,
            RepositoryPubSubImpl pubSub,
            EventsLoggerImpl logger) {
        return new FeedPresenter(repository, pubSub, logger);
    }
}
