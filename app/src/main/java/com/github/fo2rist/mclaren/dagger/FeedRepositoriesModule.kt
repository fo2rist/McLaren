package com.github.fo2rist.mclaren.dagger

import com.github.fo2rist.mclaren.repository.RepositoryEventBusImpl
import com.github.fo2rist.mclaren.repository.converters.FeedConverter
import com.github.fo2rist.mclaren.repository.converters.McLarenFeedConverter
import com.github.fo2rist.mclaren.repository.converters.StoryStreamConverter
import com.github.fo2rist.mclaren.repository.converters.TwitterConverter
import com.github.fo2rist.mclaren.repository.feed.FeedRepository
import com.github.fo2rist.mclaren.repository.feed.FeedRepositoryEventBus
import com.github.fo2rist.mclaren.repository.feed.McLarenFeedRepositoryImpl
import com.github.fo2rist.mclaren.repository.feed.StoryStreamRepositoryImpl
import com.github.fo2rist.mclaren.repository.feed.TwitterRepositoryImpl
import com.github.fo2rist.mclaren.web.feed.FeedHistoryPredictor
import com.github.fo2rist.mclaren.web.feed.McLarenFeedHistoryPredictor
import com.github.fo2rist.mclaren.web.feed.McLarenFeedWebService
import com.github.fo2rist.mclaren.web.feed.McLarenWebServiceImpl
import com.github.fo2rist.mclaren.web.feed.StoryStreamWebService
import com.github.fo2rist.mclaren.web.feed.StoryStreamWebServiceImpl
import com.github.fo2rist.mclaren.web.models.McLarenFeed
import com.github.fo2rist.mclaren.web.models.StoryStream
import dagger.Binds
import dagger.Module
import twitter4j.ResponseList
import twitter4j.Status
import javax.inject.Named
import javax.inject.Singleton

@Module
internal interface FeedRepositoriesModule {
    //region mclaren legacy API feed
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
    @Singleton
    fun provideMclarenFeedStreamConverter(converter: McLarenFeedConverter): FeedConverter<McLarenFeed>
    //endregion

    //region story-stream API feed
    @Binds
    @Named("storystream-feed")
    @Singleton
    fun provideStoryStreamRepository(repository: StoryStreamRepositoryImpl): FeedRepository

    @Binds
    @Singleton
    fun provideStoryStreamWebservice(webService: StoryStreamWebServiceImpl): StoryStreamWebService

    @Binds
    @Singleton
    fun provideStoryStreamConverter(converter: StoryStreamConverter): FeedConverter<StoryStream>
    //endregion

    //twitter feed
    @Binds
    @Named("twitter-feed")
    @Singleton
    fun provideTwitterRepository(repository: TwitterRepositoryImpl): FeedRepository

    @Binds
    @Singleton
    fun provideTwitterConverter(converter: TwitterConverter): FeedConverter<ResponseList<Status>>
    //endregion

    @Binds
    @Singleton
    fun provideFeedRepositoryEventBus(eventBus: RepositoryEventBusImpl): FeedRepositoryEventBus
}
