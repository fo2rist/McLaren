package com.github.fo2rist.mclaren.repository.converters

import android.text.Html
import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.models.FeedItem.Companion.TEXT_LENGTH_LIMIT
import com.github.fo2rist.mclaren.models.FeedItem.SourceType
import com.github.fo2rist.mclaren.models.FeedItem.Type
import com.github.fo2rist.mclaren.models.ImageUrl
import com.github.fo2rist.mclaren.models.Size
import com.github.fo2rist.mclaren.web.models.StoryStream
import com.github.fo2rist.mclaren.web.models.StoryStreamItem
import com.github.fo2rist.mclaren.web.models.StoryStreamItem.ContentType
import com.github.fo2rist.mclaren.web.models.StoryStreamItem.FeedType
import com.github.fo2rist.mclaren.web.models.StoryStreamItem.ImageData
import com.github.fo2rist.mclaren.web.models.StoryStreamItem.ImageSize
import com.github.fo2rist.mclaren.web.models.StoryStreamWrappingItem
import java.util.Date
import java.util.Locale

private const val HTTP = "http"

/**
 * Converts StoryStream API feed web-model to app models.
 */
object StoryStreamConverter : FeedConverter<StoryStream> {

    override fun convertFeed(feedWebModel: StoryStream): List<FeedItem> {
        return feedWebModel.items?.mapNotNull {
            convertFeedItem(it)
        } ?: emptyList()
    }

    private fun convertFeedItem(storyStreamWrappingItem: StoryStreamWrappingItem): FeedItem? {
        val contentItem = fetchContentItem(storyStreamWrappingItem)
                ?: return null

        return FeedItem(
                fetchId(contentItem),
                fetchType(contentItem),
                fetchText(contentItem),
                fetchContent(contentItem),
                fetchDate(contentItem),
                fetchSourceType(contentItem),
                fetchSourceName(contentItem),
                fetchMediaLink(contentItem),
                fetchImageUrls(contentItem))
    }

    private fun fetchContentItem(storyStreamItem: StoryStreamWrappingItem): StoryStreamItem? {
        return storyStreamItem.contentItems?.firstOrNull()
    }

    private fun fetchId(storyStreamItem: StoryStreamItem): Long {
        //StoryStream don't offer sequential IDs so use timestamp instead as a hack
        val publishDate = storyStreamItem.publishDate
        return publishDate?.time
                ?: 0 // TODO default values makes no sense need. Check if this kind of item can be displayed. 2019-07-31
    }

    private fun fetchType(storyStreamItem: StoryStreamItem): Type {
        if (storyStreamItem.feedType == FeedType.Custom) {
            return Type.Article
        }

        return when {
            storyStreamItem.contentType == ContentType.Text ->
                Type.Message
            storyStreamItem.contentType == ContentType.Video ->
                Type.Video
            storyStreamItem.contentType == ContentType.Image && storyStreamItem.images.len == 1 ->
                Type.Image
            storyStreamItem.contentType == ContentType.Image && storyStreamItem.images.len > 1 ->
                Type.Gallery
            else ->
                Type.Message
        }
    }

    private val List<*>?.len: Int
        get() = this?.size ?: 0

    @Suppress("DEPRECATION") //Html.fromHtml is deprecated but new version is only available on SDK 24
    private fun fetchText(storyStreamItem: StoryStreamItem): String {
        val rawBody = storyStreamItem.body
                ?: return ""

        if (storyStreamItem.feedType == FeedType.Custom) {
            val textBody = Html.fromHtml(rawBody)
                    .toString()
                    .replace("\\n+".toRegex(), " ")

            val textTitle = storyStreamItem.title
            val text = if (textTitle.isNullOrEmpty()) {
                textBody
            } else {
                textTitle.toUpperCase(Locale.US) + "\n" + textBody
            }

            return text.trimmed()
        } else {
            //TODO We are loosing links that way. HTML should be preserved and then handled on the UI side. 2018.03.09
            return Html.fromHtml(rawBody).toString()
        }
    }

    private fun String.trimmed(): String {
        return if (this.length > TEXT_LENGTH_LIMIT) {
            this.substring(0, TEXT_LENGTH_LIMIT) + "..."
        } else {
            this
        }
    }

    private fun fetchContent(storyStreamItem: StoryStreamItem): String? {
        return storyStreamItem.body
    }

    private fun fetchDate(storyStreamItem: StoryStreamItem): Date {
        return storyStreamItem.publishDate
                ?: Date() //TODO default value makes no sense. Check if it can be used anyhow. 2019-07-31
    }

    private fun fetchSourceType(storyStreamItem: StoryStreamItem): SourceType {
        return when (storyStreamItem.feedType) {
            FeedType.Twitter -> SourceType.Twitter
            FeedType.Instagram -> SourceType.Instagram
            else -> SourceType.Unknown
        }
    }

    private fun fetchSourceName(storyStreamItem: StoryStreamItem): String {
        //for pretty name use .author not .source
        return storyStreamItem.source
                ?: ""
    }

    private fun fetchMediaLink(storyStreamItem: StoryStreamItem): String {
        return storyStreamItem.videos?.firstOrNull()?.url
                ?: ""
    }

    private fun fetchImageUrls(storyStreamItem: StoryStreamItem): List<ImageUrl> {
        val images = storyStreamItem.images
                ?: return emptyList()

        return images.map {
            fetchUrlFromImageData(it)
        }
    }

    private fun fetchUrlFromImageData(imageData: ImageData): ImageUrl {
        val originalSizeUrl = fixUrl(imageData.originalSizeUrl)
        val originalSize = imageData.sizes?.originalSize.toImageSize()

        if (originalSizeUrl.isNotEmpty()) {
            //Here the original link is present additional links are optional

            val twoUpSizeUrl = fixUrl(imageData.twoUpSizeUrl)
            val threeUpSizeUrl = fixUrl(imageData.threeUpSizeUrl)

            val twoUpSize = imageData.sizes?.twoUpSize.toImageSize()
            val threeUpSize = imageData.sizes?.threeUpSize.toImageSize()

            return buildImageUrlFromMultipleUrls(
                    originalSizeUrl, twoUpSizeUrl, threeUpSizeUrl,
                    originalSize, twoUpSize, threeUpSize)
        } else {
            //Here original link is absent
            //when links are broken the name usually contains the link
            return buildImageUrlFromName(imageData.name, originalSize)
        }
    }

    @Suppress("Detekt.LongParameterList")
    private fun buildImageUrlFromMultipleUrls(
        originalSizeUrl: String, twoUpSizeUrl: String, threeUpSizeUrl: String,
        originalSize: Size, twoUpSize: Size, threeUpSize: Size
    ): ImageUrl {
        return if (originalSizeUrl == twoUpSizeUrl && originalSizeUrl == threeUpSizeUrl) {
            //Broken links are usually equal, so we should ignore small size links as incorrect
            ImageUrl.create(originalSizeUrl, originalSize)
        } else if (twoUpSizeUrl.isEmpty() && threeUpSizeUrl.isEmpty()) {
            //We only have one link of three, create a single URL
            ImageUrl.create(originalSizeUrl, originalSize)
        } else {
            //Normal case - all three links are different and not empty
            ImageUrl.create(originalSizeUrl, originalSize,
                    twoUpSizeUrl, twoUpSize,
                    threeUpSizeUrl, threeUpSize)
        }
    }

    private fun buildImageUrlFromName(imageName: String?, originalSize: Size): ImageUrl {
        return if (imageName != null && imageName.startsWith(HTTP)) {
            ImageUrl.create(imageName, originalSize)
        } else {
            //if it doesn't there is nothing we can do
            ImageUrl.empty()
        }
    }

    /**
     * Strip URL from non-http prefix.
     * Sometimes API return weird prefix before the actual URL, which should be stripped.
     * @return empty string if input is null, empty, or doesn't contain URL
     */
    private fun fixUrl(url: String?): String {
        return when {
            url == null -> ""
            !url.startsWith(HTTP) && url.contains(HTTP) -> url.replaceFirst("^.*\\/http?".toRegex(), HTTP)
            else -> url
        }
    }

    private fun ImageSize?.toImageSize(): Size {
        return if (this == null) {
            Size.UNKNOWN
        } else {
            Size.valueOf(width, height)
        }
    }
}
