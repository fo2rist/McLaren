package com.github.fo2rist.mclaren.dagger

import com.github.fo2rist.mclaren.mvp.FeedContract
import com.github.fo2rist.mclaren.repository.feed.FeedRepository
import com.github.fo2rist.mclaren.ui.feedscreen.FeedPresenter
import com.github.fo2rist.mclaren.ui.feedscreen.TwitterFeedFragment
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
interface TwitterFeedFragmentModule {
    @Binds
    @Scopes.PerFragment
    fun provideTwitterFeedView(view: TwitterFeedFragment): FeedContract.View

    @Binds
    @Scopes.PerFragment
    fun provideTwitterFeedRepository(@Named("twitter-feed") repository: FeedRepository): FeedRepository

    @Binds
    @Scopes.PerFragment
    fun providePresenter(presenter: FeedPresenter): FeedContract.Presenter
}
