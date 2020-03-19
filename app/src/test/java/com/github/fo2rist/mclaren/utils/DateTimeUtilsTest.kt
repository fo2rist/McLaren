package com.github.fo2rist.mclaren.utils

import org.joda.time.DateTime
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

private val ONE_DIGIT_DAY_DATE = DateTime.parse("2000-01-01")
private val TWO_DIGIT_DAY_DATE = DateTime.parse("2000-01-31")

@RunWith(RobolectricTestRunner::class)
class DateTimeUtilsTest {

    @Test
    fun `formatShort formats as short Month and double digit day`() {
        val context = RuntimeEnvironment.application.applicationContext

        assertEquals("Jan 01", formatShort(context, ONE_DIGIT_DAY_DATE))
        assertEquals("Jan 31", formatShort(context, TWO_DIGIT_DAY_DATE))
    }
}
