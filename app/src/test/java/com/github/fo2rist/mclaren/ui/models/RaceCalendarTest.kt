package com.github.fo2rist.mclaren.ui.models

import com.github.fo2rist.mclaren.testdata.FEB_2
import com.github.fo2rist.mclaren.testdata.FEB_2_1AM
import com.github.fo2rist.mclaren.testdata.JAN_1
import com.github.fo2rist.mclaren.testdata.JAN_2
import com.github.fo2rist.mclaren.testdata.JAN_3
import com.github.fo2rist.mclaren.testdata.JAN_31
import com.github.fo2rist.mclaren.testdata.TEST_EVENT_JAN_1
import com.github.fo2rist.mclaren.testdata.TEST_EVENT_JAN_31
import com.github.fo2rist.mclaren.testdata.TEST_EVENT_NO_DATES
import org.joda.time.DateTimeUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RaceCalendarTest {

    private lateinit var raceCalendar: RaceCalendar

    @Before
    fun setUp() {
        raceCalendar = RaceCalendar().apply {
            add(TEST_EVENT_JAN_1)
            add(TEST_EVENT_NO_DATES)
            add(TEST_EVENT_JAN_31)
        }
    }

    @Test
    fun `findEventByTime returns event between start and end time`() {
        //second day of first event
        assertEquals(TEST_EVENT_JAN_1, raceCalendar.findEventByTime(JAN_2))
        //third day of first event
        assertEquals(TEST_EVENT_JAN_1, raceCalendar.findEventByTime(JAN_3))

        //last day of second event
        assertEquals(TEST_EVENT_JAN_31, raceCalendar.findEventByTime(FEB_2))
        //last hour of second event
        assertEquals(TEST_EVENT_JAN_31, raceCalendar.findEventByTime(FEB_2_1AM))
    }

    @Test
    fun `findEventByTime return null at not event time`() {
        //before first event
        assertNull(raceCalendar.findEventByTime(JAN_1))

        //between events
        assertNull(raceCalendar.findEventByTime(JAN_31.minusDays(1)))

        //after last event
        assertNull(raceCalendar.findEventByTime(JAN_31.plusDays(10)))
    }

    @Test
    fun `getNextEvent returns event when it exists`() {
        //before first
        DateTimeUtils.setCurrentMillisFixed(JAN_1.plusHours(1).minusSeconds(1).millis)
        assertEquals(TEST_EVENT_JAN_1, raceCalendar.getNextEvent())

        //before second
        DateTimeUtils.setCurrentMillisFixed(JAN_31.plusHours(1).minusSeconds(1).millis)
        assertEquals(TEST_EVENT_JAN_31, raceCalendar.getNextEvent())

        DateTimeUtils.setCurrentMillisSystem()
    }

    @Test
    fun `getNextEvent returns negative null after end of season`() {
        DateTimeUtils.setCurrentMillisFixed(FEB_2_1AM.plusHours(RACE_DURATION_H).plusSeconds(1).millis)

        assertNull(raceCalendar.getNextEvent())

        DateTimeUtils.setCurrentMillisSystem()
    }
}
