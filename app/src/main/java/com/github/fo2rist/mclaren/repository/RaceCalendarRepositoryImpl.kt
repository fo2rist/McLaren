package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.models.Circuit
import com.github.fo2rist.mclaren.models.Event
import com.github.fo2rist.mclaren.ui.models.CalendarEvent
import com.github.fo2rist.mclaren.ui.models.RaceCalendar
import com.github.fo2rist.mclaren.web.RemoteConfigService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class RaceCalendarRepositoryImpl
@Inject constructor(
    private val remoteConfigService: RemoteConfigService
) : RaceCalendarRepository {

    private val grandPrixListType = object : TypeToken<ArrayList<Event>>() {}
    private val circuitListType = object : TypeToken<ArrayList<Circuit>>() {}

    /**
     * Get Grand Prix calendar for given year
     * @return calendar non empty, if year found.
     */
    override fun loadCalendar(): RaceCalendar {
        val events = parse(remoteConfigService.calendar, grandPrixListType)
        val circuits = parse(remoteConfigService.circuits, circuitListType)

        if (events.isNullOrEmpty() || circuits.isNullOrEmpty()) {
            return RaceCalendar.EMPTY
        }

        val calendarEvents = RaceCalendar()
        for (gp in events) {
            val circuit = circuits.find { it.id == gp.circuit_id }
            if (circuit != null) {
                calendarEvents.add(CalendarEvent(circuit, gp))
            }
        }
        return calendarEvents
    }

    /**
     * @throws com.google.gson.JsonParseException see [Gson.fromJson] for details.
     */
    private fun <T> parse(json: String, objectTypeToken: TypeToken<T>): T? {
        return Gson().fromJson<T>(json, objectTypeToken.type)
    }

}
