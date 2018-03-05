package com.github.fo2rist.mclaren.web.models;

import com.github.fo2rist.mclaren.testdata.StoryStreamResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;


@RunWith(JUnit4.class)
public class StoryStreamParserTest {
    private StoryStreamResponseParser parser = new StoryStreamResponseParser();

    @Test
    public void testReadDataParsedWithoutErrors() throws Exception {
        StoryStream storyStream = parser.parse(StoryStreamResponse.REAL_FEED_RESPONSE);

        assertEquals(StoryStreamResponse.REAL_FEED_SIZE, storyStream.items.size());
    }
}
