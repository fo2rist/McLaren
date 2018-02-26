package com.github.fo2rist.mclaren.web;

import com.github.fo2rist.mclaren.BuildConfig;
import java.net.URL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class McLarenFeedWebserviceTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testServiceInstantiation() {
        McLarenFeedWebservice webservice = new McLarenFeedWebservice(RuntimeEnvironment.application);
    }

    @Test
    public void testPageFormUrlProperlyFetched() throws Exception {
        int testPage = 123;
        int pageFromUrl = McLarenFeedWebservice.fetchPageFromUrl(new URL(BuildConfig.MCLAREN_FEED_URL + "?p=" + testPage));

        assertEquals(testPage, pageFromUrl);
    }

    @Test
    public void testNoPageProperlyHandled() throws Exception {
        int pageFromUrl = McLarenFeedWebservice.fetchPageFromUrl(new URL(BuildConfig.MCLAREN_FEED_URL));

        assertEquals(-1, pageFromUrl);
    }
}
