package com.github.fo2rist.mclaren.web

import com.github.fo2rist.mclaren.web.FeedWebService.FeedRequestCallback
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.RETURNS_MOCKS
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
        val immediateRequestMock = mockImmediateSuccessfulRequest()
        whenever(httpClientMock.newCall(any())).thenReturn(immediateRequestMock)

        webservice = McLarenWebServiceImpl(httpClientMock)
    }

    private fun mockImmediateSuccessfulRequest(): Call {
        val requestMock = mock<Call>(defaultAnswer = RETURNS_MOCKS)
        val responseMock = mock<Response>()
        //make it successful
        whenever(responseMock.isSuccessful).thenReturn(true)
        //make it return immediately
        whenever(requestMock.enqueue(any())).thenAnswer {
            val callback: Callback = it.getArgument(0)
            callback.onResponse(requestMock, responseMock)
        }
        return requestMock
    }

    @Test
    fun test_requestLatestFeed_callsWebClient() = runBlocking {
        webservice.requestLatestFeed()

        verify(httpClientMock).newCall(any())
        Unit
    }

    @Test
    fun test_requestFeedPage_callsWebClient() {
        webservice.requestFeedPage(1, mock(FeedRequestCallback::class.java))

        verify(httpClientMock).newCall(any())
    }

    @Test
    fun test_requestTransmission_callsWebClient() = runBlocking<Unit> {
        webservice.requestTransmission()

        verify(httpClientMock).newCall(any())
    }
}
