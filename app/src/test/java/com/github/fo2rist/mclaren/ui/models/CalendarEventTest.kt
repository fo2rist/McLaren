package com.github.fo2rist.mclaren.ui.models

import com.github.fo2rist.mclaren.testdata.DUMMY_CIRCUIT
import com.github.fo2rist.mclaren.testdata.DUMMY_EVENT
import com.github.fo2rist.mclaren.testdata.FEB_2
import com.github.fo2rist.mclaren.testdata.FEB_2_1AM
import com.github.fo2rist.mclaren.testdata.JAN_1
import com.github.fo2rist.mclaren.testdata.JAN_2
import com.github.fo2rist.mclaren.testdata.JAN_3
import com.github.fo2rist.mclaren.testdata.JAN_31
import com.github.fo2rist.mclaren.testdata.JAN_3_1AM
import com.github.fo2rist.mclaren.testdata.TEST_EVENT_JAN_1
import com.github.fo2rist.mclaren.testdata.TEST_EVENT_JAN_31
import com.github.fo2rist.mclaren.testdata.TEST_EVENT_NO_DATES
import com.github.fo2rist.mclaren.testdata.TEST_EVENT_JAN_1_ONE_PRACTICE
import org.joda.time.DateTime
import org.joda.time.DateTimeUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class CalendarEventTest {

    @Test
    fun `grandPrixName takes value of event when track's event name also present`() {
        val raceEvent = CalendarEvent(
                DUMMY_CIRCUIT.copy(gpName = "P2"),
                DUMMY_EVENT.copy(name = "P1"))

        assertEquals("P1", raceEvent.grandPrixName)
    }

    @Test
    fun `grandPrixName ignores empyt event's name`() {
        val raceEvent = CalendarEvent(
                DUMMY_CIRCUIT.copy(gpName = "P2"),
                DUMMY_EVENT.copy(name = null))

        assertEquals("P2", raceEvent.grandPrixName)
    }

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
    fun `not isActive day before practice time`() {
        assertFalse(TEST_EVENT_JAN_1.isActiveAt(JAN_1))
        assertFalse(TEST_EVENT_JAN_31.isActiveAt(JAN_31))
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
    fun `isActive between practice and race time when other practices are missing`() {
        assertTrue(TEST_EVENT_JAN_1_ONE_PRACTICE.isActiveAt(JAN_1.plusHours(1)))
        assertTrue(TEST_EVENT_JAN_1_ONE_PRACTICE.isActiveAt(JAN_2))
        assertTrue(TEST_EVENT_JAN_1_ONE_PRACTICE.isActiveAt(JAN_3_1AM))
    }

    @Test
    fun `isActive 2h after race start time`() {
        assertTrue(TEST_EVENT_JAN_1.isActiveAt(JAN_3_1AM.plusHours(2)))
        assertTrue(TEST_EVENT_JAN_31.isActiveAt(FEB_2_1AM.plusHours(2)))
    }

    @Test
    fun `not isActive more than 2h after race start time`() {
        assertFalse(TEST_EVENT_JAN_1.isActiveAt(JAN_3_1AM.plusHours(2).plusMinutes(1)))
        assertFalse(TEST_EVENT_JAN_31.isActiveAt(FEB_2_1AM.plusHours(2).plusMinutes(1)))
    }

    @Test
    fun `not isActive if dates are unknown`() {
        assertFalse(TEST_EVENT_NO_DATES.isActiveAt(DateTime()))
    }

    @Test
    fun `timeToFirstPractice returns precise non-zero period for event in the future`() {
        DateTimeUtils.setCurrentMillisFixed(JAN_31.plusHours(1).minusMinutes(1).millis)

        assertEquals(60, TEST_EVENT_JAN_31.timeToFirstPractice()!!.toStandardSeconds().seconds)

        DateTimeUtils.setCurrentMillisSystem()
    }

    @Test
    fun `timeToFirstPractice returns negative period for event in the past`() {
        DateTimeUtils.setCurrentMillisFixed(FEB_2_1AM.plusDays(1).millis)

        assertTrue(TEST_EVENT_JAN_31.timeToFirstPractice()!!.toStandardSeconds().seconds < 0)

        DateTimeUtils.setCurrentMillisSystem()
    }

    @Test
    fun `timeToFirstPractice returns negative null when dates are unknown`() {
        assertNull(TEST_EVENT_NO_DATES.timeToFirstPractice())
    }
}
