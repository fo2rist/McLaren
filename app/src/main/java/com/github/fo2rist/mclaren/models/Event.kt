package com.github.fo2rist.mclaren.models

import java.util.Date

/**
 * Grad Prix Event (date and place).
 */
data class Event (
    var circuit_id: String, // e.g "china_shanghai"
    var date: Date // e.g "2017-04-07"
)
