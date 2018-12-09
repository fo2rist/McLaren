package com.github.fo2rist.mclaren.repository.converters

import com.github.fo2rist.mclaren.models.FeedItem

/**
 * Convert web-model to app Feed model.
 */
interface FeedConverter<T> {
    fun convertFeed(feedWebModel: T): List<FeedItem>
}
