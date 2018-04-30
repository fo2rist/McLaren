package com.github.fo2rist.mclaren.ui.models

import android.support.annotation.VisibleForTesting
import org.joda.time.DateTime

class RaceCalendar : ArrayList<CalendarEvent>() {

    /**
     * Get current race.
     * @return race event if it's date of the race weekend, null otherwise.
     */
    fun getActiveEvent(): CalendarEvent? {
        val today = DateTime.now()
        return findEventByTime(today)
    }

    @VisibleForTesting
    fun findEventByTime(currentTime: DateTime): CalendarEvent? {
        return this.find {
            currentTime >= it.startDate && currentTime <= it.endDate.plusHours(23) // +23H to cover all time zones
        }
    }
}
