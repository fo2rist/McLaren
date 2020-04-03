package com.github.fo2rist.mclaren.repository.feed

import com.github.fo2rist.mclaren.repository.converters.FeedConverter
import com.github.fo2rist.mclaren.repository.feed.FeedRepositoryEventBus.LoadingEvent
import com.github.fo2rist.mclaren.testutilities.fakes.FakeTwitterResponse
import com.github.fo2rist.mclaren.web.feed.TwitterWebServiceBuilder
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.stubbing
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import twitter4j.ResponseList
import twitter4j.Status
import twitter4j.Twitter
import twitter4j.TwitterException

class TwitterRepositoryImplTest {
    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    private lateinit var repository: TwitterRepositoryImpl

    @Mock
    lateinit var mockEventBus: FeedRepositoryEventBus

    @Mock
    lateinit var mockTwitterConverter: FeedConverter<ResponseList<Status>>

    @Mock
    lateinit var mockTwitterService: Twitter

    @Before
    fun setUp() {
        val mockTwitterBuilder = mock<TwitterWebServiceBuilder>()
        whenever(mockTwitterBuilder.getInstance()).thenReturn(mockTwitterService)

        repository = TwitterRepositoryImpl(mockEventBus, mockTwitterBuilder, mockTwitterConverter)
    }

    @Test
    fun `loadAndNotify onSuccess start reader finish events`() = runBlocking {
        stubbing(mockTwitterService) {
            on { getUserTimeline(anyString(), any()) }.doReturn(FakeTwitterResponse())
        }
        repository.loadAndNotify(1)

        verify(mockTwitterService).getUserTimeline(anyString(), any())
        verify(mockEventBus).publish(any<LoadingEvent.LoadingStarted>())
        verify(mockEventBus).publish(any<LoadingEvent.FeedUpdateReady>())
        verify(mockEventBus).publish(any<LoadingEvent.LoadingFinished>())
    }

    @Test
    fun `loadAndNotify onError fires start error finish`() = runBlocking {
        stubbing(mockTwitterService) {
            on { getUserTimeline(anyString(), any()) }.doThrow(TwitterException("anything"))
        }
        repository.loadAndNotify(1)

        verify(mockTwitterService).getUserTimeline(anyString(), any())
        verify(mockEventBus).publish(any<LoadingEvent.LoadingStarted>())
        verify(mockEventBus).publish(any<LoadingEvent.LoadingError>())
        verify(mockEventBus).publish(any<LoadingEvent.LoadingFinished>())
    }

    @Test
    fun `prepareForHistoryLoading does nothing`() {
        repository.prepareForHistoryLoading()

        verifyZeroInteractions(mockEventBus, mockTwitterConverter, mockTwitterService)
    }
}
