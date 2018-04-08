package com.github.fo2rist.mclaren.ui.calendar;

import android.support.annotation.Nullable;

import com.github.fo2rist.mclaren.ui.models.CalendarEvent;
import java.util.ArrayList;

public class RaceCalendar extends ArrayList<CalendarEvent> {

    /**
     * Get current race.
     * @return race event if it's date of the race weekend, null otherwise.
     */
    @Nullable
    public CalendarEvent getActiveEvent() {
        if (!isEmpty()) {
            return get(0); //TODO implement
        } else {
            return null;
        }
    }
}
