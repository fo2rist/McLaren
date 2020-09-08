package com.github.fo2rist.mclaren.testdata

import com.github.fo2rist.mclaren.models.Circuit
import com.github.fo2rist.mclaren.models.Event
import com.github.fo2rist.mclaren.ui.models.CalendarEvent
import org.joda.time.DateTime
import java.sql.Date

val DUMMY_CIRCUIT = Circuit(
        id = "some_id",
        country = "",
        track = "",
        city = "",
        gpName = "",
        laps = 1,
        length = 0.0,
        distance = 0.0,
        seasons = "",
        gpHeld = 0,
        wikiLink = "")

val DUMMY_EVENT = Event(
        name = null,
        circuit_id = DUMMY_CIRCUIT.id,
        practice1Time = null,
        practice2Time = null,
        practice3Time = null,
        qualifyingTime = null,
        raceTime = null,
        date = Date.valueOf("2020-10-01"))

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

/** An event without dates. */
val TEST_EVENT_NO_DATES = CalendarEvents.createDummyEvent("unknown",
        practice1DateTime = null,
        practice2DateTime = null,
        practice3DateTime = null,
        qualifyingDateTime = null,
        raceDateTime = null)

/** An event 2 practices absent. */
val TEST_EVENT_JAN_1_ONE_PRACTICE = CalendarEvents.createDummyEvent("partial events",
        practice1DateTime = JAN_1.plusHours(1),
        practice2DateTime = null,
        practice3DateTime = null,
        qualifyingDateTime = JAN_2.plusHours(2),
        raceDateTime = JAN_3_1AM)

object CalendarEvents {

    @JvmStatic
    @JvmOverloads
    fun createDummyEvent(sameTime: DateTime = DateTime.now()): CalendarEvent {
        return createDummyEvent("id", sameTime, sameTime, sameTime, sameTime, sameTime)
    }

    fun createDummyEvent(
        id: String = "id",
        practice1DateTime: DateTime?,
        practice2DateTime: DateTime?,
        practice3DateTime: DateTime?,
        qualifyingDateTime: DateTime?,
        raceDateTime: DateTime?
    ): CalendarEvent {
        return CalendarEvent(
                DUMMY_CIRCUIT.copy(id = id),
                Event(
                        circuit_id = id,
                        practice1Time = practice1DateTime?.toString(),
                        practice2Time = practice2DateTime?.toString(),
                        practice3Time = practice3DateTime?.toString(),
                        qualifyingTime = qualifyingDateTime?.toString(),
                        raceTime = raceDateTime?.toString(),
                        date = raceDateTime?.withTimeAtStartOfDay()?.toDate())
        )
    }
}
