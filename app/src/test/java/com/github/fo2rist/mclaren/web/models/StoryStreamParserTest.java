package com.github.fo2rist.mclaren.web.models;

import com.github.fo2rist.mclaren.testdata.StoryStreamResponse;
import com.github.fo2rist.mclaren.web.SafeJsonParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;


@RunWith(JUnit4.class)
public class StoryStreamParserTest {
    private SafeJsonParser<StoryStream> parser = new SafeJsonParser<>(StoryStream.class);

    @Test
    public void testReadDataParsedWithoutErrors() throws Exception {
        StoryStream storyStream = parser.parse(StoryStreamResponse.REAL_FEED_RESPONSE);

        assertEquals(StoryStreamResponse.REAL_FEED_SIZE, storyStream.items.size());
    }
}
