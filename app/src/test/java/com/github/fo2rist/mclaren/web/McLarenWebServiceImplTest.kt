package com.github.fo2rist.mclaren.web

import com.github.fo2rist.mclaren.testutilities.overrideAnswersToSuccess
import com.github.fo2rist.mclaren.web.FeedWebService.FeedRequestCallback
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class McLarenWebServiceImplTest {

    private lateinit var httpClientMock: OkHttpClient
    private lateinit var webservice: McLarenWebServiceImpl

    @Before
    fun setUp() {
        httpClientMock = mock()
        httpClientMock.overrideAnswersToSuccess()

        webservice = McLarenWebServiceImpl(httpClientMock)
    }

    @Test
    fun `test requestLatestFeed requests null page`() = runBlocking<Unit> {
        val webserviceSpy = spy(webservice)

        webserviceSpy.requestLatestFeed()

        verify(webserviceSpy).createFeedPageRequest(null)
    }

    @Test
    fun `test requestLatestFeed calls http client`() = runBlocking<Unit> {
        webservice.requestLatestFeed()

        verify(httpClientMock).newCall(any())
    }

    @Test
    fun `test requestFeedPage calls http client`() {
        webservice.requestFeedPage(1, mock(FeedRequestCallback::class.java))

        verify(httpClientMock).newCall(any())
    }

    @Test
    fun `test requestTransmission calls http client`() = runBlocking<Unit> {
        webservice.requestTransmission()

        verify(httpClientMock).newCall(any())
    }
}
