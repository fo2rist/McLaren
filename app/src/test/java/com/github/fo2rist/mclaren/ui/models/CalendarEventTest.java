package com.github.fo2rist.mclaren.ui.models;

import java.sql.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class CalendarEventTest {

    @Test
    public void testEndDateCalculatedProperly() throws Exception {
        CalendarEvent event = new CalendarEvent("", "", "", "", "",
                new Date(100, 0, 1));
        assertEquals(new Date(100, 0, 3), event.endDate);

        CalendarEvent eventAtEdge = new CalendarEvent("", "", "", "", "",
                new Date(100, 0, 31));
        assertEquals(new Date(100, 1, 2), eventAtEdge.endDate);
    }
}
