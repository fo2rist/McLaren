package com.github.fo2rist.mclaren.web.utils

import okhttp3.Call
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.io.IOException
import java.net.URL

@RunWith(JUnit4::class)
class OkHttpCallbackWrapperTest {

    private lateinit var dummyRequest: Request
    private lateinit var mockCall: Call
    private lateinit var mockResponse: Response

    @Before
    fun setUp() {
        dummyRequest = Request.Builder().url("http://dummy.url").build()
        mockCall = mock(Call::class.java)
        `when`(mockCall.request()).thenReturn(dummyRequest)
        mockResponse = mock(Response::class.java)
    }

    @Test
    fun testOnSuccessDelivered() {
        `when`(mockResponse.isSuccessful).thenReturn(true)
        `when`(mockResponse.code).thenReturn(200)

        val responseWrapper = createTestWrapper(
                doOnFailure = { _, _, _ ->
                    fail("Should not be called")
                },
                doOnSuccess = { _, code, _ ->
                    assertEquals(200, code)
                }
        )
        responseWrapper.onResponse(mockCall, mockResponse)
    }

    @Test
    fun testBadResponseDelivered() {
        `when`(mockResponse.isSuccessful).thenReturn(false)
        `when`(mockResponse.code).thenReturn(500)

        val responseWrapper = createTestWrapper(
                doOnFailure = { _, code, _ ->
                    assertEquals(500, code)
                },
                doOnSuccess = { _, _, _ ->
                    fail("Should not be called")
                }
        )
        responseWrapper.onResponse(mockCall, mockResponse)
    }

    @Test
    fun testNetworkErrorDelivered() {
        val responseWrapper = createTestWrapper(
                doOnFailure = { _, _, exception ->
                    assertNotNull(exception)
                },
                doOnSuccess = { _, _, _ ->
                    fail("Should not be called")
                }
        )
        responseWrapper.onFailure(mockCall, IOException())
    }

    private fun createTestWrapper(
        doOnSuccess: (url: URL, responseCode: Int, responseBody: String?) -> Unit,
        doOnFailure: (url: URL, responseCode: Int, connectionError: IOException?) -> Unit
    ): OkHttpCallbackWrapper {
        return object : OkHttpCallbackWrapper() {
            override fun onOkHttpFailure(url: URL, responseCode: Int, connectionError: IOException?) {
                doOnFailure(url, responseCode, connectionError)
            }

            override fun onOkHttpSuccess(url: URL, responseCode: Int, responseBody: String?) {
                doOnSuccess(url, responseCode, responseBody)
            }
        }
    }
}
