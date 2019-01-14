package com.github.fo2rist.mclaren.web.models;

import com.github.fo2rist.mclaren.testdata.McLarenFeedResponse;
import com.github.fo2rist.mclaren.web.SafeJsonParser;
import com.github.fo2rist.mclaren.web.models.McLarenFeedItem.Source;
import com.github.fo2rist.mclaren.web.models.McLarenFeedItem.Type;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.github.fo2rist.mclaren.testdata.FeedUrlSamplesKt.IMAGE_URL_TAB_API_WITH_PLACEHOLDERS;
import static com.github.fo2rist.mclaren.testdata.FeedUrlSamplesKt.IMAGE_URL_TAB_API_WITH_PLACEHOLDERS_0;
import static com.github.fo2rist.mclaren.testdata.FeedUrlSamplesKt.IMAGE_URL_TAB_API_WITH_PLACEHOLDERS_1;
import static com.github.fo2rist.mclaren.testdata.FeedUrlSamplesKt.IMAGE_URL_TAB_API_WITH_PLACEHOLDERS_2;
import static com.github.fo2rist.mclaren.testdata.FeedUrlSamplesKt.IMAGE_URL_TAB_API_WITH_PLACEHOLDERS_3;
import static com.github.fo2rist.mclaren.testutilities.CustomAssertions.assertStartsWith;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

@RunWith(JUnit4.class)
public class McLarenFeedParserTest {

    private SafeJsonParser<McLarenFeed> parser = new SafeJsonParser<>(McLarenFeed.class);

    @Test
    public void testReadDataParsedWithoutErrors() {
        McLarenFeed mcLarenFeedItems = parser.parse(McLarenFeedResponse.REAL_FEED_RESPONSE);

        assertEquals(McLarenFeedResponse.REAL_FEED_SIZE, mcLarenFeedItems.size());

        checkItemFields(mcLarenFeedItems.get(0),
                Type.IMAGE,
                "@mclaren",
                22549,
                null,
                "2017-08-12T10:34:06Z",
                Source.INSTAGRAM,
                null,
                null,
                "The swimming pool",
                "The swimming pool",
                false, IMAGE_URL_TAB_API_WITH_PLACEHOLDERS_0);

        checkItemFields(mcLarenFeedItems.get(6),
                Type.GALLERY,
                "@McLarenAuto",
                22543,
                null,
                "2017-08-11T13:02:59+00:00",
                Source.TWITTER,
                null,
                null,
                "Here's a slightly different",
                "Here's a slightly different",
                false,
                IMAGE_URL_TAB_API_WITH_PLACEHOLDERS_1,
                IMAGE_URL_TAB_API_WITH_PLACEHOLDERS_2,
                IMAGE_URL_TAB_API_WITH_PLACEHOLDERS_3);
        checkItemFields(mcLarenFeedItems.get(8),
                Type.ARTICLE,
                null,
                22541,
                "mclaren.com",
                "2017-08-11T12:10:00+00:00",
                null,
                "The ultimate F1 summer holiday quiz",
                null,
                "<div>\r\n<p>For F1 folk, the summer",
                "Enjoying exclusive content",
                false,
                IMAGE_URL_TAB_API_WITH_PLACEHOLDERS);
    }

    private void checkItemFields(McLarenFeedItem item, Type type, String author, int id, Object origin,
            String publicationDate, Source source, String title, String body, String contentPrefix,
            String tweetTextPrefix, boolean hidden, String... imageUrls) {
        assertEquals(type, item.type);
        assertEquals(id, item.id);
        assertEquals(author, item.author);
        assertEquals(origin, item.origin);
        assertEquals(DateTime.parse(publicationDate).toDate(), item.publicationDate);
        assertEquals(source, item.source);
        assertEquals(title, item.title);
        assertStartsWith(contentPrefix, item.content);
        assertNull(body, item.body);
        assertStartsWith(tweetTextPrefix, item.tweetText);
        assertEquals(hidden, item.hidden);
        assertEquals(imageUrls.length, item.media.size());
        for (int i = 0; i < imageUrls.length; i++) {
            assertEquals(imageUrls[i], item.media.get(i).url);
        }
    }
}
