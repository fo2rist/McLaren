package com.github.fo2rist.mclaren.dagger

import com.github.fo2rist.mclaren.mvp.FeedContract
import com.github.fo2rist.mclaren.ui.feedscreen.FeedPresenter
import dagger.Binds
import dagger.Module

@Module
internal interface BaseFeedFragmentModule {

    @Binds
    @Scopes.PerFragment
    fun providePresenter(presenter: FeedPresenter): FeedContract.Presenter
}
