package com.github.fo2rist.mclaren.web.feed

import com.github.fo2rist.mclaren.web.feed.BaseFeedWebService
import com.github.fo2rist.mclaren.web.feed.BaseFeedWebServiceTest
import com.github.fo2rist.mclaren.web.feed.McLarenWebServiceImpl
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class McLarenWebServiceImplTest : BaseFeedWebServiceTest() {

    override lateinit var webservice: BaseFeedWebService

    @Before
    override fun setUp() {
        super.setUp()

        webservice = McLarenWebServiceImpl(httpClientMock)
    }

    @Test
    fun `test requestTransmission calls http client`() = runBlocking<Unit> {
        (webservice as McLarenWebServiceImpl).requestTransmission()

        verify(httpClientMock).newCall(any())
    }
}
