package com.github.fo2rist.mclaren.web.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import timber.log.Timber;

/**
 * Parse raw network responses into web-data models.
 */
public class ResponseParser {
    private static final McLarenFeed EMPTY_FEED = new McLarenFeed();

    private static Gson gson = new Gson();

    private ResponseParser() {
    }

    public static McLarenFeed parseFeed(String responseBody) {
        try {
            McLarenFeed feed = gson.fromJson(responseBody, McLarenFeed.class);
            return feed != null ? feed : EMPTY_FEED;
        } catch (JsonSyntaxException exc) {
            Timber.e(exc);
            return EMPTY_FEED;
        }
    }
}
