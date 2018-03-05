package com.github.fo2rist.mclaren.web.models;

import com.github.fo2rist.mclaren.testdata.McLarenFeedResponse;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

@RunWith(JUnit4.class)
public class McLarenFeedParserTest {
    private McLarenFeedResponseParser parser = new McLarenFeedResponseParser();

    @Test
    public void testReadDataParsedWithoutErrors() throws Exception {
        McLarenFeed mcLarenFeedItems = parser.parse(McLarenFeedResponse.REAL_FEED_RESPONSE);

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
        assertEquals(DateTime.parse("2017-08-12T10:34:06Z").toDate(), item.publicationDate);
    }
}
