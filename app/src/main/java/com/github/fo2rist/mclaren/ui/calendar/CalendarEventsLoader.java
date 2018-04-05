package com.github.fo2rist.mclaren.ui.calendar;

public interface CalendarEventsLoader {
    RaceCalendar loadCurrentCalendar();

    RaceCalendar loadCalendar(int year);
}
