package com.github.fo2rist.mclaren.ui.models

import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RaceCalendarTest {

    private val FIRST = "first"
    private val SECOND = "second"
    lateinit var raceCalendar: RaceCalendar

    @Before
    fun setUp() {
        raceCalendar = RaceCalendar().apply {
            add(dummyEventFor(FIRST, "2018-03-01"))
            add(dummyEventFor(SECOND, "2018-05-01"))
        }
    }

    private fun dummyEventFor(id: String, date: String): CalendarEvent {
        return CalendarEvent(id,
                "", "", "", "", 0, 0.0, 0.0, "", 0, "",
                DateTime.parse(date + "T01:00:00Z").toDate())
    }

    @Test
    fun testKnownDates() {
        //before first event
        assertNull(
                raceCalendar.getEventForDate(DateTime.parse("2018-02-15T01:00:00Z")))
        //first day of first event
        assertEquals(FIRST,
                raceCalendar.getEventForDate(DateTime.parse("2018-03-01T01:00:00Z"))?.circuitId)
        //second day of first event
        assertEquals(FIRST,
                raceCalendar.getEventForDate(DateTime.parse("2018-03-02T01:00:00Z"))?.circuitId)
        //between events
        assertNull(
                raceCalendar.getEventForDate(DateTime.parse("2018-03-15T01:00:00Z")))
        //last day of second day
        assertEquals(SECOND,
                raceCalendar.getEventForDate(DateTime.parse("2018-05-03T01:00:00Z"))?.circuitId)
        //after last event
        assertNull(
                raceCalendar.getEventForDate(DateTime.parse("2018-05-15T01:00:00Z")))
    }
}
