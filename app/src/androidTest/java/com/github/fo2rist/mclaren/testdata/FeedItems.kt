package com.github.fo2rist.mclaren.testdata

import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.models.FeedItem.SourceType
import com.github.fo2rist.mclaren.models.FeedItem.Type
import com.github.fo2rist.mclaren.models.ImageUrl
import com.github.fo2rist.mclaren.models.Size
import java.util.Date

object FeedItems {
    private val ID: Long = 1
    private val TITLE = "test title"
    private val CONTENT = "test content"
    private val SOURCE_NAME = "test source"
    private val MEDIA_LINK = "http://mclaren.com/formula1"
    private val GOOGLE_LOGO_IMAGE = ImageUrl.create(
            "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
            Size.valueOf(272, 92))
    private val IMAGES = listOf(GOOGLE_LOGO_IMAGE, GOOGLE_LOGO_IMAGE)

    @JvmField
    val GALLERY_ITEM = FeedItem(
            ID,
            Type.Gallery,
            TITLE,
            CONTENT,
            Date(),
            SourceType.Twitter,
            SOURCE_NAME,
            MEDIA_LINK,
            IMAGES)

    @JvmField
    val HTML_ARTICLE_ITEM = FeedItem(
            ID,
            Type.Article,
            "Some text",
            TestData.ARTICLE_HTML,
            Date(),
            FeedItem.SourceType.Unknown,
            SOURCE_NAME,
            MEDIA_LINK,
            IMAGES)
}
