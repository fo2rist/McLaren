package com.github.fo2rist.mclaren.repository.converters

import com.github.fo2rist.mclaren.models.FeedItem

/**
 * Convert web-model to app Feed model.
 * @param I type of the input feed model.
 */
interface FeedConverter<I> {
    /** Create list of all parsed items from the web data model. */
    fun convertFeed(feedWebModel: I): List<FeedItem>
}
