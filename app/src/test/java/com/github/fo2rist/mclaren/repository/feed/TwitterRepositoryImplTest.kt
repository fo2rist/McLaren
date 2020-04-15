package com.github.fo2rist.mclaren.repository.feed

import com.github.fo2rist.mclaren.repository.converters.TwitterConverter
import com.github.fo2rist.mclaren.repository.feed.FeedRepositoryEventBus.LoadingEvent
import com.github.fo2rist.mclaren.testutilities.fakes.FakeTwitterResponse
import com.github.fo2rist.mclaren.testutilities.fakes.FakeTwitterStatus
import com.github.fo2rist.mclaren.web.feed.TwitterWebServiceBuilder
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
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
import twitter4j.Twitter
import twitter4j.TwitterException

private const val ACCOUNT = "some_twitter"

class TwitterRepositoryImplTest {
    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    private lateinit var repository: TwitterRepositoryImpl

    private val twitterConverter = TwitterConverter()

    @Mock
    lateinit var mockEventBus: FeedRepositoryEventBus

    @Mock
    lateinit var mockTwitterService: Twitter

    @Before
    fun setUp() {
        val mockTwitterBuilder = mock<TwitterWebServiceBuilder>()
        whenever(mockTwitterBuilder.getInstance()).thenReturn(mockTwitterService)

        repository = TwitterRepositoryImpl(mockEventBus, mockTwitterBuilder, twitterConverter)
    }

    @Test
    fun `loadAndNotify onSuccess fires start,update,finish events`() = runBlocking {
        stubbing(mockTwitterService) {
            on { getUserTimeline(anyString(), any()) }.doReturn(FakeTwitterResponse())
        }

        repository.loadAndNotify(ACCOUNT, 1)

        verify(mockTwitterService).getUserTimeline(anyString(), any())
        verify(mockEventBus).publish(any<LoadingEvent.LoadingStarted>())
        verify(mockEventBus).publish(any<LoadingEvent.FeedUpdateReady>())
        verify(mockEventBus).publish(any<LoadingEvent.LoadingFinished>())
    }

    @Test
    fun `loadAndNotify delivers different content for different accounts`() = runBlocking {
        stubbing(mockTwitterService) {
            on { getUserTimeline(eq("1"), any()) }.doReturn(FakeTwitterResponse(FakeTwitterStatus(_id = 1)))
            on { getUserTimeline(eq("2"), any()) }.doReturn(FakeTwitterResponse(FakeTwitterStatus(_id = 2)))
        }

        repository.loadAndNotify("1", 1)

        verify(mockEventBus).publish(any<LoadingEvent.LoadingStarted>())
        verify(mockEventBus).publish(
                argThat { this is LoadingEvent.FeedUpdateReady && this.feed.find { it.id == 1L } != null })
        verify(mockEventBus).publish(any<LoadingEvent.LoadingFinished>())
        reset(mockEventBus)

        repository.loadAndNotify("2", 1)

        verify(mockEventBus).publish(any<LoadingEvent.LoadingStarted>())
        verify(mockEventBus).publish(
                argThat { this is LoadingEvent.FeedUpdateReady && this.feed.find { it.id == 2L } != null })
        verify(mockEventBus).publish(any<LoadingEvent.LoadingFinished>())
    }

    @Test
    fun `loadAndNotify onError fires start,error,finish events`() = runBlocking {
        stubbing(mockTwitterService) {
            on { getUserTimeline(anyString(), any()) }.doThrow(TwitterException("anything"))
        }
        repository.loadAndNotify(ACCOUNT, 1)

        verify(mockTwitterService).getUserTimeline(anyString(), any())
        verify(mockEventBus).publish(any<LoadingEvent.LoadingStarted>())
        verify(mockEventBus).publish(any<LoadingEvent.LoadingError>())
        verify(mockEventBus).publish(any<LoadingEvent.LoadingFinished>())
    }

    @Test
    fun `prepareForHistoryLoading does nothing`() {
        repository.prepareForHistoryLoading()

        verifyZeroInteractions(mockEventBus, mockTwitterService)
    }
}
