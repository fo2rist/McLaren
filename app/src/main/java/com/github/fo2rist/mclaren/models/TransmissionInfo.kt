package com.github.fo2rist.mclaren.models

import com.github.fo2rist.mclaren.models.TransmissionItem.Session

/**
 * Single message of life transmission.
 */
data class TransmissionInfo(
    val messages: List<TransmissionItem>
) {

    val currentSession: Session = messages.firstOrNull()?.session ?: Session.UNKNOWN
}
