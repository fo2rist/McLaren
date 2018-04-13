package com.github.fo2rist.mclaren.repository

import android.content.Context
import com.github.fo2rist.mclaren.models.Circuit
import com.github.fo2rist.mclaren.models.Event
import com.github.fo2rist.mclaren.ui.models.CalendarEvent
import com.github.fo2rist.mclaren.ui.models.RaceCalendar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.joda.time.LocalDate
import java.io.IOException
import java.io.InputStreamReader
import java.util.*
import javax.inject.Inject

class RaceCalendarRepositoryImpl
@Inject constructor(
        private val context: Context
) : RaceCalendarRepository {

    private val grandPrixListType = object : TypeToken<ArrayList<Event>>() {}
    private val circuitListType = object : TypeToken<ArrayList<Circuit>>() {}

    override fun loadCurrentCalendar(): RaceCalendar {
        val date = LocalDate()
        val currentCalendar = loadCalendar(date.year)
        return if (!currentCalendar.isEmpty())
            currentCalendar
        else
            loadCalendar(date.year - 1)
    }

    /**
     * Get Grand Prix calendar for given year
     * @return calendar non empty, if year found.
     */
    override fun loadCalendar(year: Int): RaceCalendar {
        val events = readJsonFromAssets(context, "calendar_$year.json", grandPrixListType)
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
            objectTypeToken: TypeToken<T>): T? {
        try {
            val stream = context.assets.open(fileName)
            val reader = InputStreamReader(stream)
            val result = Gson().fromJson<T>(reader, objectTypeToken.type)
            stream.close()
            return result
        } catch (exc: IOException) {
            //that should never happen in prod, or at least it mean apk is broken
            return null
        }

    }
}
