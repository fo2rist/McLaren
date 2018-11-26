package com.github.fo2rist.mclaren.web.models;

import com.github.fo2rist.mclaren.testdata.McLarenFeedResponse;
import com.github.fo2rist.mclaren.web.SafeJsonParser;
import com.github.fo2rist.mclaren.web.models.McLarenFeedItem.Source;
import com.github.fo2rist.mclaren.web.models.McLarenFeedItem.Type;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.github.fo2rist.mclaren.testutilities.CustomAssertions.assertStartsWith;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

@RunWith(JUnit4.class)
public class McLarenFeedParserTest {
    private SafeJsonParser<McLarenFeed> parser = new SafeJsonParser<>(McLarenFeed.class);

    @Test
    public void testReadDataParsedWithoutErrors() throws Exception {
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
                false,
                "https://cdn.mcl-app-api.com/api/v1/image?url=https%3a%2f%2fscontent.cdninstagram.com%2ft51.2885-15%2fs640x640%2fsh0.08%2fe35%2f20759124_860267880794627_525257414920896512_n.jpg&width=WIDTH_PLACEHOLDER&height=HEIGHT_PLACEHOLDER");
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
                "https://cdn.mcl-app-api.com/api/v1/image?url=https%3a%2f%2fpbs.twimg.com%2fmedia%2fDG81vaZXgAASWQX.jpg&width=WIDTH_PLACEHOLDER&height=HEIGHT_PLACEHOLDER",
                "https://cdn.mcl-app-api.com/api/v1/image?url=https%3a%2f%2fpbs.twimg.com%2fmedia%2fDG81vacXsAAM3K2.jpg&width=WIDTH_PLACEHOLDER&height=HEIGHT_PLACEHOLDER",
                "https://cdn.mcl-app-api.com/api/v1/image?url=https%3a%2f%2fpbs.twimg.com%2fmedia%2fDG81vaaXYAAJFHu.jpg&width=WIDTH_PLACEHOLDER&height=HEIGHT_PLACEHOLDER");
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
                "http://www.mclaren.com/formula1/tab-api/1.0/image/{width}/{height}/images/articles/hero/_R3I3889_vlGjOGj.jpg");
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
