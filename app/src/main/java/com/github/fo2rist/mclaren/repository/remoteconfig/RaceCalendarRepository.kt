package com.github.fo2rist.mclaren.repository.remoteconfig

import com.github.fo2rist.mclaren.ui.models.RaceCalendar

/**
 * Storage of race calendars from current and previous years.
 */
interface RaceCalendarRepository {

    fun loadCalendar(): RaceCalendar
}
