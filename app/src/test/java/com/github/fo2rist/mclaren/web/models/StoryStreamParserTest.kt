package com.github.fo2rist.mclaren.web.models

import com.github.fo2rist.mclaren.testdata.StoryStreamResponse
import com.github.fo2rist.mclaren.web.SafeJsonParser
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class StoryStreamParserTest {
    private val parser = SafeJsonParser(StoryStream::class.java)

    @Test
    fun testReadDataParsedWithoutErrors() {
        val (_, items) = parser.parse(StoryStreamResponse.REAL_FEED_RESPONSE)

        assertEquals(StoryStreamResponse.REAL_FEED_SIZE, items?.size)
    }
}
