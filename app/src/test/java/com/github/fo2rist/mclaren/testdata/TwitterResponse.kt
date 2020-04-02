package com.github.fo2rist.mclaren.testdata

import com.github.fo2rist.mclaren.repository.converters.MEDIA_TYPE_PHOTO
import com.github.fo2rist.mclaren.repository.converters.MEDIA_TYPE_VIDEO
import com.github.fo2rist.mclaren.testutilities.fakes.FakeTwitterMediaEntity
import com.github.fo2rist.mclaren.testutilities.fakes.FakeTwitterStatus

const val PLAIN_TWEET_ID = 100L
const val PLAIN_TWEET_TEXT = "Plain tweet text"

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
