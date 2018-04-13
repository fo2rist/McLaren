package com.github.fo2rist.mclaren.tests.calendar;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.github.fo2rist.mclaren.repository.RaceCalendarRepositoryImpl;
import com.github.fo2rist.mclaren.ui.models.RaceCalendar;
import com.github.fo2rist.mclaren.ui.models.CalendarEvent;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CalendarEventsLoaderTest {

    private Context context;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testCalendar2017() throws Exception {
        RaceCalendar calendarEvents = new RaceCalendarRepositoryImpl(context).loadCalendar(2017);
        assertEquals(20, calendarEvents.size());

        assertDataCorrect(
                "Melbourne Grand Prix Circuit",
                "Australian Grand Prix",
                "Melbourne",
                2017,
                3,
                24,
                58,
                5.303,
                307.574,
                "1996–2017",
                22,
                calendarEvents.get(0));
        assertDataCorrect(
                "Hungaroring",
                "Hungarian Grand Prix",
                "Budapest",
                2017,
                7,
                28,
                70,
                4.381,
                306.630,
                "1986–2017",
                32,
                calendarEvents.get(10));
        assertDataCorrect(
                "Yas Marina Circuit",
                "Abu Dhabi Grand Prix",
                "Abu Dhabi",
                2017,
                11,
                24,
                55,
                5.554,
                305.355,
                "2009–2017",
                9,
                calendarEvents.get(19));
    }

    @Test
    public void testCalendar2018() throws Exception {
        RaceCalendar calendarEvents = new RaceCalendarRepositoryImpl(context).loadCalendar(2018);
        assertEquals(21, calendarEvents.size());

        assertDataCorrect(
                "Melbourne Grand Prix Circuit",
                "Australian Grand Prix",
                "Melbourne",
                2018,
                3,
                23,
                58,
                5.303,
                307.574,
                "1996–2017",
                22,
                calendarEvents.get(0));

        assertDataCorrect(
                "Circuit Paul Ricard",
                "French Grand Prix",
                "Le Castellet",
                2018,
                6,
                22,
                52,
                5.842,
                303.784,
                "1971, 1973, 1975–1976, 1978, 1980, 1982–1983, 1985–1990",
                14,
                calendarEvents.get(7));
    }

    private void assertDataCorrect(String trackName,
            String gpName,
            String city,
            int year,
            int month,
            int day,
            int laps,
            double length,
            double distance,
            String seasons,
            int gpHeld,
            CalendarEvent calendarEvent) {
        assertEquals(trackName, calendarEvent.trackName);
        assertEquals(gpName, calendarEvent.grandPrixName);
        assertEquals(city, calendarEvent.city);
        assertEquals(laps, calendarEvent.laps);
        assertDoubleEquals(length, calendarEvent.length);
        assertDoubleEquals(distance, calendarEvent.distance);
        assertEquals(seasons, calendarEvent.seasons);
        assertEquals(gpHeld, calendarEvent.gpHeld);
        assertNotNull(calendarEvent.wikiLink);
        assertEquals(new DateTime(year, month, day, 0, 0,0 ).toDate(), calendarEvent.startDate);
    }

    private void assertDoubleEquals(double expected, double actual) {
        assertTrue(Math.abs(expected - actual) < 0.00001);
    }
}
