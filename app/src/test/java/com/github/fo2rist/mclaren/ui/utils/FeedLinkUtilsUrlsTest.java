package com.github.fo2rist.mclaren.ui.utils;

import com.github.fo2rist.mclaren.testdata.FeedItems;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class FeedLinkUtilsUrlsTest {

    @Test
    public void testUnknonwSourceTreatAsMclarenF1Link() throws Exception {
        String link = FeedLinkUtils.getFeedMentionLink(FeedItems.MCLAREN_ARTICLE_ITEM,
                "any");

        assertEquals("http://www.mclaren.com/formula1/", link);
    }
}
