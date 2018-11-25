package com.github.fo2rist.mclaren.models

import com.google.gson.annotations.SerializedName

/**
 * Race Circuit's (Race Track) info model.
 */
data class Circuit(
    var id: String, // e.g "austria"
    var country: String, // e.g "AUS"
    var track: String, // e.g "Melbourne Grand Prix Circuit"
    var city: String, // e.g "Melbourne"
    var name: String, // e.g "Australian Grand Prix"
    var laps: Int,
    var length: Double,
    var distance: Double,
    var seasons: String, // e.g "2009–2016",
    // serializable name is acceptable here because json is shipped with code and not coming from the outside
    // the class is not a networking or DB data transfer object
    @SerializedName("gp_held")
    var gpHeld: Int,
    @SerializedName("wiki_link")
    var wikiLink: String
)
