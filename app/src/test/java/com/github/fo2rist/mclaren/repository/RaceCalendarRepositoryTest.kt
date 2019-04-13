package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.testdata.FakeRemoteConfigService
import com.github.fo2rist.mclaren.ui.models.CalendarEvent
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.joda.time.DateTime
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

private const val EMPTY_JSON_ARRAY = "[]"
private const val SINGLE_EVENT_CALENDAR_JSON = """[{"circuit_id": "australia_melbourne", "date": "2000-01-01"}]"""
private const val SINGLE_CIRCUIT_JSON = """[{
    "id": "australia_melbourne",
    "country": "AUS",
    "track": "Track name",
    "city": "Some city",
    "name": "Some Grand Prix",
    "laps": 1,
    "length": 1.234,
    "distance": 300.000,
    "seasons": "1990–2000",
    "gp_held": 10,
    "wiki_link": "https://dummy.url"
}]"""

@RunWith(RobolectricTestRunner::class)
class RaceCalendarRepositoryTest {

    @Test
    fun `empty calendar produces empty result`() {
        val calendarEvents = RaceCalendarRepositoryImpl(
                FakeRemoteConfigService(EMPTY_JSON_ARRAY, SINGLE_CIRCUIT_JSON)
        ).loadCalendar()

        assertEquals(0, calendarEvents.size)
    }

    @Test
    fun `empty circuits list produces empty result`() {
        val calendarEvents = RaceCalendarRepositoryImpl(
                FakeRemoteConfigService(SINGLE_EVENT_CALENDAR_JSON, EMPTY_JSON_ARRAY)
        ).loadCalendar()

        assertEquals(0, calendarEvents.size)
    }

    @Test
    fun `single match produces one event with track info and date`() {
        val calendarEvents = RaceCalendarRepositoryImpl(
                FakeRemoteConfigService(SINGLE_EVENT_CALENDAR_JSON, SINGLE_CIRCUIT_JSON)
        ).loadCalendar()
        assertEquals(1, calendarEvents.size)

        assertDataCorrect(
                "Track name",
                "Some Grand Prix",
                "Some city",
                2000,
                1,
                1,
                1,
                1.234,
                300.000,
                "1990–2000",
                10,
                calendarEvents[0])
    }

    private fun assertDataCorrect(
        trackName: String,
        gpName: String,
        city: String,
        year: Int,
        month: Int,
        day: Int,
        laps: Int,
        length: Double,
        distance: Double,
        seasons: String,
        gpHeld: Int,
        calendarEvent: CalendarEvent
    ) {
        assertEquals(trackName, calendarEvent.trackName)
        assertEquals(gpName, calendarEvent.grandPrixName)
        assertEquals(city, calendarEvent.city)
        assertEquals(laps, calendarEvent.laps)
        assertDoubleEquals(length, calendarEvent.length)
        assertDoubleEquals(distance, calendarEvent.distance)
        assertEquals(seasons, calendarEvent.seasons)
        assertEquals(gpHeld, calendarEvent.gpHeld)
        assertNotNull(calendarEvent.wikiLink)
        assertEquals(DateTime(year, month, day, 0, 0, 0), calendarEvent.startDate)
    }

    private fun assertDoubleEquals(expected: Double, actual: Double) {
        assertTrue(Math.abs(expected - actual) < 0.00001)
    }
}
