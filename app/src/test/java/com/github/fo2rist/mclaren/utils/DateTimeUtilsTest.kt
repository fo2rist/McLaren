package com.github.fo2rist.mclaren.utils

import org.joda.time.DateTime
import org.joda.time.Period
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

private val ONE_DIGIT_DAY_DATE = DateTime.parse("2000-01-01")
private val TWO_DIGIT_DAY_DATE = DateTime.parse("2000-01-31")

@RunWith(RobolectricTestRunner::class)
class DateTimeUtilsTest {
    private val context by lazy { RuntimeEnvironment.application.applicationContext }

    @Test
    fun `formatShort formats as short Month and double digit day`() {
        assertEquals("01 Jan", formatShort(context, ONE_DIGIT_DAY_DATE))
        assertEquals("31 Jan", formatShort(context, TWO_DIGIT_DAY_DATE))
    }

    @Test
    fun `formatRemainingTimeShort gives most significant units only`() {
        val periodToFormat = mapOf(
                Period(0, 1, 0, 0, 0, 0, 0, 0) to "1M", // exact month
                Period(0, 1, 2, 3, 0, 0, 0, 0) to "1M", // month + some time
                Period(0, 0, 1, 0, 0, 0, 0, 0) to "1W", // exact week
                Period(0, 0, 1, 2, 3, 0, 0, 0) to "1W", // week + some time
                Period(0, 0, 0, 1, 0, 0, 0, 0) to "1D", // exact day
                Period(0, 0, 0, 1, 2, 3, 0, 0) to "1D", // day +
                Period(0, 0, 0, 0, 1, 0, 0, 0) to "1H", // exact hour
                Period(0, 0, 0, 0, 1, 2, 3, 0) to "1H", // hour +
                Period(0, 0, 0, 0, 0, 1, 0, 0) to "1Min", // exact minute
                Period(0, 0, 0, 0, 0, 1, 2, 0) to "1Min" // minute +
        )

        for ((remainingTime, expectedFormat) in periodToFormat) {
            assertEquals(expectedFormat, formatRemainingTimeShort(context, remainingTime))
        }
    }

    @Test
    fun `formatRemainingTimeShort ignores time less than one minute`() {
        val zeroMinutePlus = Period(0, 0, 0, 0, 0, 0, 30, 0)

        assertEquals("seconds", formatRemainingTimeShort(context, zeroMinutePlus))
    }
}
