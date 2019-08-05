package com.github.fo2rist.mclaren.web.models

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StoryStreamTest {
    @Test
    fun `test empty object can be instantiated to be used with SafeJsonParser`() {
        val empty = StoryStream()

        assertEquals(0, empty.totalPages)
    }
}
