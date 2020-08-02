package com.github.fo2rist.mclaren.models

import org.joda.time.DateTime

/**
 * Single message of life transmission.
 */
data class TransmissionItem(
    val id: Long,
    val dateTime: DateTime,
    /** Name of guest with guest [Type.MESSAGE_FROM_GUEST], empty otherwise. */
    val guestName: String,
    val message: String,
    val session: Session,
    val type: Type
) {
    /**
     * Race weekend session.
     */
    enum class Session {
        UNKNOWN,
        PRACTICE_1,
        PRACTICE_2,
        PRACTICE_3,
        QUALIFICATION,
        RACE
    }

    /**
     * Type (direction) of transmissions message.
     */
    enum class Type {
        SAINZ_TO_PIT,
        NORRIS_TO_PIT,
        PIT_TO_SAINZ,
        PIT_TO_NORRIS,
        MESSAGE_GENERAL,
        MESSAGE_FROM_GUEST,
    }
}
