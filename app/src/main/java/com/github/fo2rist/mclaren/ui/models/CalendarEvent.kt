package com.github.fo2rist.mclaren.ui.models

import android.support.annotation.VisibleForTesting
import com.github.fo2rist.mclaren.models.Circuit
import com.github.fo2rist.mclaren.models.Event
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.Interval
import org.joda.time.Period
import java.io.Serializable

/**
 * Limit on race duration.
 */
const val RACE_DURATION_H = 2

/**
 * Grand Prix event full UI model.
 * Includes track information and the dates.
 */
data class CalendarEvent private constructor(
    val circuitId: String,
    val countryCode: String,
    val trackName: String,
    val city: String,
    val grandPrixName: String,
    val laps: Int,
    val length: Double,
    val distance: Double,
    val seasons: String,
    val gpHeld: Int,
    val wikiLink: String,

    val practice1DateTime: DateTime,
    val practice2DateTime: DateTime,
    val practice3DateTime: DateTime,
    val qualifyingDateTime: DateTime,
    val raceDateTime: DateTime
) : Serializable {

    val startDate: DateTime by lazy { practice1DateTime.withTimeAtStartOfDay() }
    val endDate: DateTime by lazy { raceDateTime.withTimeAtStartOfDay() }

    constructor(circuit: Circuit, grandPrixEvent: Event) : this(
            circuitId = circuit.id,
            countryCode = circuit.country,
            trackName = circuit.track,
            city = circuit.city,
            grandPrixName = circuit.name,
            laps = circuit.laps,
            length = circuit.length,
            distance = circuit.distance,
            seasons = circuit.seasons,
            gpHeld = circuit.gpHeld,
            wikiLink = circuit.wikiLink,
            practice1DateTime = DateTime.parse(grandPrixEvent.practice1Time),
            practice2DateTime = DateTime.parse(grandPrixEvent.practice2Time),
            practice3DateTime = DateTime.parse(grandPrixEvent.practice3Time),
            qualifyingDateTime =DateTime.parse(grandPrixEvent.qualifyingTime),
            raceDateTime = DateTime.parse(grandPrixEvent.raceTime)
    ) {
        require(circuit.id == grandPrixEvent.circuit_id)
    }

    /** Is race weekend started or recently over. */
    fun isActiveAt(time: DateTime): Boolean {
        return time >= practice1DateTime && time <= raceDateTime.plusHours(RACE_DURATION_H)
    }

    /**
     * Time to the beginning of weekend (1st practice) as [Period].
     * @return period with all standard fields, negative if current time is greater than practice start time.
     */
    fun timeToFirstPractice(): Period = Period(DateTime.now(), practice1DateTime)
}

