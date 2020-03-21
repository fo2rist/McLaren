package com.github.fo2rist.mclaren.testdata

import com.github.fo2rist.mclaren.models.Circuit
import com.github.fo2rist.mclaren.models.Event
import com.github.fo2rist.mclaren.ui.models.CalendarEvent
import org.joda.time.DateTime

object CalendarEvents {

    fun createDummyEvent() =
            createDummyEvent("", DateTime(), DateTime(), DateTime(), DateTime(), DateTime())

    fun createDummyEvent(
        id: String = "",
        practice1DateTime: DateTime,
        practice2DateTime: DateTime,
        practice3DateTime: DateTime,
        qualifyingDateTime: DateTime,
        raceDateTime: DateTime
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
