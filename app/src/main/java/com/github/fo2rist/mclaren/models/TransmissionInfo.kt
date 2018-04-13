package com.github.fo2rist.mclaren.models

data class TransmissionInfo(
        val raceId: Long,
        val raceName: String,
        val messages: List<TransmissionItem>
)
