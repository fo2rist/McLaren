package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.repository.FeedRepositoryEventBus.LoadingEvent
import com.github.fo2rist.mclaren.testdata.StoryStreamResponse.REAL_FEED_RESPONSE
import com.github.fo2rist.mclaren.web.StoryStreamWebService
import com.github.fo2rist.mclaren.web.utils.BadResponse
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.net.URL

//TODO remove tests duplications with MCL Repo test. 2018.11.25
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class StoryStreamRepositoryImplTest {

    private lateinit var repository: FeedRepository
    private lateinit var mockWebService: StoryStreamWebService
    private lateinit var mockEventBus: FeedRepositoryEventBus

    @Before
    fun setUp() {
        mockWebService = mock(StoryStreamWebService::class.java)
        mockEventBus = mock(FeedRepositoryEventBus::class.java)

        repository = StoryStreamRepositoryImpl(mockWebService, mockEventBus)
    }

    @Test
    fun test_loadLatest_startLoading_and_firesLoadStartEvent() = runBlocking<Unit> {
        repository.loadLatestPage()

        verify(mockEventBus).publish(any<LoadingEvent.LoadingStarted>())
        verify(mockWebService).requestLatestFeed()
    }

    @Test
    fun test_loadNextHistory_startLoading_and_firesLoadStartEvent() {
        repository.loadNextPage()

        verify(mockEventBus).publish(any<LoadingEvent.LoadingStarted>())
        verify(mockWebService).requestFeedPage(anyInt(), any())
    }

    @Test
    fun test_onSuccess_firesLoadFinishSuccessfullyEvents() = runBlocking {
        whenever(mockWebService.requestLatestFeed()).thenReturn(REAL_FEED_RESPONSE)

        repository.loadLatestPage()

        verify(mockEventBus).publish(any<LoadingEvent.LoadingStarted>())
        verify(mockEventBus).publish(any<LoadingEvent.FeedUpdateReady>())
        verify(mockEventBus).publish(any<LoadingEvent.LoadingFinished>())
    }

    @Test
    fun test_onFailure_firesLoadFinishWithErrorEvents() = runBlocking {
        whenever(mockWebService.requestLatestFeed()).thenThrow(BadResponse(URL("http://empty.url"), 400))

        repository.loadLatestPage()


        verify(mockEventBus).publish(any<LoadingEvent.LoadingStarted>())
        verify(mockEventBus).publish(any<LoadingEvent.LoadingError>())
        verify(mockEventBus).publish(any<LoadingEvent.LoadingFinished>())
    }
}
