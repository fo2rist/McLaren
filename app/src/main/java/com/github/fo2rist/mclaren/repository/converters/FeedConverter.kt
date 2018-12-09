package com.github.fo2rist.mclaren.repository.converters

import com.github.fo2rist.mclaren.models.FeedItem

/**
 * Convert web-model to app Feed model.
 * @param T type of the resulting feed.
 */
interface FeedConverter<T> {
    /** Create list of all parsed items from the web data model. */
    fun convertFeed(feedWebModel: T): List<FeedItem>
}
