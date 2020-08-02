package com.github.fo2rist.mclaren.models

import org.joda.time.DateTime

/**
 * Single message of life transmission.
 */
data class TransmissionItem(
    val id: Long,
    val dateTime: DateTime,
    /** Name of the driver or the guest speaker if it's communication with driver or guest. */
    val name: String?,
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
        DRIVER_A_TO_PIT,
        DRIVER_B_TO_PIT,
        PIT_TO_DRIVER_A,
        PIT_TO_DRIVER_B,
        MESSAGE_GENERAL,
        MESSAGE_FROM_GUEST,
    }
}
