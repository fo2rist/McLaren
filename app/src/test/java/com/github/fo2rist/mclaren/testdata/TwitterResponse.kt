package com.github.fo2rist.mclaren.testdata

import com.github.fo2rist.mclaren.repository.converters.MEDIA_TYPE_PHOTO
import com.github.fo2rist.mclaren.repository.converters.MEDIA_TYPE_VIDEO
import com.github.fo2rist.mclaren.testutilities.fakes.FakeTwitterMediaEntity
import com.github.fo2rist.mclaren.testutilities.fakes.FakeTwitterStatus
import com.github.fo2rist.mclaren.testutilities.fakes.FakeTwitterUrlEntity

const val PLAIN_TWEET_ID = 100L
const val PLAIN_TWEET_TEXT = "Plain tweet text"
const val SHORT_LINK_URL = "https://t.co/abcde123"
const val DISPLAY_LINK_URL = "https://example.display.link"
const val PLAIN_TWEET_TEXT_WITH_LINK = "$PLAIN_TWEET_TEXT $SHORT_LINK_URL"

val PLAIN_TWEET = FakeTwitterStatus(PLAIN_TWEET_ID, _text = PLAIN_TWEET_TEXT)

val REPLY_TWEET = FakeTwitterStatus(_inReplyToStatusId = 1)

val RETWEET_OF_PLAIN_TWEET = FakeTwitterStatus(1, _text = "Retweet text", _retweetedStatus = PLAIN_TWEET)

val QUOTE_OF_PLAIN_TWEET = FakeTwitterStatus(2, _text = "Quote text", _quotedStatus = PLAIN_TWEET)

val ONE_IMAGE_TWEET = FakeTwitterStatus(10, _mediaEntities = arrayOf(FakeTwitterMediaEntity(_type = MEDIA_TYPE_PHOTO)))

val ONE_VIDEO_TWEET = FakeTwitterStatus(11, _mediaEntities = arrayOf(FakeTwitterMediaEntity(_type = MEDIA_TYPE_VIDEO)))

val TWO_IMAGES_TWEET = FakeTwitterStatus(12, _mediaEntities = arrayOf(
        FakeTwitterMediaEntity(_type = MEDIA_TYPE_PHOTO),
        FakeTwitterMediaEntity(_type = MEDIA_TYPE_PHOTO)
))

val PLAIN_TWEET_WITH_TRAILING_SOURCE_LINK = FakeTwitterStatus(13, _text = PLAIN_TWEET_TEXT_WITH_LINK)

val PLAIN_TWEET_WITH_KNOWN_LINK = FakeTwitterStatus(14, _text = PLAIN_TWEET_TEXT_WITH_LINK,
        _urlEntities = arrayOf(FakeTwitterUrlEntity(SHORT_LINK_URL, DISPLAY_LINK_URL)))

val LINK_TWEET_WITH_EXTENDED_LINK = FakeTwitterStatus(15, SHORT_LINK_URL,
        _urlEntities = arrayOf(FakeTwitterUrlEntity(SHORT_LINK_URL, DISPLAY_LINK_URL)))

val LINK_TWEET_WITH_EMPTY_EXTENDED_LINK = FakeTwitterStatus(16, SHORT_LINK_URL,
        _urlEntities = arrayOf(FakeTwitterUrlEntity(SHORT_LINK_URL, null)))

val LINK_TWEET_WITH_SHORTENED_EXTENDED_LINK = FakeTwitterStatus(17, SHORT_LINK_URL,
        _urlEntities = arrayOf(FakeTwitterUrlEntity(SHORT_LINK_URL, DISPLAY_LINK_URL + "...")))

