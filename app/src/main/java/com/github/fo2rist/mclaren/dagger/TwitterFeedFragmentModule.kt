package com.github.fo2rist.mclaren.dagger

import com.github.fo2rist.mclaren.mvp.FeedContract
import com.github.fo2rist.mclaren.repository.FeedRepository
import com.github.fo2rist.mclaren.ui.feedscreen.TwitterFeedFragment
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module(includes = [BaseFeedFragmentModule::class])
interface TwitterFeedFragmentModule {
    @Binds
    @Scopes.PerFragment
    fun provideMcLarenFeedView(view: TwitterFeedFragment): FeedContract.View

    @Binds
    @Scopes.PerFragment
    fun provideMcLarenFeedRepository(@Named("twitter-feed") repository: FeedRepository): FeedRepository
}
