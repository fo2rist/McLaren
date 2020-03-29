package com.github.fo2rist.mclaren.utils

import com.github.fo2rist.mclaren.models.FeedItem
import java.util.ArrayList
import java.util.TreeSet

/**
 * Convert [TreeSet] into descending list.
 */
fun TreeSet<FeedItem>.toDescendingList(): List<FeedItem> {
    return ArrayList(this.descendingSet())
}
