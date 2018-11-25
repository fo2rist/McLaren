package com.github.fo2rist.mclaren.models

import java.util.Date

/**
 * Grad Prix Event (date and place).
 */
data class Event (
    var track: String, // e.g "Shanghai International Circuit"
    var date: Date // e.g "2017-04-07"
)
