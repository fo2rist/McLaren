package com.github.fo2rist.mclaren.web;

import android.support.annotation.NonNull;

import com.github.fo2rist.mclaren.BuildConfig;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

@Singleton
public class StoryStreamWebServiceImpl implements StoryStreamWebService {

    @NonNull
    private static final HttpUrl FEED_URL = HttpUrl.parse(BuildConfig.STORYSTREAM_FEED_URL);
    private static final String ACCESS_TOKEN = BuildConfig.STORYSTREAM_TOKEN;
    private static final int STORIES_PER_PAGE = 20;
    private static final boolean INCLUDE_ALL_MEDIA = true;
    private static final Headers DEFAULT_HEADERS = Headers.of(
            "Content-Type", "application/json; charset=utf-8"
    );

    private final OkHttpClient client;

    @Inject
    StoryStreamWebServiceImpl(@Named("web-okhttp") OkHttpClient webClient) {
        this.client = webClient;
    }

    @Override
    public void requestLatestFeed(@NonNull FeedRequestCallback callback) {
        client.newCall(createLatestFeedRequest())
                .enqueue(new FeedCallbackWrapper(FeedWebService.DEFAULT_PAGE, callback));
    }

    @Override
    public void requestFeedPage(int pageNumber, @NonNull FeedRequestCallback callback) {
        client.newCall(createFeedPageRequest(pageNumber))
                .enqueue(new FeedCallbackWrapper(pageNumber, callback));
    }

    private Request createLatestFeedRequest() {
        return createFeedPageRequest(1); //StoryStream reacts on page=1 properly so we can use it for the top page
    }

    private Request createFeedPageRequest(int pageNumber) {
        HttpUrl url = FEED_URL
                .newBuilder()
                .addQueryParameter("access_token", ACCESS_TOKEN)
                .addQueryParameter("page", String.valueOf(pageNumber))
                .addQueryParameter("rpp", String.valueOf(STORIES_PER_PAGE))
                .addQueryParameter("all_media", String.valueOf(INCLUDE_ALL_MEDIA))
                .build();

        return new Request.Builder()
                .url(url)
                .headers(DEFAULT_HEADERS)
                .build();
    }
}
