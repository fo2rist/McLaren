package com.github.fo2rist.mclaren.web.model;

import com.github.fo2rist.mclaren.testdata.McLarenFeedResponse;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
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
        McLarenFeed mcLarenFeedItems = ResponseParser.parseFeed(McLarenFeedResponse.REAL_FEED_RESPONSE);

        McLarenFeedItem item = mcLarenFeedItems.get(0);
        assertEquals(McLarenFeedResponse.REAL_FEED_SIZE, mcLarenFeedItems.size());
        assertEquals("@mclaren", item.author);
        assertNull(null, item.body);
        assertTrue(item.content.startsWith("The swimming pool"));
        assertEquals(McLarenFeedItem.Source.INSTAGRAM, item.source);
        assertEquals(McLarenFeedItem.Type.IMAGE, item.type);
        assertFalse(item.hidden);
        assertNotNull(item.tweetText);
        assertEquals(1, item.media.size());
        assertEquals(new Date(Date.UTC(2017-1900, 8-1, 12, 10, 34, 06)),
                item.publicationDate);
    }
}
