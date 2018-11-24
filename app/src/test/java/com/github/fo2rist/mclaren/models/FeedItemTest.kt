package com.github.fo2rist.mclaren.models

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.Date

@RunWith(JUnit4::class)
class FeedItemTest {

    private lateinit var referenceItem: FeedItem

    @Before
    fun setUp() {
        referenceItem = FeedItem(ID, TYPE_1, TEXT, CONTENT, DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_2)
    }

    @Test
    fun testImageUrlIsNullWhenNoUrlsProvided() {
        val noUrlFeedItem = FeedItem(ID, TYPE_1, TEXT, CONTENT, DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK)

        assertNull(noUrlFeedItem.imageUrl)
    }

    @Test
    fun testImageUrlIsFirstUrl() {
        assertEquals(IMAGE_URL_1, referenceItem.imageUrl)
    }

    @Test
    fun testEqualObjectsAreEqual() {
        val feedItem2 = FeedItem(ID, TYPE_1, TEXT, CONTENT, DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_2)

        assertEquals(referenceItem, feedItem2)
        assertEquals(referenceItem.hashCode().toLong(), feedItem2.hashCode().toLong())
    }

    @Test
    fun testItemsWithDifferentIdNotEqual() {
        val differentId = FeedItem(ID + 1, TYPE_1, TEXT, CONTENT, DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_2)
        assertFeedItemsNotEquals(referenceItem, differentId)
    }

    @Test
    fun testItemsWithDifferentTypeNotEqual() {
        val differentType = FeedItem(ID, TYPE_2, TEXT, CONTENT, DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_2)
        assertFeedItemsNotEquals(referenceItem, differentType)
    }

    @Test
    fun testItemsWithDifferentTextNotEqual() {
        val differentText = FeedItem(ID, TYPE_1, "$TEXT!", CONTENT, DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_2)
        assertFeedItemsNotEquals(referenceItem, differentText)
    }

    @Test
    fun testItemsWithDifferentContentNotEqual() {
        val different = FeedItem(ID, TYPE_1, TEXT, "$CONTENT!", DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_2)
        assertFeedItemsNotEquals(referenceItem, different)
    }

    @Test
    fun testItemsWithDifferentDateNotEqual() {
        val differentDate = FeedItem(ID, TYPE_1, TEXT, CONTENT, Date(DATE.time + 1), SOURCE_1, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_2)
        assertFeedItemsNotEquals(referenceItem, differentDate)
    }

    @Test
    fun testItemsWithDifferentSourceTypeNotEqual() {
        val differentSourceType = FeedItem(ID, TYPE_1, TEXT, CONTENT, DATE, SOURCE_2, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_2)
        assertFeedItemsNotEquals(referenceItem, differentSourceType)
    }

    @Test
    fun testItemsWithDifferentSourceNameNotEqual() {
        val differentSourceName = FeedItem(ID, TYPE_1, TEXT, CONTENT, DATE, SOURCE_1, "$SOURCE_NAME!", MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_2)
        assertFeedItemsNotEquals(referenceItem, differentSourceName)
    }

    @Test
    fun testItemsWithDifferentMedialLinkNotEqual() {
        val differentMediaLink = FeedItem(ID, TYPE_1, TEXT, CONTENT, DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK + "m", IMAGE_URL_1, IMAGE_URL_2)
        assertFeedItemsNotEquals(referenceItem, differentMediaLink)
    }

    @Test
    fun testItemsWithDifferentImageUrlNotEqual() {
        val differentImageUrl = FeedItem(ID, TYPE_1, TEXT, CONTENT, DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_3)
        assertFeedItemsNotEquals(referenceItem, differentImageUrl)

        val differentImagesCount = FeedItem(ID, TYPE_1, TEXT, CONTENT, DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_2, IMAGE_URL_3)
        assertFeedItemsNotEquals(referenceItem, differentImagesCount)

        val differentImagesOrder = FeedItem(ID, TYPE_1, TEXT, CONTENT, DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_2, IMAGE_URL_1)
        assertFeedItemsNotEquals(referenceItem, differentImagesOrder)
    }

    private fun assertFeedItemsNotEquals(firstItem: FeedItem, secondItem: FeedItem) {
        assertNotEquals(firstItem, secondItem)
        assertNotEquals("Hashes should be different", firstItem.hashCode().toLong(), secondItem.hashCode().toLong())
    }

    companion object {
        private val ID: Long = 101
        private val TYPE_1 = FeedItem.Type.Image
        private val TYPE_2 = FeedItem.Type.Article
        private val TEXT = "text"
        private val CONTENT = "content"
        private val DATE = Date()
        private val SOURCE_1 = FeedItem.SourceType.Instagram
        private val SOURCE_2 = FeedItem.SourceType.Twitter
        private val SOURCE_NAME = "source"
        private val MEDIA_LINK = "http://url_m.co"
        private val IMAGE_URL_1 = ImageUrl.create("http://url1.co", Size.UNKNOWN)
        private val IMAGE_URL_2 = ImageUrl.create("http://url2.co", Size.UNKNOWN)
        private val IMAGE_URL_3 = ImageUrl.create("http://url3.co", Size.UNKNOWN)
    }
}
