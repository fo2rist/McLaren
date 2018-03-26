package com.github.fo2rist.mclaren.models;

import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(JUnit4.class)
public class FeedItemTest {
    private static final long ID = 101;
    private static final FeedItem.Type TYPE_1 = FeedItem.Type.Image;
    private static final FeedItem.Type TYPE_2 = FeedItem.Type.Article;
    private static final String TEXT = "text";
    private static final String CONTENT = "content";
    private static final Date DATE = new Date();
    private static final FeedItem.SourceType SOURCE_1 = FeedItem.SourceType.Instagram;
    private static final FeedItem.SourceType SOURCE_2 = FeedItem.SourceType.Twitter;
    private static final String SOURCE_NAME = "source";
    private static final String MEDIA_LINK = "http://url_m.co";
    private static final ImageUrl IMAGE_URL_1 = ImageUrl.create("http://url1.co", Size.UNKNOWN);
    private static final ImageUrl IMAGE_URL_2 = ImageUrl.create("http://url2.co", Size.UNKNOWN);
    private static final ImageUrl IMAGE_URL_3 = ImageUrl.create("http://url3.co", Size.UNKNOWN);

    private FeedItem referenceItem;

    @Before
    public void setUp() throws Exception {
        referenceItem = new FeedItem(ID, TYPE_1, TEXT, CONTENT, DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_2);
    }

    @Test
    public void testEqualObjectAreEqual() throws Exception {
        FeedItem feedItem2 = new FeedItem(ID, TYPE_1, TEXT, CONTENT, DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_2);

        assertEquals(referenceItem, feedItem2);
        assertEquals(referenceItem.hashCode(), feedItem2.hashCode());
    }

    @Test
    public void testItemsWithDifferentIdNotEqual() throws Exception {
        FeedItem differentId = new FeedItem(ID + 1, TYPE_1, TEXT, CONTENT, DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_2);
        assertFeedItemsNotEquals(referenceItem, differentId);
    }

    @Test
    public void testItemsWithDifferentTypeNotEqual() throws Exception {
        FeedItem differentType = new FeedItem(ID, TYPE_2, TEXT, CONTENT, DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_2);
        assertFeedItemsNotEquals(referenceItem, differentType);
    }

    @Test
    public void testItemsWithDifferentTextNotEqual() throws Exception {
        FeedItem differentText = new FeedItem(ID, TYPE_1, TEXT + "!", CONTENT, DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_2);
        assertFeedItemsNotEquals(referenceItem, differentText);
    }

    @Test
    public void testItemsWithDifferentContentNotEqual() throws Exception {
        FeedItem different = new FeedItem(ID, TYPE_1, TEXT, CONTENT + "!", DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_2);
        assertFeedItemsNotEquals(referenceItem, different);
    }

    @Test
    public void testItemsWithDifferentDateNotEqual() throws Exception {
        FeedItem differentDate = new FeedItem(ID, TYPE_1, TEXT, CONTENT, new Date(DATE.getTime() + 1), SOURCE_1, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_2);
        assertFeedItemsNotEquals(referenceItem, differentDate);
    }

    @Test
    public void testItemsWithDifferentSourceTypeNotEqual() throws Exception {
        FeedItem differentSourceType = new FeedItem(ID, TYPE_1, TEXT, CONTENT, DATE, SOURCE_2, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_2);
        assertFeedItemsNotEquals(referenceItem, differentSourceType);
    }

    @Test
    public void testItemsWithDifferentSourceNameNotEqual() throws Exception {
        FeedItem differentSourceName = new FeedItem(ID, TYPE_1, TEXT, CONTENT, DATE, SOURCE_1, SOURCE_NAME + "!", MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_2);
        assertFeedItemsNotEquals(referenceItem, differentSourceName);
    }

    @Test
    public void testItemsWithDifferentMedialLinkNotEqual() throws Exception {
        FeedItem differentMediaLink = new FeedItem(ID, TYPE_1, TEXT, CONTENT, DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK + "m", IMAGE_URL_1, IMAGE_URL_2);
        assertFeedItemsNotEquals(referenceItem, differentMediaLink);
    }

    @Test
    public void testItemsWithDifferentImageUrlNotEqual() throws Exception {
        FeedItem differentImageUrl = new FeedItem(ID, TYPE_1, TEXT, CONTENT, DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_3);
        assertFeedItemsNotEquals(referenceItem, differentImageUrl);

        FeedItem differentImagesCount = new FeedItem(ID, TYPE_1, TEXT, CONTENT, DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_1, IMAGE_URL_2, IMAGE_URL_3);
        assertFeedItemsNotEquals(referenceItem, differentImagesCount);

        FeedItem differentImagesOrder = new FeedItem(ID, TYPE_1, TEXT, CONTENT, DATE, SOURCE_1, SOURCE_NAME, MEDIA_LINK, IMAGE_URL_2, IMAGE_URL_1);
        assertFeedItemsNotEquals(referenceItem, differentImagesOrder);
    }

    private void assertFeedItemsNotEquals(FeedItem firstItem, FeedItem secondItem) {
        assertNotEquals(firstItem, secondItem);
        assertNotEquals("Hashes should be different", firstItem.hashCode(), secondItem.hashCode());
    }
}
