package com.github.fo2rist.mclaren.testdata

import com.github.fo2rist.mclaren.models.Circuit
import com.github.fo2rist.mclaren.models.Event
import com.github.fo2rist.mclaren.ui.models.CalendarEvent
import org.joda.time.DateTime

object CalendarEvents {

    fun createDummyEvent(
        id: String = "",

        practice1DateTime: DateTime = DateTime(),
        practice2DateTime: DateTime = DateTime(),
        practice3DateTime: DateTime = DateTime(),
        qualifyingDateTime: DateTime = DateTime(),
        raceDateTime: DateTime = DateTime()
    ): CalendarEvent {
        return CalendarEvent(
                Circuit(
                        id = id,
                        country = "",
                        track = "",
                        city = "",
                        name = "",
                        laps = 1,
                        length = 0.0,
                        distance = 0.0,
                        seasons = "",
                        gpHeld = 0,
                        wikiLink = ""),
                Event(
                        circuit_id = id,
                        practice1Time = practice1DateTime.toString(),
                        practice2Time = practice2DateTime.toString(),
                        practice3Time = practice3DateTime.toString(),
                        qualifyingTime = qualifyingDateTime.toString(),
                        raceTime = raceDateTime.toString(),
                        date = raceDateTime.withTimeAtStartOfDay().toDate())
        )
    }
}
