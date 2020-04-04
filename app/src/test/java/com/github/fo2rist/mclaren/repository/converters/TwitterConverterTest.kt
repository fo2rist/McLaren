package com.github.fo2rist.mclaren.repository.converters

import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.testdata.DISPLAY_LINK_URL
import com.github.fo2rist.mclaren.testdata.LINK_TWEET_WITH_EMPTY_EXTENDED_LINK
import com.github.fo2rist.mclaren.testdata.LINK_TWEET_WITH_EXTENDED_LINK
import com.github.fo2rist.mclaren.testdata.LINK_TWEET_WITH_SHORTENED_EXTENDED_LINK
import com.github.fo2rist.mclaren.testdata.ONE_IMAGE_TWEET
import com.github.fo2rist.mclaren.testdata.ONE_VIDEO_TWEET
import com.github.fo2rist.mclaren.testdata.PLAIN_TWEET
import com.github.fo2rist.mclaren.testdata.PLAIN_TWEET_TEXT
import com.github.fo2rist.mclaren.testdata.PLAIN_TWEET_WITH_KNOWN_LINK
import com.github.fo2rist.mclaren.testdata.PLAIN_TWEET_WITH_TRAILING_SOURCE_LINK
import com.github.fo2rist.mclaren.testdata.QUOTE_OF_PLAIN_TWEET
import com.github.fo2rist.mclaren.testutilities.fakes.FakeTwitterResponse
import com.github.fo2rist.mclaren.testdata.REPLY_TWEET
import com.github.fo2rist.mclaren.testdata.RETWEET_OF_PLAIN_TWEET
import com.github.fo2rist.mclaren.testdata.SHORT_LINK_URL
import com.github.fo2rist.mclaren.testdata.TWO_IMAGES_TWEET
import com.github.fo2rist.mclaren.testutilities.CustomAssertions.assertStartsWith
import com.github.fo2rist.mclaren.testutilities.fakes.MEDIA_URL_DEFAULT
import com.github.fo2rist.mclaren.testutilities.fakes.SIZE_LARGE
import org.junit.Test

import org.junit.Assert.*

class TwitterConverterTest {

    @Test
    fun `convertFeed filters out replies`() {
        val result = TwitterConverter().convertFeed(FakeTwitterResponse(
                REPLY_TWEET
        ))

        assertEquals(0, result.size)
    }

    @Test
    fun `convertFeed takes original tweet text from Quotes and Retweets`() {
        val result = TwitterConverter().convertFeed(FakeTwitterResponse(
                RETWEET_OF_PLAIN_TWEET,
                QUOTE_OF_PLAIN_TWEET
        ))

        assertEquals(PLAIN_TWEET_TEXT, result[0].text)
        assertEquals(PLAIN_TWEET_TEXT, result[1].text)
    }

    @Test
    fun `convertFeed removes trailing URL if it's not present in URL entities`() {
        val result = TwitterConverter().convertFeed(FakeTwitterResponse(
                PLAIN_TWEET_WITH_TRAILING_SOURCE_LINK,
                PLAIN_TWEET_WITH_KNOWN_LINK
        ))

        assertEquals(PLAIN_TWEET_TEXT, result[0].text)
        assertStartsWith(PLAIN_TWEET_TEXT, result[1].text)
        assertNotEquals(PLAIN_TWEET_TEXT, result[1].text)
    }

    @Test
    fun `convertFeed replaces short URLs with display versions if they aren't short`() {
        val result = TwitterConverter().convertFeed(FakeTwitterResponse(
                LINK_TWEET_WITH_EXTENDED_LINK,
                LINK_TWEET_WITH_EMPTY_EXTENDED_LINK,
                LINK_TWEET_WITH_SHORTENED_EXTENDED_LINK
        ))

        assertEquals(DISPLAY_LINK_URL, result[0].text)
        assertEquals(SHORT_LINK_URL, result[1].text)
        assertEquals(SHORT_LINK_URL, result[2].text)
    }

    @Test
    fun `convertFeed detects type as Message if no media found`() {
        val result = TwitterConverter().convertFeed(FakeTwitterResponse(
                PLAIN_TWEET
        ))

        assertEquals(FeedItem.Type.Message, result[0].type)
    }

    @Test
    fun `convertFeed detects type as Image if one image found`() {
        val result = TwitterConverter().convertFeed(FakeTwitterResponse(
                ONE_IMAGE_TWEET
        ))

        assertEquals(FeedItem.Type.Image, result[0].type)
    }

    @Test
    fun `convertFeed detects type as video if one video found`() {
        val result = TwitterConverter().convertFeed(FakeTwitterResponse(
                ONE_VIDEO_TWEET
        ))

        assertEquals(FeedItem.Type.Video, result[0].type)
    }

    @Test
    fun `convertFeed detects type as gallery if 1+ media item found`() {
        val result = TwitterConverter().convertFeed(FakeTwitterResponse(
                TWO_IMAGES_TWEET
        ))

        assertEquals(FeedItem.Type.Gallery, result[0].type)
    }

    @Test
    fun `convertFeed takes largest size for image as primary`() {
        val result = TwitterConverter().convertFeed(FakeTwitterResponse(
                ONE_IMAGE_TWEET
        ))
        assertEquals(SIZE_LARGE, result[0].imageUrls[0].size.height)
        assertEquals(SIZE_LARGE, result[0].imageUrls[0].size.width)
    }

    @Test
    fun `convertFeed takes largest mp4 version of video as primary`() {
        val result = TwitterConverter().convertFeed(FakeTwitterResponse(
                ONE_VIDEO_TWEET
        ))
        assertEquals(MEDIA_URL_DEFAULT, result[0].embeddedMediaLink)
    }
}
