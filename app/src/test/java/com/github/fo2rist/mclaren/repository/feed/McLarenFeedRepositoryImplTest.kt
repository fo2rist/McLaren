package com.github.fo2rist.mclaren.repository.feed

import com.github.fo2rist.mclaren.repository.feed.FeedRepositoryEventBus.LoadingEvent
import com.github.fo2rist.mclaren.testdata.McLarenFeedResponse.REAL_FEED_RESPONSE
import com.github.fo2rist.mclaren.web.feed.FeedHistoryPredictor
import com.github.fo2rist.mclaren.web.feed.McLarenFeedWebService
import com.github.fo2rist.mclaren.web.utils.BadResponse
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.stubbing
import com.nhaarman.mockitokotlin2.verifyBlocking
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import java.net.URL

//TODO remove tests duplications with StoryStream Repo test. 2018.11.25
@RunWith(RobolectricTestRunner::class)
class McLarenFeedRepositoryImplTest {

    @Mock
    private lateinit var mockWebService: McLarenFeedWebService
    @Mock
    private lateinit var mockEventBus: FeedRepositoryEventBus
    @Mock
    private lateinit var mockHistoryPredictor: FeedHistoryPredictor

    private lateinit var repository: McLarenFeedRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        repository = McLarenFeedRepositoryImpl(mockWebService, mockEventBus, mockHistoryPredictor)
    }

    @Test
    fun test_loadLatest_startLoading_and_firesLoadStartEvent() = runBlocking<Unit> {
        repository.loadLatestPage()

        verify(mockEventBus).publish(any<LoadingEvent.LoadingStarted>())
        verify(mockWebService).requestLatestFeed()
    }

    @Test
    fun testHistoryLoadingCallPredictorFirst() {
        whenever(mockHistoryPredictor.isFirstHistoryPageKnown).thenReturn(false)
        whenever(mockHistoryPredictor.firstHistoryPage).thenReturn(-1)

        repository.loadNextPage()

        verify(mockHistoryPredictor).startPrediction()
        verifyBlocking(mockWebService, never()) { requestFeedPage(anyInt()) }
    }

    @Test
    fun testHistoryLoadingCallPredictedPage() = runBlocking<Unit> {
        whenever(mockHistoryPredictor.isFirstHistoryPageKnown).thenReturn(true)
        whenever(mockHistoryPredictor.firstHistoryPage).thenReturn(1000)

        repository.loadNextPage()

        verify(mockHistoryPredictor, never()).startPrediction()
        verify(mockHistoryPredictor).firstHistoryPage
        verify(mockWebService).requestFeedPage(anyInt())
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
    fun test_onSuccess_firesLoadFinishEvents() {
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
