package com.github.fo2rist.mclaren.ui.models

import android.support.annotation.VisibleForTesting
import org.joda.time.DateTime

class RaceCalendar : ArrayList<CalendarEvent>() {

    /**
     * Get current race.
     * @return race event if it's date of the race weekend, null otherwise.
     */
    fun getActiveEvent(): CalendarEvent? {
        val now = DateTime.now()
        return findEventByTime(now)
    }

    @VisibleForTesting
    fun findEventByTime(time: DateTime): CalendarEvent? {
        return this.find {
            it.isActiveAt(time)
        }
    }
}
