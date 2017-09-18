package com.github.fo2rist.mclaren.web.model;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.testdata.McLarenFeedResponse;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class FeedConverterTest {
    @Test
    public void testRealDataFilteredByHiddenFlag() throws Exception {
        List<FeedItem> feed = McLarenFeedConverter.convertFeed(ResponseParser.parseFeed(McLarenFeedResponse.REAL_FEED_RESPONSE));

        assertEquals(McLarenFeedResponse.VISIBLE_ITEMS_IN_FEED, feed.size());
    }

    @Test
    public void testLikExtractedFromTweetText() throws Exception {
        List<FeedItem> feed = McLarenFeedConverter.convertFeed(ResponseParser.parseFeed(McLarenFeedResponse.SINGLE_ITEM_FEED_WITH_HIDDEN_LINK));

        assertEquals(1, feed.size());
        assertEquals(McLarenFeedResponse.HIDDEN_LINK, feed.get(0).embeddedLink);
    }
}
