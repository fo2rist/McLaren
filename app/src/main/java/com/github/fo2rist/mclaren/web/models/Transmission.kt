package com.github.fo2rist.mclaren.web.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Transmission(
        val id: Long = 0,
        val name: String = "", // eg "Australian Grand Prix"
        val telemetries: Telemetry = Telemetry(),
        val commentaries: List<TransmissionItem> = emptyList()
)

data class Telemetry(
        val driverA: Driver = Driver(),
        val driverB: Driver = Driver()
)

data class Driver(
        val driverName: String = "",
        val lap: String = "", // eg. "58 / 58",
        val lapTime: String = "",
        val tyre: String = "" // eg. "s-soft"
)

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

enum class TransmissionSession {
    @SerializedName("Practice")
    PRACTICE,

    @SerializedName("Qualification")
    QUALIFICATION,

    @SerializedName("Race")
    RACE
}

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
