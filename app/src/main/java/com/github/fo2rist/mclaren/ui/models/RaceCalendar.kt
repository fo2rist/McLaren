package com.github.fo2rist.mclaren.ui.models

import android.support.annotation.VisibleForTesting
import org.joda.time.DateTime
import java.util.*

class RaceCalendar : ArrayList<CalendarEvent>() {

    /**
     * Get current race.
     * @return race event if it's date of the race weekend, null otherwise.
     */
    fun getActiveEvent(): CalendarEvent? {
        val today = DateTime.now()
        return getEventForDate(today)
    }

    @VisibleForTesting
    fun getEventForDate(today: DateTime): CalendarEvent? {
        return this.find {
            today >= DateTime(it.startDate.time) && today <= DateTime(it.endDate.time)
        }
    }
}
