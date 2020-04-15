package com.github.fo2rist.mclaren.dagger

import com.github.fo2rist.mclaren.mvp.FeedContract
import com.github.fo2rist.mclaren.repository.feed.FeedRepository
import com.github.fo2rist.mclaren.ui.feedscreen.FeedPresenter
import com.github.fo2rist.mclaren.ui.feedscreen.StoriesFeedFragment
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
internal interface StoriesFragmentModule {

    @Binds
    @Scopes.PerFragment
    fun provideStoriesFeedView(view: StoriesFeedFragment): FeedContract.View

    @Binds
    @Scopes.PerFragment
    fun provideStoryStreamRepository(@Named("storystream-feed") repository: FeedRepository): FeedRepository

    @Binds
    @Scopes.PerFragment
    fun providePresenter(presenter: FeedPresenter): FeedContract.Presenter
}
