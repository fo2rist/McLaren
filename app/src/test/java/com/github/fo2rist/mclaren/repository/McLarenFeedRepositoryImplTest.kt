package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.repository.FeedRepositoryEventBus.LoadingEvent
import com.github.fo2rist.mclaren.testdata.McLarenFeedResponse.REAL_FEED_RESPONSE
import com.github.fo2rist.mclaren.web.FeedHistoryPredictor
import com.github.fo2rist.mclaren.web.McLarenFeedWebService
import com.github.fo2rist.mclaren.web.utils.BadResponse
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.net.URL

//TODO remove tests duplications with StoryStream Repo test. 2018.11.25
@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(21))
class McLarenFeedRepositoryImplTest {

    private lateinit var repository: McLarenFeedRepositoryImpl
    private lateinit var mockWebService: McLarenFeedWebService
    private lateinit var mockEventBus: FeedRepositoryEventBus
    private lateinit var mockHistoryPredictor: FeedHistoryPredictor

    @Before
    fun setUp() {
        mockWebService = mock(McLarenFeedWebService::class.java)
        mockEventBus = mock(FeedRepositoryEventBus::class.java)
        mockHistoryPredictor = mock(FeedHistoryPredictor::class.java)

        repository = McLarenFeedRepositoryImpl(mockWebService, mockEventBus, mockHistoryPredictor)
    }

    @Test
    fun test_loadLatest_startLoading_and_firesLoadStartEvent() = runBlocking {
        repository.loadLatestPage()

        verify(mockEventBus).publish(any<LoadingEvent.LoadingStarted>())
        verify(mockWebService).requestLatestFeed()
        Unit
    }

    @Test
    fun testHistoryLoadingCallPredictorFirst() {
        whenever(mockHistoryPredictor.isFirstHistoryPageKnown).thenReturn(false)
        whenever(mockHistoryPredictor.firstHistoryPage).thenReturn(-1)

        repository.loadNextPage()

        verify(mockHistoryPredictor).startPrediction()
        verify(mockWebService, never()).requestFeedPage(anyInt(), any())
    }

    @Test
    fun testHistoryLoadingCallPredictedPage() {
        whenever(mockHistoryPredictor.isFirstHistoryPageKnown).thenReturn(true)
        whenever(mockHistoryPredictor.firstHistoryPage).thenReturn(1000)

        repository.loadNextPage()

        verify(mockHistoryPredictor, never()).startPrediction()
        verify(mockHistoryPredictor).firstHistoryPage
        verify(mockWebService).requestFeedPage(anyInt(), any())
    }

    @Test
    fun test_PrepareForHistoryLoading_calledWhenHistoryIsUnknown() {
        whenever(mockHistoryPredictor.isFirstHistoryPageKnown).thenReturn(false)

        repository.prepareForHistoryLoading()

        verify(mockHistoryPredictor).startPrediction()
    }

    @Test
    fun test_PrepareForHistoryLoading_notCalledWhenHistoryIsKnown() {
        whenever(mockHistoryPredictor.isFirstHistoryPageKnown).thenReturn(true)

        repository.prepareForHistoryLoading()

        verify(mockHistoryPredictor, never()).startPrediction()
    }

    @Test
    fun test_onSuccess_firesLoadFinishEvents() = runBlocking {
        whenever(mockWebService.requestLatestFeed()).thenReturn(REAL_FEED_RESPONSE)

        repository.loadLatestPage()

        verify(mockEventBus).publish(any<LoadingEvent.LoadingStarted>())
        verify(mockEventBus).publish(any<LoadingEvent.FeedUpdateReady>())
        verify(mockEventBus).publish(any<LoadingEvent.LoadingFinished>())
    }

    @Test
    fun test_onFailure_firesLoadFinishWithErrorEvents() = runBlocking {
        whenever(mockWebService.requestLatestFeed())
                .thenThrow(BadResponse(URL("http://empty.url"), 400))

        repository.loadLatestPage()

        verify(mockEventBus).publish(any<LoadingEvent.LoadingStarted>())
        verify(mockEventBus).publish(any<LoadingEvent.LoadingError>())
        verify(mockEventBus).publish(any<LoadingEvent.LoadingFinished>())
    }
}
