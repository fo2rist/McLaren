package com.github.fo2rist.mclaren.dagger

import com.github.fo2rist.mclaren.mvp.FeedContract
import com.github.fo2rist.mclaren.repository.FeedRepository
import com.github.fo2rist.mclaren.ui.feedscreen.StoriesFeedFragment
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module(includes = [BaseFeedFragmentModule::class])
internal interface StoriesFragmentModule {

    @Binds
    @Scopes.PerFragment
    fun provideStoriesFeedView(view: StoriesFeedFragment): FeedContract.View

    @Binds
    @Scopes.PerFragment
    fun provideStoryStreamRepository(@Named("storystream-feed") repository: FeedRepository): FeedRepository
}
