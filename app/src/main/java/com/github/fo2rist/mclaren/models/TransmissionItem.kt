package com.github.fo2rist.mclaren.models

import org.joda.time.DateTime

data class TransmissionItem(
        val id: Long,
        val dateTime: DateTime,
        val driverName: String?,
        val message: String,
        val session: Session,
        val type: Type
) {
    enum class Session {
        UNKNOWN,
        PRACTICE,
        QUALIFICATION,
        RACE
    }

    enum class Type {
        DRIVER_A_TO_PIT,
        DRIVER_B_TO_PIT,
        PIT_TO_DRIVER_A,
        PIT_TO_DRIVER_B,
        PIT_TO_EVERYONE,
        MESSAGE_ABOUT_DRIVER_A,
        MESSAGE_ABOUT_DRIVER_B,
        MESSAGE_GENERAL
    }
}
