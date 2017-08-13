package com.github.fo2rist.mclaren.web.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(JUnit4.class)
public class FeedParserTest {
    @Test
    public void testEmptyResponseHandledWithoutErrors() throws Exception {
        McLarenFeed mcLarenFeedItems = ResponseParser.parseFeed("");

        assertTrue(mcLarenFeedItems.isEmpty());
    }

    @Test
    public void testEmptyJsonHandledWithoutErrors() throws Exception {
        McLarenFeed mcLarenFeedItems = ResponseParser.parseFeed("[]");

        assertTrue(mcLarenFeedItems.isEmpty());
    }

    @Test
    public void testIncorrectJsonHandledWithoutErrors() throws Exception {
        McLarenFeed mcLarenFeedItems = ResponseParser.parseFeed("{}"); //should be array

        assertTrue(mcLarenFeedItems.isEmpty());
    }

    @Test
    public void testReadDataParsedWithoutErrors() throws Exception {
        McLarenFeed mcLarenFeedItems = ResponseParser.parseFeed(TestData.REAL_FEED_RESPONSE);

        assertEquals(TestData.REAL_FEED_SIZE, mcLarenFeedItems.size());
    }
}
