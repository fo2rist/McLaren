package com.github.fo2rist.mclaren.ui.models

import android.support.annotation.VisibleForTesting
import com.github.fo2rist.mclaren.models.Circuit
import com.github.fo2rist.mclaren.models.Event
import org.joda.time.DateTime
import java.io.Serializable

/**
 * Grand Prix event full UI model.
 * Includes track information and the dates.
 */
data class CalendarEvent @VisibleForTesting constructor(
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

    val startDate: DateTime
) : Serializable {
    val endDate: DateTime by lazy { calculateEndDate(startDate) }

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
        startDate = DateTime(grandPrixEvent.date)
    ) {
        require(circuit.track == grandPrixEvent.track)
    }

    /** Is race weekend started or recently over. */
    fun isActiveAt(time: DateTime): Boolean {
        return time >= startDate && time <= endDate.plusHours(23)
        // +23H to cover all time zones
    }

}

private fun calculateEndDate(startDate: DateTime): DateTime {
    return startDate.plusDays(2)
}
