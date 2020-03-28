package com.github.fo2rist.mclaren.web.models

import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * Model for whole race life transmission (team communications).
 */
data class Transmission(
    val id: Long = 0,
    val name: String = "", // eg "Australian Grand Prix"
    val telemetries: Telemetry = Telemetry(),
    val commentaries: List<TransmissionItem> = emptyList()
)

/**
 * Extended info about drivers current telemetry.
 */
data class Telemetry(
    val driverA: Driver = Driver(),
    val driverB: Driver = Driver()
)

/**
 * Models for Driver info including position in [Transmission].
 */
data class Driver(
    val driverName: String = "",
    val lap: String = "", // eg. "58 / 58",
    val lapTime: String = "",
    val tyre: String = "" // eg. "s-soft"
)

/**
 * Item (message) in [Transmission].
 */
data class TransmissionItem(
    val id: Long,
    val sourceId: Long,
    val date: Date, // eg. "2018-03-23T00:47:08+00:00",
    val driverName: String?,
    val grandPrixId: Long,
    val initials: String,
    val message: String,
    val session: TransmissionSession?, // null or "Practice", "Qualification", "Race"
    val type: TransmissionMessageType?
)

/**
 * Type of race session transmission belongs to.
 */
enum class TransmissionSession {
    @SerializedName("Practice")
    PRACTICE,

    @SerializedName("Qualification")
    QUALIFICATION,

    @SerializedName("Race")
    RACE
}

/**
 * Type of message (sender and receiver) in transmission.
 */
enum class TransmissionMessageType {
    ATP, // racer A to pit
    BTP, // racer B to pit
    PTA, // pit to racer A
    PTB, // pit to racer B
    PIT, // general talks //marked pitwall
    ALY, // general status messages
    COM, // general status message //non marked
    CAA, // about racer A
    CAB, // about racer B
}
