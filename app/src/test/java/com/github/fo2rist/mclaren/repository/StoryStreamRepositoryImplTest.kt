package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.repository.FeedRepositoryEventBus.LoadingEvent
import com.github.fo2rist.mclaren.testdata.StoryStreamResponse.REAL_FEED_RESPONSE
import com.github.fo2rist.mclaren.web.StoryStreamWebService
import com.github.fo2rist.mclaren.web.utils.BadResponse
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.stubbing
import com.nhaarman.mockitokotlin2.verifyBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import java.net.URL

//TODO remove tests duplications with MCL Repo test. 2018.11.25
@RunWith(RobolectricTestRunner::class)
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
    fun test_loadNextHistory_startLoading_and_firesLoadStartEvent() {
        repository.loadNextPage()

        verify(mockEventBus).publish(any<LoadingEvent.LoadingStarted>())
        verifyBlocking(mockWebService) { requestFeedPage(anyInt()) }
    }

    @Test
    fun test_onSuccess_firesLoadFinishSuccessfullyEvents() {
        stubbing(mockWebService) {
            onBlocking { requestLatestFeed() }.doReturn(REAL_FEED_RESPONSE)
        }

        repository.loadLatestPage()

        verify(mockEventBus).publish(any<LoadingEvent.LoadingStarted>())
        verify(mockEventBus).publish(any<LoadingEvent.FeedUpdateReady>())
        verify(mockEventBus).publish(any<LoadingEvent.LoadingFinished>())
    }

    @Test
    fun test_onFailure_firesLoadFinishWithErrorEvents() {
        stubbing(mockWebService) {
            onBlocking { requestLatestFeed() }.doThrow(BadResponse(URL("http://empty.url"), 400))
        }

        repository.loadLatestPage()

        verify(mockEventBus).publish(any<LoadingEvent.LoadingStarted>())
        verify(mockEventBus).publish(any<LoadingEvent.LoadingError>())
        verify(mockEventBus).publish(any<LoadingEvent.LoadingFinished>())
    }
}
