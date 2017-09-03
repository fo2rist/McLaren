package com.github.fo2rist.mclaren.web.model;

import com.github.fo2rist.mclaren.models.FeedItem;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class FeedConverterTest {
    @Test
    public void testRealDataFilteredByHiddenFlag() throws Exception {
        List<FeedItem> feed = McLarenFeedConverter.convertFeed(ResponseParser.parseFeed(TestData.REAL_FEED_RESPONSE));

        assertEquals(TestData.VISIBLE_ITEMS_IN_FEED, feed.size());
    }
}
