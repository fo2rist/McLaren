package com.github.fo2rist.mclaren.models

/**
 * Single message of life transmission.
 */
data class TransmissionInfo(
    val raceId: Long,
    val raceName: String,
    val messages: List<TransmissionItem>
)
