package com.github.fo2rist.mclaren.ui.models

import com.github.fo2rist.mclaren.testdata.CalendarEvents.createDummyEvent
import org.joda.time.DateTime
import org.joda.time.DateTimeUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

private const val T_0AM = "00:00Z"
private const val T_1AM = "01:00Z"

private val JAN_1 = DateTime.parse("2100-01-01T$T_0AM")
private val JAN_2 = DateTime.parse("2100-01-02T$T_0AM")
private val JAN_3 = DateTime.parse("2100-01-03T$T_0AM")
private val JAN_3_1AM = DateTime.parse("2100-01-03T$T_1AM")

private val JAN_31 = DateTime.parse("2100-01-31T$T_0AM")
private val FEB_2 = DateTime.parse("2100-02-02T$T_0AM")
private val FEB_2_1AM = DateTime.parse("2100-02-02T$T_1AM")

/** An event within the same month. */
private val TEST_EVENT_JAN_1 = createDummyEvent(
        practice1DateTime = JAN_1.plusHours(1),
        practice2DateTime = JAN_1.plusHours(2),
        practice3DateTime = JAN_2.plusHours(1),
        qualifyingDateTime = JAN_2.plusHours(2),
        raceDateTime = JAN_3_1AM)
/** An event that spans two months. */
private val TEST_EVENT_JAN_31 = createDummyEvent(
        practice1DateTime = JAN_31.plusHours(1),
        practice2DateTime = JAN_31.plusHours(2),
        practice3DateTime = JAN_31.plusHours(25),
        qualifyingDateTime = JAN_31.plusHours(26),
        raceDateTime = FEB_2_1AM)

@RunWith(JUnit4::class)
class CalendarEventTest {

    @Test
    fun `startDate is the day of practice 1`() {
        assertEquals(JAN_1, TEST_EVENT_JAN_1.startDate)

        assertEquals(JAN_31, TEST_EVENT_JAN_31.startDate)
    }

    @Test
    fun `endDate is the day of race`() {
        assertEquals(JAN_3, TEST_EVENT_JAN_1.endDate)

        assertEquals(FEB_2, TEST_EVENT_JAN_31.endDate)
    }

    @Test
    fun `isActive between practice and race time inclusive`() {
        assertTrue(TEST_EVENT_JAN_1.isActiveAt(JAN_1.plusHours(1)))
        assertTrue(TEST_EVENT_JAN_1.isActiveAt(JAN_2))
        assertTrue(TEST_EVENT_JAN_1.isActiveAt(JAN_3_1AM))

        assertTrue(TEST_EVENT_JAN_31.isActiveAt(JAN_31.plusHours(1)))
        assertTrue(TEST_EVENT_JAN_31.isActiveAt(FEB_2_1AM))
    }

    @Test
    fun `isActive 2h after race start time`() {
        assertTrue(TEST_EVENT_JAN_1.isActiveAt(JAN_3_1AM.plusHours(2)))
        assertTrue(TEST_EVENT_JAN_31.isActiveAt(FEB_2_1AM.plusHours(2)))
    }

    @Test
    fun `not isActive day before practice time`() {
        assertFalse(TEST_EVENT_JAN_1.isActiveAt(JAN_1))
        assertFalse(TEST_EVENT_JAN_31.isActiveAt(JAN_31))
    }

    @Test
    fun `not isActive more than 2h after race start time`() {
        assertFalse(TEST_EVENT_JAN_1.isActiveAt(JAN_3_1AM.plusHours(2).plusMinutes(1)))
        assertFalse(TEST_EVENT_JAN_31.isActiveAt(FEB_2_1AM.plusHours(2).plusMinutes(1)))
    }

    @Test
    fun `timeToFirstPractice returns precise non-zero period for event in the future`() {
        DateTimeUtils.setCurrentMillisFixed(JAN_31.plusHours(1).minusMinutes(1).millis)

        assertEquals(60, TEST_EVENT_JAN_31.timeToFirstPractice().toStandardSeconds().seconds)

        DateTimeUtils.setCurrentMillisSystem()
    }

    @Test
    fun `timeToFirstPractice returns negative period for event in the past`() {
        DateTimeUtils.setCurrentMillisFixed(FEB_2_1AM.plusDays(1).millis)

        assertTrue(TEST_EVENT_JAN_31.timeToFirstPractice().toStandardSeconds().seconds < 0)

        DateTimeUtils.setCurrentMillisSystem()
    }
}
