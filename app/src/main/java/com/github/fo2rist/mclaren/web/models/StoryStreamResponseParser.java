package com.github.fo2rist.mclaren.web.models;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import timber.log.Timber;

public class StoryStreamResponseParser {
    private static final StoryStream EMPTY_FEED = new StoryStream();

    private static Gson gson = new Gson();

    private StoryStreamResponseParser() {
    }

    public static StoryStream parse(String data) {
        try {
            StoryStream feed = gson.fromJson(data, StoryStream.class);
            return feed != null ? feed : EMPTY_FEED;
        } catch (JsonSyntaxException exc) {
            Timber.e(exc);
            return EMPTY_FEED;
        }
    }
}
