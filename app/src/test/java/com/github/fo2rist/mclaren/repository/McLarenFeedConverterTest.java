package com.github.fo2rist.mclaren.repository;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.models.FeedItem.SourceType;
import com.github.fo2rist.mclaren.testdata.McLarenFeedResponse;
import com.github.fo2rist.mclaren.web.models.McLarenFeedResponseParser;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static com.github.fo2rist.mclaren.testutilities.CustomAssertions.assertStartsWith;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class McLarenFeedConverterTest {
    private McLarenFeedResponseParser parser = new McLarenFeedResponseParser();

    @Test
    public void testRealDataFilteredByHiddenFlag() throws Exception {
        List<FeedItem> feed = McLarenFeedConverter.convertFeed(parser.parse(McLarenFeedResponse.REAL_FEED_RESPONSE));

        assertEquals(McLarenFeedResponse.VISIBLE_ITEMS_IN_FEED, feed.size());
    }

    @Test
    public void testLikExtractedFromTweetText() throws Exception {
        List<FeedItem> feed = McLarenFeedConverter.convertFeed(parser.parse(McLarenFeedResponse.SINGLE_ITEM_FEED_WITH_HIDDEN_LINK));

        assertEquals(1, feed.size());
        assertEquals(McLarenFeedResponse.HIDDEN_LINK, feed.get(0).embeddedMediaLink);
    }

    @Test
    public void testSomeItemsParsing() throws Exception {
        List<FeedItem> feed = McLarenFeedConverter.convertFeed(parser.parse(McLarenFeedResponse.REAL_FEED_RESPONSE));

        checkItemFields(feed.get(0),
                22549,
                FeedItem.Type.Image,
                "The swimming pool",
                "The swimming pool",
                "2017-08-12T10:34:06Z",
                SourceType.Instagram,
                "@mclaren",
                "https://www.instagram.com/p/BXsMf3HlcB4",
                "https://cdn.mcl-app-api.com/api/v1/image?url=https%3a%2f%2fscontent.cdninstagram.com%2ft51.2885-15%2fs640x640%2fsh0.08%2fe35%2f20759124_860267880794627_525257414920896512_n.jpg&width=WIDTH_PLACEHOLDER&height=HEIGHT_PLACEHOLDER");

        checkItemFields(feed.get(3),
                22541,
                FeedItem.Type.Article,
                "The ultimate F1 summer holiday quiz",
                "<div>\r\n<p>For F1 folk, the summer",
                "2017-08-11T12:10:00+00:00",
                SourceType.Unknown,
                "mclaren.com",
                "",
                "http://www.mclaren.com/formula1/tab-api/1.0/image/{width}/{height}/images/articles/hero/_R3I3889_vlGjOGj.jpg");
    }

    private void checkItemFields(FeedItem item, int id, FeedItem.Type type, String textPrefix, String contentPrefix,
            String dateTime, SourceType sourceType, String sourceName, String embeddedMediaLink, String... imageUrls) {
        assertNotNull(item.content);
        assertEquals(id, item.id);
        assertEquals(type, item.type);
        assertStartsWith(textPrefix, item.text);
        assertStartsWith(contentPrefix, item.content);
        assertEquals(DateTime.parse(dateTime).toDate(), item.dateTime);
        assertEquals(sourceType, item.sourceType);
        assertEquals(sourceName, item.sourceName);
        assertEquals(embeddedMediaLink, item.embeddedMediaLink);

        assertEquals(imageUrls.length, item.imageUrls.length);
        for (int i = 0; i < imageUrls.length; i++) {
            assertEquals(imageUrls[i], item.imageUrls[i]);
        }
    }
}