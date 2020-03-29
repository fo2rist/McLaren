package com.github.fo2rist.mclaren.web.feed

import com.github.fo2rist.mclaren.web.feed.BaseFeedWebService
import com.github.fo2rist.mclaren.web.feed.BaseFeedWebServiceTest
import com.github.fo2rist.mclaren.web.feed.StoryStreamWebServiceImpl
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class StoryStreamWebServiceImplTest : BaseFeedWebServiceTest() {

    override lateinit var webservice: BaseFeedWebService

    @Before
    override fun setUp() {
        super.setUp()

        webservice = StoryStreamWebServiceImpl(httpClientMock)
    }
}
