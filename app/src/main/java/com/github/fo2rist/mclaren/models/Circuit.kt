package com.github.fo2rist.mclaren.models

import com.google.gson.annotations.SerializedName

/**
 * Race Circuit's (Race Track) info model.
 * !Used as model for storage!
 */
data class Circuit(
    var id: String, // e.g "austria"
    var country: String, // e.g "AUS"
    var track: String, // e.g "Melbourne Grand Prix Circuit"
    var city: String, // e.g "Melbourne"
    /** Default grand prix name for a circuit. */
    @SerializedName("name")
    var gpName: String, // e.g "Australian Grand Prix"
    var laps: Int,
    var length: Double,
    var distance: Double,
    var seasons: String, // e.g "2009–2016",
    @SerializedName("gp_held")
    var gpHeld: Int,
    @SerializedName("wiki_link")
    var wikiLink: String
)
