package com.github.fo2rist.mclaren.repository;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.models.FeedItem.SourceType;
import com.github.fo2rist.mclaren.models.FeedItem.Type;
import com.github.fo2rist.mclaren.models.ImageUrl;
import com.github.fo2rist.mclaren.models.Size;
import com.github.fo2rist.mclaren.web.models.StoryStreamResponseParser;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static com.github.fo2rist.mclaren.testdata.StoryStreamResponse.CORRECT_LINK_ORIGINAL_IMAGE_SIZE;
import static com.github.fo2rist.mclaren.testdata.StoryStreamResponse.CORRECT_LINK_ORIGINAL_IMAGE_URL;
import static com.github.fo2rist.mclaren.testdata.StoryStreamResponse.CORRECT_LINK_THREE_UP_IMAGE_SIZE;
import static com.github.fo2rist.mclaren.testdata.StoryStreamResponse.CORRECT_LINK_THREE_UP_IMAGE_URL;
import static com.github.fo2rist.mclaren.testdata.StoryStreamResponse.CORRECT_LINK_TWO_UP_IMAGE_SIZE;
import static com.github.fo2rist.mclaren.testdata.StoryStreamResponse.CORRECT_LINK_TWO_UP_IMAGE_URL;
import static com.github.fo2rist.mclaren.testdata.StoryStreamResponse.INCORRECT_LINK_FIXED_IMAGE_URL;
import static com.github.fo2rist.mclaren.testdata.StoryStreamResponse.INCORRECT_LINK_ORIGINAL_IMAGE_SIZE;
import static com.github.fo2rist.mclaren.testdata.StoryStreamResponse.REAL_FEED_RESPONSE;
import static com.github.fo2rist.mclaren.testdata.StoryStreamResponse.REAL_FEED_SIZE;
import static com.github.fo2rist.mclaren.testdata.StoryStreamResponse.SINGLE_ITEM_FEED_WITH_CORRECT_LINKS;
import static com.github.fo2rist.mclaren.testdata.StoryStreamResponse.SINGLE_ITEM_FEED_WITH_INCORRECT_LINKS_NO_SIZE;
import static com.github.fo2rist.mclaren.testdata.StoryStreamResponse.SINGLE_ITEM_FEED_WITH_INCORRECT_LINKS_WITH_SIZE;
import static com.github.fo2rist.mclaren.testdata.StoryStreamResponse.createTestUrlFromOriginalSize;
import static com.github.fo2rist.mclaren.testutilities.CustomAssertions.assertStartsWith;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class StoryStreamConverterTest {
    private StoryStreamResponseParser parser = new StoryStreamResponseParser();

    @Test
    public void testBrokenLinkFixed() throws Exception {
        List<FeedItem> feed = StoryStreamConverter.convertFeed(parser.parse(
                SINGLE_ITEM_FEED_WITH_INCORRECT_LINKS_WITH_SIZE));
        List<ImageUrl> imageUrls = feed.get(0).imageUrls;

        assertEquals(1, imageUrls.size());
        assertEquals(
                ImageUrl.create(INCORRECT_LINK_FIXED_IMAGE_URL, INCORRECT_LINK_ORIGINAL_IMAGE_SIZE),
                imageUrls.get(0));
    }

    @Test
    public void testImageWithoutSizeParsed() throws Exception {
        List<FeedItem> feed = StoryStreamConverter.convertFeed(parser.parse(
                SINGLE_ITEM_FEED_WITH_INCORRECT_LINKS_NO_SIZE));
        List<ImageUrl> imageUrls = feed.get(0).imageUrls;

        assertEquals(1, imageUrls.size());
        assertEquals(
                ImageUrl.create(INCORRECT_LINK_FIXED_IMAGE_URL, Size.UNKNOWN),
                imageUrls.get(0));
    }

    @Test
    public void testImageWithSizeParsed() throws Exception {
        List<FeedItem> feed = StoryStreamConverter.convertFeed(parser.parse(SINGLE_ITEM_FEED_WITH_CORRECT_LINKS));
        List<ImageUrl> imageUrls = feed.get(0).imageUrls;

        assertEquals(1, imageUrls.size());
        assertEquals(
                ImageUrl.create(
                        CORRECT_LINK_ORIGINAL_IMAGE_URL, CORRECT_LINK_ORIGINAL_IMAGE_SIZE,
                        CORRECT_LINK_TWO_UP_IMAGE_URL, CORRECT_LINK_TWO_UP_IMAGE_SIZE,
                        CORRECT_LINK_THREE_UP_IMAGE_URL, CORRECT_LINK_THREE_UP_IMAGE_SIZE),
                imageUrls.get(0));
    }

    @Test
    public void testIdGeneratedAsTimestampEpoch() throws Exception {
        List<FeedItem> feed = StoryStreamConverter.convertFeed(parser.parse(REAL_FEED_RESPONSE));
        FeedItem feedItem = feed.get(0);
        
        assertEquals(DateTime.parse("2018-03-02T17:23:38Z").toDate(), feedItem.dateTime);
        assertEquals(DateTime.parse("2018-03-02T17:23:38Z").toDate().getTime(), feedItem.id);
    }

    @Test
    public void testFeedParsed() throws Exception {
        List<FeedItem> feed = StoryStreamConverter.convertFeed(parser.parse(REAL_FEED_RESPONSE));

        //check the length
        assertEquals(REAL_FEED_SIZE, feed.size());

        checkItemFields(feed.get(0),
                Type.Article,
                "TESTING TIMES\nThe Formula 1 test season",
                "<p>The Formula 1 test season",
                "2018-03-02T17:23:38Z",
                SourceType.Unknown,
                "",
                "",
                createTestUrlFromOriginalSize(
                        "https://d1sdeqoxcfgxl0.cloudfront.net/images/original/8812a81ec59cb728ec930603dd7ca2eafba2e88e67b5b68b0e0ea313.jpg", 3904, 2599)
        );
        checkItemFields(feed.get(3),
                Type.Image,
                "Now at @mclaren",
                "Now at @mclaren",
                "2018-03-02T14:46:26Z",
                SourceType.Instagram,
                "fernandoalo_oficial",
                "",
                createTestUrlFromOriginalSize(
                        "https://d1sdeqoxcfgxl0.cloudfront.net/images/original/72e8d5c933b4a12e548b9e99a02ed19a9df80ca0908658d277af7a1d.jpg", 640, 700)
        );
        checkItemFields(feed.get(8),
                Type.Video,
                "Wishing all of our fans and friends ",
                "Wishing all of our fans and friends ",
                "2018-03-01T20:55:21Z",
                SourceType.Twitter,
                "McLarenF1",
                "https://video.twimg.com/amplify_video/969181322840301568/vid/640x360/fukkcv4GajOkCbsj.mp4",
                createTestUrlFromOriginalSize(
                        "https://d1sdeqoxcfgxl0.cloudfront.net/images/original/8b8529c43230a736c05118be3768820cbe7197522f6eb44a9f7ef3f8.jpg", 640, 360)
        );
        checkItemFields(feed.get(9),
                Type.Article,
                "BARCELONA TEST: DAY 4\nPROGRAMME A very positive",
                "<h3><strong>PROGRAMME</strong></h3>",
                "2018-03-01T19:16:49Z",
                SourceType.Unknown,
                "",
                "",
                createTestUrlFromOriginalSize(
                        "https://d1sdeqoxcfgxl0.cloudfront.net/images/original/be83dfbad20f59231e0da679e4ab805dadbb0807cce6a47ec0eff4cb.jpg", 4928, 3280),
                createTestUrlFromOriginalSize(
                        "https://d1sdeqoxcfgxl0.cloudfront.net/images/original/d4662bfd4a1f652b94d92afa35226c31d28c9298192b8d8ed4a42d7a.jpg", 4720, 3152),
                createTestUrlFromOriginalSize(
                        "https://d1sdeqoxcfgxl0.cloudfront.net/images/original/be6689a3e0501c06c2b2f3e2f04de688f04883f731fde8ffbeeb9070.jpg", 5278, 3518),
                createTestUrlFromOriginalSize(
                        "https://d1sdeqoxcfgxl0.cloudfront.net/images/original/723255cfa6cb46e1d3cb8908b156ff32148b62c13dda250586403e06.jpg", 3964, 2638),
                createTestUrlFromOriginalSize(
                        "https://d1sdeqoxcfgxl0.cloudfront.net/images/original/46e61e7f0f0492b80a598df6443a1750d420781b569e226fc3284c89.jpg", 5279, 3519),
                createTestUrlFromOriginalSize(
                        "https://d1sdeqoxcfgxl0.cloudfront.net/images/original/593f68c6ade9177f6b9dd0eb15d7d5ff5856d67480265fec6912888b.jpg", 5472, 3648)
        );
    }

    private void checkItemFields(FeedItem item, Type type, String textPrefix, String contentPrefix, String dateTime,
            SourceType sourceType, String sourceName, String videoLink, ImageUrl... imageUrls) {
        assertNotNull(item);

        assertEquals(type, item.type);
        assertStartsWith(textPrefix, item.text);
        assertStartsWith(contentPrefix, item.content);
        assertEquals(DateTime.parse(dateTime).toDate(), item.dateTime);
        assertEquals(sourceType, item.sourceType);
        assertEquals(sourceName, item.sourceName);
        assertEquals(videoLink, item.embeddedMediaLink);

        assertEquals(imageUrls.length, item.imageUrls.size());
        for (int i = 0; i < imageUrls.length; i++) {
            assertEquals(imageUrls[i], item.imageUrls.get(i));
        }
    }
}
