package com.github.fo2rist.mclaren.models

import com.github.fo2rist.mclaren.models.FeedItem.Companion
import java.io.Serializable
import java.util.Date

/**
 * Represent single item in the feed.
 * @constructor Creates instance but doesn't create deep copy of the [imageUrls], use [Companion.invoke] instead.
 */
data class FeedItem internal constructor(
    var id: Long,
    val type: Type,
    val text: String,
    val content: String?,
    val dateTime: Date,
    val sourceType: SourceType,
    val sourceName: String,
    /** Non displayable link that may be found in source data.  */
    val embeddedMediaLink: String,
    val imageUrls: List<ImageUrl>
) : Serializable, Comparable<FeedItem> {

    /**
     * Get single image. Should be null for text posts.
     * @return the first image if there are many or null
     */
    val imageUrl: ImageUrl? = imageUrls.firstOrNull()

    override fun compareTo(other: FeedItem): Int {
        return java.lang.Long.compare(this.id, other.id)
    }

    companion object {
        /**
         * Suggested limit for text length.
         * Not enforced by constructor itself.
         */
        const val TEXT_LENGTH_LIMIT: Int = 280

        /**
         * Creates instance in a safe way so urls list is not shared with called.
         */
        @SuppressWarnings("SpreadOperator", "LongParameterList")
        operator fun invoke(
            id: Long, type: Type, text: String, content: String?, dateTime: Date,
            sourceType: SourceType, sourceName: String, embeddedMediaLink: String, vararg imageUrls: ImageUrl
        ) = FeedItem(id, type, text, content, dateTime, sourceType, sourceName, embeddedMediaLink,
                listOf(*imageUrls))
    }

    /** Item's primary content type. */
    enum class Type {
        Image,
        Gallery,
        Video,
        Message,
        Article,
    }

    /** Social network if any - the source of the item. */
    enum class SourceType {
        Twitter,
        Instagram,
        Unknown,
    }
}
