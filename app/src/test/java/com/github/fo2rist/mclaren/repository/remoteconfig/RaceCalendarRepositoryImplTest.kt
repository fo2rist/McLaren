package com.github.fo2rist.mclaren.repository.remoteconfig

import com.github.fo2rist.mclaren.testutilities.fakes.FakeRemoteConfigService
import com.github.fo2rist.mclaren.ui.models.CalendarEvent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.joda.time.DateTime
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

private const val EMPTY_JSON_ARRAY = "[]"
private const val SINGLE_EVENT_CALENDAR_JSON = """[{
    "circuit_id": "some_circuit_id",
    "practice_1_time": "2000-01-02T01:00:00Z",
    "practice_2_time": "2000-01-02T05:00:00Z",
    "practice_3_time": "2000-01-03T03:00:00Z",
    "qualifying_time": "2000-01-03T06:00:00Z",
    "race_time": "2000-01-04T05:10:00Z",
    "date": "2000-01-02"
}]"""
private const val SINGLE_CIRCUIT_JSON = """[{
    "id": "some_circuit_id",
    "country": "AUS",
    "track": "Track name",
    "city": "Some city",
    "name": "Some Grand Prix",
    "laps": 50,
    "length": 1.234,
    "distance": 300.000,
    "seasons": "1990–2000",
    "gp_held": 10,
    "wiki_link": "https://dummy.url"
}]"""

@RunWith(RobolectricTestRunner::class)
class RaceCalendarRepositoryImplTest {

    @Test
    fun `empty calendar + circuits list produces empty result`() {
        val calendarEvents = RaceCalendarRepositoryImpl(
                FakeRemoteConfigService(EMPTY_JSON_ARRAY, SINGLE_CIRCUIT_JSON)
        ).loadCalendar()

        assertEquals(0, calendarEvents.size)
    }

    @Test
    fun `calendar + empty circuits list produces empty result`() {
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
                50,
                1.234,
                300.000,
                "1990–2000",
                10,
                calendarEvents[0],
                DateTime.parse("2000-01-02T01:00:00Z"),
                DateTime.parse("2000-01-02T05:00:00Z"),
                DateTime.parse("2000-01-03T03:00:00Z"),
                DateTime.parse("2000-01-03T06:00:00Z"),
                DateTime.parse("2000-01-04T05:10:00Z")
        )
    }

    private fun assertDataCorrect(
        trackName: String,
        gpName: String,
        city: String,
        laps: Int,
        length: Double,
        distance: Double,
        seasons: String,
        gpHeld: Int,
        calendarEvent: CalendarEvent,
        practice1Time: DateTime?,
        practice2Time: DateTime?,
        practice3Time: DateTime?,
        qualifyingTime: DateTime?,
        raceTime: DateTime?
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
        assertEquals(practice1Time, calendarEvent.practice1DateTime)
        assertEquals(practice2Time, calendarEvent.practice2DateTime)
        assertEquals(practice3Time, calendarEvent.practice3DateTime)
        assertEquals(qualifyingTime, calendarEvent.qualifyingDateTime)
        assertEquals(raceTime, calendarEvent.raceDateTime)
    }

    private fun assertDoubleEquals(expected: Double, actual: Double) {
        assertTrue(Math.abs(expected - actual) < 0.00001)
    }
}
