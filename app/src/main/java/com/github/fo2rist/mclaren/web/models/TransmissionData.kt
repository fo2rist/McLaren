package com.github.fo2rist.mclaren.web.models

import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * Model for whole race life transmission (team communications).
 */
class TransmissionData : HashMap<TransmissionSession, List<TransmissionItemData>>()

/**
 * Item (message) in [TransmissionData].
 *
 * Response example:
 *    "published_time": "2020-07-18T14:05:21Z",
 *    "source": "cm",
 *    "guest_name": "",
 *    "commentary": "Long text here",
 *    "sticky": false,
 *    "sticky_until": null
 */
data class TransmissionItemData(
    @SerializedName("published_time")
    val date: Date, // eg. "2018-03-23T00:47:08Z",
    @SerializedName("source")
    val source: TransmissionMessageType,
    @SerializedName("commentary")
    val message: String,
    @SerializedName("guest_name")
    val guestName: String
)

/**
 * Type of race session transmission belongs to.
 */
enum class TransmissionSession {
    @SerializedName("P1")
    PRACTICE_1,

    @SerializedName("P2")
    PRACTICE_2,

    @SerializedName("P3")
    PRACTICE_3,

    @SerializedName("Q")
    QUALIFICATION,

    @SerializedName("R")
    RACE
}

/**
 * Type of message (sender and receiver) in transmission.
 */
enum class TransmissionMessageType {
    @SerializedName("d_RIC")
    RIC_TO_PIT, // Racer A to pit

    @SerializedName("d_NOR")
    NOR_TO_PIT, // Racer B to pit

    @SerializedName("p_RIC")
    PIT_TO_RIC, // pit to racer A

    @SerializedName("p_NOR")
    PIT_TO_NOR, // pit to racer B

    @SerializedName("cm")
    COMMENTARY, // commentary

    @SerializedName("gu")
    GUEST, // guest message
}
