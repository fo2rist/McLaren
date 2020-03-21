package com.github.fo2rist.mclaren.ui.models

import com.github.fo2rist.mclaren.testdata.CalendarEvents.createDummyEvent
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
        private const val FIRST_HOUR = 1
        private const val LAST_HOUR = 3

        val FIRST_EVENT = dummyEventStartingAt(FIRST_EVENT_ID, dateTime(FIRST_EVENT_MONTH, FIRST_DAY))
        val SECOND_EVENT = dummyEventStartingAt(SECOND_EVENT_ID, dateTime(SECOND_EVENT_MONTH, FIRST_DAY))

        private fun dateTime(month: Int, day: Int, hour: Int = FIRST_HOUR) =
                DateTime(YEAR, month, day, hour, 0,  DateTimeZone.UTC)

        private fun dummyEventStartingAt(id: String, date: DateTime): CalendarEvent =
                createDummyEvent(id,
                        practice1DateTime = date,
                        practice2DateTime = date.plusHours(1),
                        practice3DateTime = date.plusDays(1),
                        qualifyingDateTime = date.plusDays(1).plusHours(1),
                        raceDateTime = date.plusDays(2))
    }

    private lateinit var raceCalendar: RaceCalendar

    @Before
    fun setUp() {
        raceCalendar = RaceCalendar().apply {
            add(FIRST_EVENT)
            add(SECOND_EVENT)
        }
    }

    @Test
    fun `findEventByTime returns event between start and end time`() {
        //first day of first event
        assertEquals(FIRST_EVENT_ID,
                raceCalendar.findEventByTime(dateTime(FIRST_EVENT_MONTH, FIRST_DAY))?.circuitId)
        //second day of first event
        assertEquals(FIRST_EVENT_ID,
                raceCalendar.findEventByTime(dateTime(FIRST_EVENT_MONTH, FIRST_DAY + 1))?.circuitId)

        //last day of second event
        assertEquals(SECOND_EVENT_ID,
                raceCalendar.findEventByTime(dateTime(SECOND_EVENT_MONTH, FIRST_DAY + 2))?.circuitId)
        //last hour of second event
        assertEquals(SECOND_EVENT_ID,
                raceCalendar.findEventByTime(dateTime(SECOND_EVENT_MONTH, FIRST_DAY + 2, LAST_HOUR))?.circuitId)
    }

    @Test
    fun `findEventByTime return null at not event time`() {
        //before first event
        assertNull(
                raceCalendar.findEventByTime(dateTime(FIRST_EVENT_MONTH - 1, FIRST_DAY)))

        //between events
        assertNull(
                raceCalendar.findEventByTime(dateTime(FIRST_EVENT_MONTH, FIRST_DAY + 10)))

        //after last event
        assertNull(
                raceCalendar.findEventByTime(dateTime( SECOND_EVENT_MONTH + 1, FIRST_DAY)))
    }
}
