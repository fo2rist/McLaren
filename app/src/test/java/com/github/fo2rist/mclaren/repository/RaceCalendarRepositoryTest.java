package com.github.fo2rist.mclaren.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.fo2rist.mclaren.ui.models.CalendarEvent;
import com.github.fo2rist.mclaren.ui.models.RaceCalendar;
import com.github.fo2rist.mclaren.web.RemoteConfigService;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class RaceCalendarRepositoryTest {

    private static String NULL_JSON = null;//"[]";
    private static String SINGLE_EVENT_CALENDAR_JSON
            = "[{\"circuit_id\": \"australia_melbourne\", \"date\": \"2000-01-01\"}]";

    private Context context;

    private static class FakeRemoteConfigService implements RemoteConfigService {
        private String calendar;

        FakeRemoteConfigService(String calendar) {
            this.calendar = calendar;
        }

        @NonNull public String getCalendar() {
            return calendar;
        }

        public void fetchConfig() {
        }
    }

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application.getApplicationContext();
    }

    @Test
    public void test_nullJson_producesEmptyResult() {
        RaceCalendar calendarEvents = new RaceCalendarRepositoryImpl(
                context,
                new FakeRemoteConfigService(NULL_JSON)
        ).loadCalendar();
        assertEquals(0, calendarEvents.size());
    }

    @Test
    public void test_AustraliaSingleEventCalendar_parsedSuccessfully() {
        RaceCalendar calendarEvents = new RaceCalendarRepositoryImpl(
                context,
                new FakeRemoteConfigService(SINGLE_EVENT_CALENDAR_JSON)
        ).loadCalendar();
        assertEquals(1, calendarEvents.size());

        assertDataCorrect(
                "Melbourne Grand Prix Circuit",
                "Australian Grand Prix",
                "Melbourne",
                2000,
                1,
                1,
                58,
                5.303,
                307.574,
                "1996–2017",
                22,
                calendarEvents.get(0));
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
        assertEquals(trackName, calendarEvent.getTrackName());
        assertEquals(gpName, calendarEvent.getGrandPrixName());
        assertEquals(city, calendarEvent.getCity());
        assertEquals(laps, calendarEvent.getLaps());
        assertDoubleEquals(length, calendarEvent.getLength());
        assertDoubleEquals(distance, calendarEvent.getDistance());
        assertEquals(seasons, calendarEvent.getSeasons());
        assertEquals(gpHeld, calendarEvent.getGpHeld());
        assertNotNull(calendarEvent.getWikiLink());
        assertEquals(new DateTime(year, month, day, 0, 0, 0), calendarEvent.getStartDate());
    }

    private void assertDoubleEquals(double expected, double actual) {
        assertTrue(Math.abs(expected - actual) < 0.00001);
    }
}
