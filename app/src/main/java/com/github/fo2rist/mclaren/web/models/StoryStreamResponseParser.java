package com.github.fo2rist.mclaren.web.models;

import com.github.fo2rist.mclaren.web.SafeJsonParser;

/**
 * Parses StoryStream raw response into web data-model.
 */
public class StoryStreamResponseParser extends SafeJsonParser<StoryStream> {

    public StoryStreamResponseParser() {
        super(StoryStream.class);
    }
}
