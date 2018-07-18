package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.ui.models.RaceCalendar

interface RaceCalendarRepository {
    fun loadCurrentCalendar(): RaceCalendar

    fun loadCalendar(year: Int): RaceCalendar
}
