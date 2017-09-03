package com.github.fo2rist.mclaren.web;

import com.github.fo2rist.mclaren.BuildConfig;
import javax.inject.Inject;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class McLarenFeedWebservice implements FeedWebsevice {
    private static final String MCLAREN_FEED_URL = BuildConfig.MCLAREN_FEED_URL;
    private static final String MCLAREN_RACE_INFO_URL = BuildConfig.MCLAREN_RACE_INFO_URL;
    private static final String MCLAREN_RACE_LIFE_DATA_URL = BuildConfig.MCLAREN_RACE_LIFE_DATA_URL;
    private static final Headers DEFAULT_HEADERS = Headers.of(
            "Content-Type",     "application/json; charset=utf-8",
            "Authorization",    BuildConfig.MCLAREN_CDN_AUTH
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
