package com.github.fo2rist.mclaren.dagger

import com.github.fo2rist.mclaren.mvp.FeedContract
import com.github.fo2rist.mclaren.repository.feed.FeedRepository
import com.github.fo2rist.mclaren.ui.feedscreen.FeedPresenter
import com.github.fo2rist.mclaren.ui.feedscreen.McLarenFeedFragment
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
internal interface McLarenFeedFragmentModule {

    @Binds
    @Scopes.PerFragment
    fun provideMcLarenFeedView(view: McLarenFeedFragment): FeedContract.View

    @Binds
    @Scopes.PerFragment
    fun provideMcLarenFeedRepository(@Named("mclaren-feed") repository: FeedRepository): FeedRepository

    @Binds
    @Scopes.PerFragment
    fun providePresenter(presenter: FeedPresenter): FeedContract.Presenter
}
