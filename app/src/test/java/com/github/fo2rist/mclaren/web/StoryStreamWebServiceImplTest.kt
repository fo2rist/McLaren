package com.github.fo2rist.mclaren.web

import com.github.fo2rist.mclaren.testutilities.overrideAnswersToSuccess
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class StoryStreamWebServiceImplTest {
    @Mock
    private lateinit var httpClientMock: OkHttpClient

    private lateinit var webservice: StoryStreamWebServiceImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        httpClientMock.overrideAnswersToSuccess()

        webservice = StoryStreamWebServiceImpl(httpClientMock)
    }

    @Test
    fun `test requestLatestFeed requests null page`() = runBlocking<Unit> {
        val webserviceSpy = spy(webservice)
        whenever(webserviceSpy.createFeedPageRequest()).thenReturn(mock())

        webserviceSpy.requestLatestFeed()

        verify(webserviceSpy).createFeedPageRequest(null)
    }

    @Test
    fun `test requestFeedPage calls http client`() = runBlocking<Unit> {
        webservice.requestFeedPage(123)

        verify(httpClientMock).newCall(any())
    }
}
