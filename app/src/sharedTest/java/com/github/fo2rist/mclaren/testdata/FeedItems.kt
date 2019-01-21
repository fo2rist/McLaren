package com.github.fo2rist.mclaren.testdata

import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.models.FeedItem.SourceType
import com.github.fo2rist.mclaren.models.FeedItem.Type
import com.github.fo2rist.mclaren.models.ImageUrl
import com.github.fo2rist.mclaren.models.Size
import java.util.*

object FeedItems {
    const val MEDIA_LINK = "http://mclaren.com/formula1"

    private const val ID: Long = 1
    private const val TITLE = "test title"
    private const val CONTENT = "test content"
    private const val SOURCE_NAME_TEST = "test source"
    private const val SOURCE_NAME_MCLAREN = "mclaren.com"
    private val EMPTY_IMAGES = emptyList<ImageUrl>()

    private val GOOGLE_LOGO_IMAGE = ImageUrl.create(
            "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
            Size.valueOf(272, 92))
    private val NON_EMPTY_IMAGES = listOf<ImageUrl>(GOOGLE_LOGO_IMAGE, GOOGLE_LOGO_IMAGE)

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
            EMPTY_IMAGES)

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
            EMPTY_IMAGES)

    @JvmField
    val ARTICLE_ITEM_WITH_LINKS = FeedItem(
            ID,
            Type.Article,
            TITLE,
            CONTENT_ARTICLE_HTML_WITH_LINK,
            Date(),
            SourceType.Unknown,
            SOURCE_NAME_MCLAREN,
            MEDIA_LINK,
            NON_EMPTY_IMAGES)

    @JvmField
    val ARTICLE_ITEM_WITH_TABLES = ARTICLE_ITEM_WITH_LINKS.copy(content = CONTENT_ARTICLE_HTML_WITH_TABLES)

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
