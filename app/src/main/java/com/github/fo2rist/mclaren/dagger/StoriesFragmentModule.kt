package com.github.fo2rist.mclaren.dagger

import com.github.fo2rist.mclaren.analytics.EventsLogger
import com.github.fo2rist.mclaren.mvp.FeedContract
import com.github.fo2rist.mclaren.repository.FeedRepository
import com.github.fo2rist.mclaren.repository.FeedRepositoryEventBus
import com.github.fo2rist.mclaren.ui.feedscreen.FeedPresenter
import com.github.fo2rist.mclaren.ui.feedscreen.StoriesFeedFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
internal class StoriesFragmentModule {

    @Provides
    @Scopes.PerFragment
    fun provideStoriesFeedView(view: StoriesFeedFragment): FeedContract.View = view

    @Provides
    @Scopes.PerFragment
    fun providePresenter(
        view: FeedContract.View,
        @Named("storystream-feed") repository: FeedRepository,
        eventBus: FeedRepositoryEventBus,
        logger: EventsLogger
    ): FeedContract.Presenter {
        return FeedPresenter(view, repository, eventBus, logger)
    }
}
