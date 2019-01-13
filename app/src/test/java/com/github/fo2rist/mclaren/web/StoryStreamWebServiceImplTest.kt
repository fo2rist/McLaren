package com.github.fo2rist.mclaren.web

import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class StoryStreamWebServiceImplTest : BaseFeedWebServiceTest() {

    override lateinit var webservice: BaseFeedWebService

    @Before
    override fun setUp() {
        super.setUp()

        webservice = StoryStreamWebServiceImpl(httpClientMock)
    }
}
