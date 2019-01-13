package com.github.fo2rist.mclaren.testdata

import com.github.fo2rist.mclaren.ui.models.CalendarEvent
import org.joda.time.DateTime

object CalendarEvents {

    fun createDummyEvent(startDate: DateTime = DateTime()): CalendarEvent {
        return CalendarEvent(
                circuitId = "",
                countryCode = "",
                trackName = "",
                city = "",
                grandPrixName = "",
                laps = 1,
                length = 0.0,
                distance = 0.0,
                seasons = "",
                gpHeld = 0,
                wikiLink = "",
                startDate = startDate
        )
    }
}
