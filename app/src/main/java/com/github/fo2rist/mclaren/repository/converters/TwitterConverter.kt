package com.github.fo2rist.mclaren.repository.converters

import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.models.ImageUrl
import com.github.fo2rist.mclaren.models.Size
import twitter4j.ResponseList
import twitter4j.Status
import javax.inject.Inject

const val MEDIA_TYPE_VIDEO = "video"
const val MEDIA_TYPE_PHOTO = "photo"
const val MEDIA_TYPE_GIF = "animated_gif"

/**
 * Converts response from [twitter4j.Twitter] into app models.
 * TODO list
 * - remove tweet's own URL at the end
 * - support retweets of quotes
 * - handle display URLs that aren't complete (e.g retweets of quotes can be trimmed)
 * - wrap URLs into proper HTML
 * - support two text for quoted tweets
 */
class TwitterConverter @Inject constructor() : FeedConverter<ResponseList<Status>> {
    override fun convertFeed(feedWebModel: ResponseList<Status>): List<FeedItem> {
        return feedWebModel
                .filter { it.inReplyToStatusId == -1L }
                .map { tweet ->
                    val originalTweet = tweet.fetchOriginalTweet()

                    FeedItem(
                            tweet.id,
                            originalTweet.fetchType(),
                            originalTweet.fetchText(),
                            null,
                            tweet.createdAt,
                            FeedItem.SourceType.Twitter,
                            originalTweet.user.screenName,
                            fetchVideoUrl(originalTweet, originalTweet.fetchType()),
                            fetchImageUrls(originalTweet)
                    )
                }
    }

    private fun Status.fetchOriginalTweet(): Status {
        return this.quotedStatus
                ?: this.retweetedStatus
                ?: this
    }

    private fun Status.fetchType(): FeedItem.Type {
        return when {
            this.mediaEntities.size == 0 ->
                FeedItem.Type.Message
            this.mediaEntities.size == 1 && this.mediaEntities[0].type == MEDIA_TYPE_VIDEO ->
                FeedItem.Type.Video
            this.mediaEntities.size == 1 ->
                FeedItem.Type.Image
            else ->
                FeedItem.Type.Gallery
        }
    }

    private fun Status.fetchText(): String {
        //Replace all URL with display versions in text
        return this.urlEntities.fold(this.text) { text, url ->
            text.replace(url.url, url.displayURL)
        }
    }

    private fun fetchVideoUrl(
        primaryTweet: Status,
        type: FeedItem.Type
    ): String {
        if (type != FeedItem.Type.Video)
            return ""

        return primaryTweet.mediaEntities[0].videoVariants.last { it.contentType == "video/mp4" }.url
    }

    private fun fetchImageUrls(
        primaryTweet: Status
    ): List<ImageUrl> {
        return primaryTweet.mediaEntities.map { entity ->
            ImageUrl.create(entity.mediaURL,
                    Size.valueOf(
                            entity.sizes.maxBy { it.key }!!.value.width,
                            entity.sizes.maxBy { it.key }!!.value.height))
        }
    }
}
