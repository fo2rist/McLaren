package com.github.fo2rist.mclaren.ui.utils;

import com.github.fo2rist.mclaren.testdata.FeedItems;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class FeedLinkUtilsTagsTest {

    @Parameters(name = "Test tag resolved \"{0}\" -> \"{1}\"")
    public static List<String[]> data() {
        return Arrays.asList(new String[][]{
                {"some_tag", "some_tag"},
                {"@some_tag", "some_tag"},
                {"#some_tag", "some_tag"},
                {".@some_tag", "some_tag"},
                {" some_tag", "some_tag"},
        });
    }

    @Parameter(0)
    public String sourceTag;
    @Parameter(1)
    public String targetTag;

    @Test
    public void testTwitterMentionResolved() throws Exception {
        String link = FeedLinkUtils.getFeedMentionLink(FeedItems.TWITTER_GALLERY_ITEM, sourceTag);

        assertEquals("https://twitter.com/" + targetTag, link);
    }

    @Test
    public void testInstagramMentionResolved() throws Exception {
        String link = FeedLinkUtils.getFeedMentionLink(FeedItems.INSTAGRAM_GALLERY_ITEM, sourceTag);

        assertEquals("https://www.instagram.com/" + targetTag, link);
    }

    @Test
    public void testTwitterHashTagResolved() throws Exception {
        String link = FeedLinkUtils.getFeedHashtagLink(FeedItems.TWITTER_GALLERY_ITEM, sourceTag);

        assertEquals("https://twitter.com/hashtag/" + targetTag, link);
    }

    @Test
    public void testInstagramHashTagResolved() throws Exception {
        String link = FeedLinkUtils.getFeedHashtagLink(FeedItems.INSTAGRAM_GALLERY_ITEM, sourceTag);

        assertEquals("https://www.instagram.com/explore/tags/" + targetTag, link);
    }
}
