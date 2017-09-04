package com.github.fo2rist.mclaren.ui.models;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CalendarEventsLoaderTest {

    private Context context;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testCalendar2017() throws Exception {
        List<CalendarEvent> calendarEvents = new CalendarEventsLoader(context, 2017).getCalendar();
        assertEquals(20, calendarEvents.size());

        assertDataCorrect(
                "Melbourne Grand Prix Circuit",
                "Australian Grand Prix",
                "Melbourne",
                2017,
                3,
                24,
                calendarEvents.get(0));
        assertDataCorrect(
                "Hungaroring",
                "Hungarian Grand Prix",
                "Budapest",
                2017,
                7,
                28,
                calendarEvents.get(10));
        assertDataCorrect(
                "Yas Marina Circuit",
                "Abu Dhabi Grand Prix",
                "Abu Dhabi",
                2017,
                11,
                24,
                calendarEvents.get(19));
    }

    private void assertDataCorrect(String trackName, String gpName, String city, int year, int month, int day,
            CalendarEvent calendarEvent) {
        assertEquals(trackName, calendarEvent.trackName);
        assertEquals(gpName, calendarEvent.grandPrixName);
        assertEquals(city, calendarEvent.city);
        assertEquals(new Date(year - 1900, month - 1, day), calendarEvent.startDate);
    }
}
