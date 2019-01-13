package com.github.fo2rist.mclaren.web.models

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StoryStreamTest {
    @Test
    fun `test empty object contains empty list`() {
        val empty = StoryStream()

        assertNotNull(empty.items)
        assertTrue(empty.items.isEmpty())
    }
}
