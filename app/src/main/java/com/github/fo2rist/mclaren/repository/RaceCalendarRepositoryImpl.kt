package com.github.fo2rist.mclaren.repository

import android.content.Context
import com.github.fo2rist.mclaren.models.Circuit
import com.github.fo2rist.mclaren.models.Event
import com.github.fo2rist.mclaren.ui.models.CalendarEvent
import com.github.fo2rist.mclaren.ui.models.RaceCalendar
import com.github.fo2rist.mclaren.web.RemoteConfigService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject

class RaceCalendarRepositoryImpl
@Inject constructor(
    private val context: Context,
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
        val circuits = readJsonFromAssets(context, "circuits.json", circuitListType)

        val calendarEvents = RaceCalendar()
        if (events == null || circuits == null) {
            return calendarEvents
        }

        for (gp in events) {
            val circuit = findCircuit(circuits, gp)
            if (circuit != null) {
                calendarEvents.add(CalendarEvent(circuit, gp))
            }
        }
        return calendarEvents
    }

    private fun findCircuit(circuits: List<Circuit>, gp: Event): Circuit? {
        for (circuit in circuits) {
            if (circuit.track == gp.track) {
                return circuit
            }
        }
        return null
    }

    private fun <T> readJsonFromAssets(
        context: Context,
        fileName: String,
        objectTypeToken: TypeToken<T>
    ): T? {
        return try {
            context.assets.open(fileName).use { stream ->
                parse(InputStreamReader(stream), objectTypeToken)
            }
        } catch (exc: IOException) {
            //that should never happen in prod, or at least it mean apk is broken
            null
        }

    }

    /**
     * @throws com.google.gson.JsonParseException see [Gson.fromJson] for details.
     */
    private fun <T> parse(reader: InputStreamReader, objectTypeToken: TypeToken<T>): T? =
            Gson().fromJson<T>(reader, objectTypeToken.type)

    /**
     * @throws com.google.gson.JsonParseException see [Gson.fromJson] for details.
     */
    private fun <T> parse(json: String, objectTypeToken: TypeToken<T>): T? {
        return Gson().fromJson<T>(json, objectTypeToken.type)
    }

}
