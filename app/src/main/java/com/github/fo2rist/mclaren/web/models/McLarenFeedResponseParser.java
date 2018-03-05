package com.github.fo2rist.mclaren.web.models;

import com.github.fo2rist.mclaren.web.SafeJsonParser;

/**
 * Parses McLaren raw network responses into web data-model.
 */
public class McLarenFeedResponseParser extends SafeJsonParser<McLarenFeed> {

    public McLarenFeedResponseParser() {
        super(McLarenFeed.class);
    }
}
