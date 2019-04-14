package com.github.fo2rist.mclaren.dagger

import com.github.fo2rist.mclaren.mvp.FeedContract
import com.github.fo2rist.mclaren.repository.FeedRepository
import com.github.fo2rist.mclaren.ui.feedscreen.McLarenFeedFragment
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module(includes = [BaseFeedFragmentModule::class])
internal interface McLarenFeedFragmentModule {

    @Binds
    @Scopes.PerFragment
    fun provideMcLarenFeedView(view: McLarenFeedFragment): FeedContract.View

    @Binds
    @Scopes.PerFragment
    fun provideMcLarenFeedRepository(@Named("mclaren-feed") repository: FeedRepository): FeedRepository
}
