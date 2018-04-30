package com.github.fo2rist.mclaren.ui.models;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class CalendarEventTest {

    @Test
    public void testEndDateCalculatedProperly() throws Exception {
        DateTime endDate = CalendarEvent.calculateEndDate(new DateTime(100, 1, 1, 0, 0));
        assertEquals(new DateTime(100, 1, 3, 0, 0), endDate);

        DateTime endDateAtEdge = CalendarEvent.calculateEndDate(new DateTime(100, 1, 31, 0, 0));
        assertEquals(new DateTime(100, 2, 2, 0, 0), endDateAtEdge);
    }
}
