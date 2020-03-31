package com.github.fo2rist.mclaren.repository.converters

import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.models.ImageUrl
import com.github.fo2rist.mclaren.models.Size
import twitter4j.ResponseList
import twitter4j.Status
import javax.inject.Inject

/**
 * Converts response from [twitter4j.Twitter] into app models.
 */
class TwitterConverter @Inject constructor() : FeedConverter<ResponseList<Status>> {
    override fun convertFeed(feedWebModel: ResponseList<Status>): List<FeedItem> {
        return feedWebModel
                .filter { it.inReplyToStatusId == -1L }
                .map {
                    val primaryTweet = when {
                        it.quotedStatus != null -> it.quotedStatus
                        it.retweetedStatus != null -> it.retweetedStatus
                        else -> it
                    }

                    val type = when {
                        primaryTweet.mediaEntities.isEmpty() -> FeedItem.Type.Message
                        primaryTweet.mediaEntities.size == 1 && primaryTweet.mediaEntities[0]!!.type == "video" -> FeedItem.Type.Video
                        primaryTweet.mediaEntities.size == 1 -> FeedItem.Type.Image
                        else -> FeedItem.Type.Gallery
                    }

                    val text = primaryTweet.urlEntities.fold(primaryTweet.text) { text, url ->
                        text.replace(url.url, url.displayURL)
                    }

                    FeedItem(
                            it.id,
                            type,
                            text,
                            null,
                            it.createdAt,
                            FeedItem.SourceType.Twitter,
                            primaryTweet.user.screenName,
                            when (type) {
                                FeedItem.Type.Video -> primaryTweet.mediaEntities[0].videoVariants[1].url
                                else -> ""
                            },
                            primaryTweet.mediaEntities.map { url ->
                                ImageUrl.create(url.mediaURL,
                                        Size.valueOf(url.sizes.values.last().width, url.sizes.values.last().height))
                            }
                    )
                }
    }
}
