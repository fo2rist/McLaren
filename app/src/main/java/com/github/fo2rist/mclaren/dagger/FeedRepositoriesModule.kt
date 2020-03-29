package com.github.fo2rist.mclaren.dagger

import com.github.fo2rist.mclaren.repository.feed.FeedRepository
import com.github.fo2rist.mclaren.repository.feed.FeedRepositoryEventBus
import com.github.fo2rist.mclaren.repository.feed.McLarenFeedRepositoryImpl
import com.github.fo2rist.mclaren.repository.RepositoryEventBusImpl
import com.github.fo2rist.mclaren.repository.feed.StoryStreamRepositoryImpl
import com.github.fo2rist.mclaren.repository.feed.TwitterRepositoryImpl
import com.github.fo2rist.mclaren.web.feed.FeedHistoryPredictor
import com.github.fo2rist.mclaren.web.feed.McLarenFeedHistoryPredictor
import com.github.fo2rist.mclaren.web.feed.McLarenFeedWebService
import com.github.fo2rist.mclaren.web.feed.McLarenWebServiceImpl
import com.github.fo2rist.mclaren.web.feed.StoryStreamWebService
import com.github.fo2rist.mclaren.web.feed.StoryStreamWebServiceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Named
import javax.inject.Singleton

@Module
internal interface FeedRepositoriesModule {
    @Binds
    @Named("mclaren-feed")
    @Singleton
    fun provideMcLarenRepository(repository: McLarenFeedRepositoryImpl): FeedRepository

    @Binds
    @Singleton
    fun provideMcLarenFeedWebservice(webService: McLarenWebServiceImpl): McLarenFeedWebService

    @Binds
    @Singleton
    fun provideHistoryPredictor(historyPredictor: McLarenFeedHistoryPredictor): FeedHistoryPredictor


    @Binds
    @Named("storystream-feed")
    @Singleton
    fun provideStoryStreamRepository(repository: StoryStreamRepositoryImpl): FeedRepository

    @Binds
    @Singleton
    fun provideStoryStreamWebservice(webService: StoryStreamWebServiceImpl): StoryStreamWebService


    @Binds
    @Named("twitter-feed")
    @Singleton
    fun provideTwitterRepository(repository: TwitterRepositoryImpl): FeedRepository


    @Binds
    @Singleton
    fun provideFeedRepositoryEventBus(eventBus: RepositoryEventBusImpl): FeedRepositoryEventBus
}
