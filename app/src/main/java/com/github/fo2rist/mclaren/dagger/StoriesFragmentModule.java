package com.github.fo2rist.mclaren.dagger;

import com.github.fo2rist.mclaren.analytics.EventsLogger;
import com.github.fo2rist.mclaren.mvp.FeedContract;
import com.github.fo2rist.mclaren.repository.FeedRepository;
import com.github.fo2rist.mclaren.repository.FeedRepositoryEventBus;
import com.github.fo2rist.mclaren.ui.feedscreen.FeedPresenter;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

@Module
class StoriesFragmentModule {
    @Provides
    @Scopes.PerFragment
    FeedContract.Presenter providePresenter(
            @Named("storystream-feed") FeedRepository repository,
            FeedRepositoryEventBus eventBus,
            EventsLogger logger
    ) {
        return new FeedPresenter(repository, eventBus, logger);
    }
}
