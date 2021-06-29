package com.github.fo2rist.mclaren.web.feed

import androidx.annotation.CallSuper
import com.github.fo2rist.mclaren.testutilities.overrideAnswersToSuccess
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Test

abstract class BaseFeedWebServiceTest {
    /** Http client which [OkHttpClient.newCall] return calls that immediately respond with 200 once executed. */
    protected var httpClientMock: OkHttpClient = mock()

    /** Service to be tested, should be provided by the client. */
    protected abstract var webservice: BaseFeedWebService

    @CallSuper
    open fun setUp() {
        httpClientMock.overrideAnswersToSuccess()
    }

    @Test
    fun `test requestLatestFeed requests null page`() = runBlocking<Unit> {
        val webserviceSpy = spy(webservice)
        whenever(webserviceSpy.createFeedPageRequest()).thenReturn(mock())

        webserviceSpy.requestLatestFeed()

        verify(webserviceSpy).createFeedPageRequest(null)
    }

    @Test
    fun `test requestLatestFeed calls http client`() = runBlocking<Unit> {
        webservice.requestLatestFeed()

        verify(httpClientMock).newCall(any())
    }


    @Test
    fun `test requestFeedPage calls http client`() = runBlocking<Unit> {
        webservice.requestFeedPage(1)

        verify(httpClientMock).newCall(any())
    }
}
