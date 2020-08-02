package com.github.fo2rist.mclaren.models

/**
 * Single message of life transmission.
 */
data class TransmissionInfo(
    val messages: List<TransmissionItem>
) {

    val currentSession: TransmissionItem.Session = messages.first().session
}
