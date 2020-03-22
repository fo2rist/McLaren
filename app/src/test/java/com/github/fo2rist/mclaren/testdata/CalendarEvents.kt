package com.github.fo2rist.mclaren.testdata

import com.github.fo2rist.mclaren.models.Circuit
import com.github.fo2rist.mclaren.models.Event
import com.github.fo2rist.mclaren.ui.models.CalendarEvent
import org.joda.time.DateTime


private const val T_0AM = "00:00Z"
private const val T_1AM = "01:00Z"

val JAN_1: DateTime = DateTime.parse("2100-01-01T$T_0AM")
val JAN_2: DateTime = DateTime.parse("2100-01-02T$T_0AM")
val JAN_3: DateTime = DateTime.parse("2100-01-03T$T_0AM")
val JAN_3_1AM: DateTime = DateTime.parse("2100-01-03T$T_1AM")

val JAN_31: DateTime = DateTime.parse("2100-01-31T$T_0AM")
val FEB_2: DateTime = DateTime.parse("2100-02-02T$T_0AM")
val FEB_2_1AM: DateTime = DateTime.parse("2100-02-02T$T_1AM")

/** An event within the same month. */
val TEST_EVENT_JAN_1 = CalendarEvents.createDummyEvent(
        "event_jan_01_circuit_id",
        practice1DateTime = JAN_1.plusHours(1),
        practice2DateTime = JAN_1.plusHours(2),
        practice3DateTime = JAN_2.plusHours(1),
        qualifyingDateTime = JAN_2.plusHours(2),
        raceDateTime = JAN_3_1AM)
/** An event that spans two months. */
val TEST_EVENT_JAN_31 = CalendarEvents.createDummyEvent(
        "event_jan_31_circuit_id",
        practice1DateTime = JAN_31.plusHours(1),
        practice2DateTime = JAN_31.plusHours(2),
        practice3DateTime = JAN_31.plusHours(25),
        qualifyingDateTime = JAN_31.plusHours(26),
        raceDateTime = FEB_2_1AM)

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
