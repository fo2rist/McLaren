package com.github.fo2rist.mclaren.web.models

import com.google.gson.annotations.SerializedName
import java.util.ArrayList
import java.util.Date

/**
 * Represent full feed as it returned by StoryStream API.
 * @see StoryStream
 *
 * @see StoryStreamWrappingItem
 */
data class StoryStream(

    @SerializedName("date")
    var date: Date? = null,

    @SerializedName("blocks")
    var items: ArrayList<StoryStreamWrappingItem>? = null,

    @SerializedName("total_pages")
    var totalPages: Int = 0,

    @SerializedName("meta")
    var meta: Any? = null

)
