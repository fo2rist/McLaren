package com.github.fo2rist.mclaren.repository;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.models.FeedItem.SourceType;
import com.github.fo2rist.mclaren.models.FeedItem.Type;
import com.github.fo2rist.mclaren.testdata.StoryStreamResponse;
import com.github.fo2rist.mclaren.web.models.StoryStreamResponseParser;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(JUnit4.class)
public class StoryStreamConverterTest {
    private StoryStreamResponseParser parser = new StoryStreamResponseParser();
    private List<FeedItem> feed;

    @Before
    public void setUp() throws Exception {
        feed = StoryStreamConverter.convertFeed(parser.parse(StoryStreamResponse.REAL_FEED_RESPONSE));
    }

    @Test
    public void testFeedParsed() throws Exception {
        //check the length
        assertEquals(StoryStreamResponse.REAL_FEED_SIZE, feed.size());

        checkItemFields(feed.get(0),
                Type.Article,
                "2018-03-02T17:23:38Z",
                SourceType.Unknown,
                "",
                "<p>The Formula 1 test season",
                "",
                "https://d1sdeqoxcfgxl0.cloudfront.net/images/original/8812a81ec59cb728ec930603dd7ca2eafba2e88e67b5b68b0e0ea313.jpg");
        checkItemFields(feed.get(1),
                Type.Image,
                "2018-03-02T17:20:45Z",
                SourceType.Twitter,
                "McLarenAuto",
                "Keep your eyes fixed on #McLaren",
                "",
                "https://d1sdeqoxcfgxl0.cloudfront.net/images/original/97d24a881ec9bed36522e6a8ac972edabb05d903ce53ddea0fe90527.jpg");
        checkItemFields(feed.get(2),
                Type.Image,
                "2018-03-02T17:20:41Z",
                SourceType.Twitter,
                "McLarenCareers",
                "McLaren's award-winning Digital",
                "",
                "https://d1sdeqoxcfgxl0.cloudfront.net/images/original/a0e558ba60cc281f8de533d7c7cf056f42676922c304f905a251139b.jpg");
        checkItemFields(feed.get(3),
                Type.Image,
                "2018-03-02T14:46:26Z",
                SourceType.Instagram,
                "fernandoalo_oficial",
                "Now at @mclaren",
                "",
                "https://d1sdeqoxcfgxl0.cloudfront.net/images/original/72e8d5c933b4a12e548b9e99a02ed19a9df80ca0908658d277af7a1d.jpg");
        checkItemFields(feed.get(8),
                Type.Video,
                "2018-03-01T20:55:21Z",
                SourceType.Twitter,
                "McLarenF1",
                "Wishing all of our fans and friends ",
                "https://video.twimg.com/amplify_video/969181322840301568/vid/640x360/fukkcv4GajOkCbsj.mp4",
                "https://d1sdeqoxcfgxl0.cloudfront.net/images/original/8b8529c43230a736c05118be3768820cbe7197522f6eb44a9f7ef3f8.jpg");
        checkItemFields(feed.get(9),
                Type.Article,
                "2018-03-01T19:16:49Z",
                SourceType.Unknown,
                "",
                "<h3><strong>PROGRAMME</strong></h3>",
                "",
                "https://d1sdeqoxcfgxl0.cloudfront.net/images/original/be83dfbad20f59231e0da679e4ab805dadbb0807cce6a47ec0eff4cb.jpg",
                "https://d1sdeqoxcfgxl0.cloudfront.net/images/original/d4662bfd4a1f652b94d92afa35226c31d28c9298192b8d8ed4a42d7a.jpg",
                "https://d1sdeqoxcfgxl0.cloudfront.net/images/original/be6689a3e0501c06c2b2f3e2f04de688f04883f731fde8ffbeeb9070.jpg",
                "https://d1sdeqoxcfgxl0.cloudfront.net/images/original/723255cfa6cb46e1d3cb8908b156ff32148b62c13dda250586403e06.jpg",
                "https://d1sdeqoxcfgxl0.cloudfront.net/images/original/46e61e7f0f0492b80a598df6443a1750d420781b569e226fc3284c89.jpg",
                "https://d1sdeqoxcfgxl0.cloudfront.net/images/original/593f68c6ade9177f6b9dd0eb15d7d5ff5856d67480265fec6912888b.jpg");
    }

    private void checkItemFields(FeedItem item, Type itemType, String dateTime, SourceType sourceType,
            String sourceName, String contentPrefix, String videoLink, String... imageUrls) {
        assertNotNull(item);

        assertEquals(itemType, item.type);
        assertEquals(DateTime.parse(dateTime).toDate(), item.dateTime);
        assertEquals(sourceType, item.sourceType);
        assertEquals(sourceName, item.sourceName);
        assertNotNull(item.content);
        assertTrue(item.content.startsWith(contentPrefix));
        assertEquals(videoLink, item.videoLink);
        assertEquals(imageUrls.length, item.imageUris.length);
        for (int i = 0; i < imageUrls.length; i++) {
            assertEquals(imageUrls[i], item.imageUris[i]);
        }
    }
}
