package com.github.fo2rist.mclaren.models

import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * Grad Prix Event (date and place).
 * !Used as model for storage!
 */
data class Event(
    @SerializedName("circuit_id")
    var circuit_id: String, // e.g "china_shanghai"

    @SerializedName("practice_1_time")
    var practice1Time: String?, // e.g. "2017-04-07T15:00:00Z"

    @SerializedName("practice_2_time")
    var practice2Time: String?, // e.g. "2020-10-23T21:00:00Z",

    @SerializedName("practice_3_time")
    var practice3Time: String?, // e.g. "2020-10-24T19:00:00Z",

    @SerializedName("qualifying_time")
    var qualifyingTime: String?, // e.g. "2020-10-24T22:00:00Z",

    @SerializedName("race_time")
    var raceTime: String?, // e.g. "2020-10-25T19:10:00Z",

    @SerializedName("date")
    var date: Date? // e.g "2017-04-07"
)
