package com.github.fo2rist.mclaren.ui.models

import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

private val JAN_1 = DateTime.parse("2100-01-01T00:00Z")
private val JAN_2 = DateTime.parse("2100-01-02T00:00Z")
private val JAN_3 = DateTime.parse("2100-01-03T00:00Z")
private val JAN_31 = DateTime.parse("2100-01-31T00:00Z")
private val FEB_2 = DateTime.parse("2100-02-02T00:00Z")

private val TEST_EVENT_JAN_1 = CalendarEvent(
        circuitId = "",
        countryCode = "",
        trackName = "",
        city = "",
        grandPrixName = "",
        laps = 1,
        length = 0.0,
        distance = 0.0,
        seasons = "",
        gpHeld = 0,
        wikiLink = "",
        startDate = JAN_1
)

private val TEST_EVENT_JAN_31 = TEST_EVENT_JAN_1.copy(startDate = JAN_31)

@RunWith(JUnit4::class)
class CalendarEventTest {

    @Test
    fun `test endDate calculated properly`() {

        assertEquals(
                JAN_3,
                TEST_EVENT_JAN_1.endDate)

        assertEquals(
                FEB_2,
                TEST_EVENT_JAN_31.endDate)
    }

    @Test
    fun `test isActive at start date`() {
        assertTrue(TEST_EVENT_JAN_1.isActiveAt(JAN_1))
    }

    @Test
    fun `test isActive between start and end`() {
        assertTrue(TEST_EVENT_JAN_1.isActiveAt(JAN_2))
    }

    @Test
    fun `test isActive at end date`() {
        assertTrue(TEST_EVENT_JAN_1.isActiveAt(JAN_3))
    }

    @Test
    fun `test isActive 20h after end date`() {
        assertTrue(TEST_EVENT_JAN_1.isActiveAt(JAN_3.plusHours(20)))
    }

    @Test
    fun `test not isActive day after end date`() {
        assertFalse(TEST_EVENT_JAN_1.isActiveAt(JAN_3.plusDays(1)))
    }

    @Test
    fun `test not isActive day before start date`() {
        assertFalse(TEST_EVENT_JAN_1.isActiveAt(JAN_1.minusDays(1)))
    }
}
