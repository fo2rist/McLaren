package com.github.fo2rist.mclaren.ui.models

import android.support.annotation.VisibleForTesting
import org.joda.time.DateTime
import org.joda.time.Period

class RaceCalendar : ArrayList<CalendarEvent>() {

    /**
     * Get current race.
     * @return race event if it's date of the race weekend, null otherwise.
     */
    fun getActiveEvent(): CalendarEvent? {
        val now = DateTime.now()
        return findEventByTime(now)
    }

    /**
     * Get next event in current season as [Period].
     * @return null if past season end.
     */
    fun getNextEvent(): CalendarEvent? {
        val now = DateTime.now()
        //first event in future
        return this.firstOrNull { it.practice1DateTime != null && it.practice1DateTime >= now }
    }

    @VisibleForTesting
    fun findEventByTime(time: DateTime): CalendarEvent? {
        return this.find {
            it.isActiveAt(time)
        }
    }

    companion object {
        val EMPTY = RaceCalendar()
    }
}
