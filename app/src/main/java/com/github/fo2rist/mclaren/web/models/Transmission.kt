package com.github.fo2rist.mclaren.web.models

import java.util.*

data class Transmission(
        val id: Long,
        val name: String, // eg "Australian Grand Prix"
        val telemetries: Telemetry,
        val commentaries: List<TransmissionItem>
)

data class Telemetry(
        val driverA: Driver,
        val driverB: Driver
)

data class Driver(
        val driverName: String,
        val lap: String, // eg. "58 / 58",
        val lapTime: String,
        val tyre: String // eg. "s-soft"
)

data class TransmissionItem(
        val id: Long,
        val sourceId: Long,
        val date: Date, // eg. "2018-03-23T00:47:08+00:00",
        val driverName: String?,
        val grandPrixId: Long,
        val initials: String,
        val message: String,
        val session: String?, // null or "Practice", "Qualification", "Race"
        val type: String // "ALY" "ATP" "BTP" "PTA" "PTB" "PIT" "COM" "CAA" "CAB"
)
