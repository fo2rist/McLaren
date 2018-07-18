package com.github.fo2rist.mclaren.ui.models

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RaceCalendarTest {

    companion object {
        private const val FIRST_EVENT_ID = "first"
        private const val SECOND_EVENT_ID = "second"
        private const val YEAR = 2018
        private const val FIRST_EVENT_MONTH = 3
        private const val SECOND_EVENT_MONTH = 5
        private const val FIRST_DAY = 1
        private const val FIRST_HOUR = 0
        private const val LAST_HOUR = 23
    }

    lateinit var raceCalendar: RaceCalendar

    @Before
    fun setUp() {
        raceCalendar = RaceCalendar().apply {
            add(dummyEventFor(FIRST_EVENT_ID, dateTime(FIRST_EVENT_MONTH, FIRST_DAY)))
            add(dummyEventFor(SECOND_EVENT_ID, dateTime(SECOND_EVENT_MONTH, FIRST_DAY)))
        }
    }

    private fun dateTime(month: Int, day: Int, hour: Int = FIRST_HOUR, timeZone: DateTimeZone = DateTimeZone.UTC) =
            DateTime(YEAR, month, day, hour, 0, timeZone)

    private fun dummyEventFor(id: String, date: DateTime): CalendarEvent {
        return CalendarEvent(id,
                "", "", "", "", 0, 0.0, 0.0, "", 0, "", date)
    }

    @Test
    fun testKnownDates() {
        //before first event
        assertNull(
                raceCalendar.findEventByTime(dateTime( FIRST_EVENT_MONTH - 1, FIRST_DAY)))
        //first day of first event
        assertEquals(FIRST_EVENT_ID,
                raceCalendar.findEventByTime(dateTime(FIRST_EVENT_MONTH, FIRST_DAY))?.circuitId)
        //second day of first event
        assertEquals(FIRST_EVENT_ID,
                raceCalendar.findEventByTime(dateTime( FIRST_EVENT_MONTH, FIRST_DAY + 1))?.circuitId)
        //between events
        assertNull(
                raceCalendar.findEventByTime(dateTime( FIRST_EVENT_MONTH, FIRST_DAY + 10)))
        //last day of second day
        assertEquals(SECOND_EVENT_ID,
                raceCalendar.findEventByTime(dateTime( SECOND_EVENT_MONTH, FIRST_DAY + 2))?.circuitId)
        //after last event
        assertNull(
                raceCalendar.findEventByTime(dateTime( SECOND_EVENT_MONTH + 1, FIRST_DAY)))
    }

    @Test
    fun testEndOfTheWeekend() {
        assertEquals(SECOND_EVENT_ID,
                raceCalendar.findEventByTime(dateTime(SECOND_EVENT_MONTH, FIRST_DAY + 2, LAST_HOUR))?.circuitId)
    }
}
