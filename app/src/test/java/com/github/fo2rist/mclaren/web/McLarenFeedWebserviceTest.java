package com.github.fo2rist.mclaren.web;

import com.github.fo2rist.mclaren.BuildConfig;
import java.net.URL;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class McLarenFeedWebserviceTest {

    private McLarenFeedWebservice webservice;

    @Before
    public void setUp() throws Exception {
        webservice = new McLarenFeedWebservice();
    }

    @Test
    public void testPageFormUrlProperlyFetched() throws Exception {
        int testPage = 123;
        int pageFromUrl = webservice.fetchPageFromUrl(new URL(BuildConfig.MCLAREN_FEED_URL + "?p=" + testPage));

        assertEquals(testPage, pageFromUrl);
    }

    @Test
    public void testNoPageProperlyHandled() throws Exception {
        int pageFromUrl = webservice.fetchPageFromUrl(new URL(BuildConfig.MCLAREN_FEED_URL));

        assertEquals(-1, pageFromUrl);
    }
}
