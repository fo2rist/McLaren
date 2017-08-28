package com.github.fo2rist.mclaren.web;

import javax.inject.Inject;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class McLarenFeedWebservice implements FeedWebsevice {
    /** Placeholder that Web API returns in image URLs to specify width. */
    public static final String WIDTH_PLACEHOLDER = "WIDTH_PLACEHOLDER";
    /** Placeholder that Web API returns in image URLs to specify height. */
    public static final String HEIGHT_PLACEHOLDER = "HEIGHT_PLACEHOLDER";

    private static final String MCLAREN_FEED_URL = "http://cdn.mcl-app-api.com/api/v1/content";
    private static final String MCLAREN_RACE_INFO_URL = "http://cdn.mcl-app-api.com/api/v1/race/141"; //141 - Hungary GP 2017
    private static final String MCLAREN_RACE_LIFE_DATA_URL = "http://cdn.mcl-app-api.com/api/v1/live/141";

    private static final Headers DEFAULT_HEADERS = Headers.of(
            "Content-Type",     "application/json; charset=utf-8",
            "Authorization",    "token IVZtVW5ZekpWI2s2OEJiRXltVCZ0MVNoeWdyI1pSM28="
    );

    private OkHttpClient client = new OkHttpClient();

    private Request feedRequest = new Request.Builder()
            .url(MCLAREN_FEED_URL)
            .headers(DEFAULT_HEADERS)
            .build();

    @Inject
    McLarenFeedWebservice() {
    }

    @Override
    public void requestFeed(Callback callback) {
        client.newCall(feedRequest).enqueue(callback);
    }
}
