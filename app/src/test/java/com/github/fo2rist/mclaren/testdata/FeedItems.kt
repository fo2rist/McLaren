package com.github.fo2rist.mclaren.testdata

import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.models.FeedItem.SourceType
import com.github.fo2rist.mclaren.models.FeedItem.Type
import com.github.fo2rist.mclaren.models.ImageUrl
import java.util.Date

object FeedItems {
    private val ID: Long = 1
    private val TITLE = "test title"
    private val CONTENT = "test content"
    private val SOURCE_NAME_TEST = "test source"
    private val SOURCE_NAME_MCLAREN = "mclaren.com"
    private val IMAGES = emptyList<ImageUrl>()
    @JvmField
    val MEDIA_LINK = "http://mclaren.com/formula1"

    @JvmField
    val TWITTER_GALLERY_ITEM = FeedItem(
            ID,
            Type.Gallery,
            TITLE,
            CONTENT,
            Date(),
            SourceType.Twitter,
            SOURCE_NAME_TEST,
            MEDIA_LINK,
            IMAGES)

    @JvmField
    val INSTAGRAM_GALLERY_ITEM = FeedItem(
            ID,
            Type.Gallery,
            TITLE,
            CONTENT,
            Date(),
            SourceType.Instagram,
            SOURCE_NAME_TEST,
            MEDIA_LINK,
            IMAGES)

    @JvmField
    val MCLAREN_ARTICLE_ITEM = FeedItem(
            ID,
            Type.Article,
            TITLE,
            CONTENT,
            Date(),
            SourceType.Unknown,
            SOURCE_NAME_MCLAREN,
            MEDIA_LINK)

    @JvmField
    val VIDEO_ITEM = FeedItem(
            ID,
            Type.Video,
            TITLE,
            CONTENT,
            Date(),
            SourceType.Twitter,
            SOURCE_NAME_MCLAREN,
            MEDIA_LINK)
}
