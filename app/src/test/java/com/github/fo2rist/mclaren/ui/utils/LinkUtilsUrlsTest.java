package com.github.fo2rist.mclaren.ui.utils;

import com.github.fo2rist.mclaren.testdata.FeedItems;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class LinkUtilsUrlsTest {

    @Test
    public void testUnknonwSourceTreatAsMcLarenF1Link() throws Exception {
        String link = LinkUtils.getFeedMentionLink(FeedItems.MCLAREN_ARTICLE_ITEM,
                "any");

        assertEquals("http://www.mclaren.com/formula1/", link);
    }

    @Test
    public void testVideoLinkFoundWhenPresent() throws Exception {
        String link = LinkUtils.getMediaLink(FeedItems.VIDEO_ITEM);

        assertNotNull(link);
        assertEquals(FeedItems.VIDEO_ITEM.embeddedLink, link);
    }
}
