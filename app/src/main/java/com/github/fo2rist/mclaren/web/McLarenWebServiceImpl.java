package com.github.fo2rist.mclaren.web;

import android.support.annotation.Nullable;

import com.github.fo2rist.mclaren.BuildConfig;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

@Singleton
public class McLarenWebServiceImpl implements McLarenFeedWebService, TransmissionWebService {

    private static final String FEED_URL = BuildConfig.MCLAREN_FEED_URL;
    private static final String MCLAREN_RACE_INFO_URL = BuildConfig.MCLAREN_RACE_INFO_URL;
    private static final String MCLAREN_RACE_LIFE_DATA_URL = BuildConfig.MCLAREN_RACE_LIFE_DATA_URL;
    private static final Headers DEFAULT_HEADERS = Headers.of(
            "Content-Type",     "application/json; charset=utf-8",
            "Authorization",    BuildConfig.MCLAREN_CDN_AUTH
    );

    private OkHttpClient client;

    @Inject
    McLarenWebServiceImpl(@Named("web-okhttp") OkHttpClient webClient) {
        this.client = webClient;
    }

    @Override
    public void requestLatestFeed(FeedRequestCallback callback) {
        client.newCall(createLatestFeedRequest())
                .enqueue(new FeedCallbackWrapper(FeedWebService.DEFAULT_PAGE, callback));
    }

    @Override
    public void requestFeedPage(int pageNumber, FeedRequestCallback callback) {
        client.newCall(createFeedPageRequest(pageNumber))
                .enqueue(new FeedCallbackWrapper(pageNumber, callback));
    }

    private Request createLatestFeedRequest() {
        return createFeedPageRequest(null);
    }

    private Request createFeedPageRequest(@Nullable Integer pageNumber) {

        HttpUrl.Builder urlBuilder = HttpUrl.parse(FEED_URL).newBuilder();

        if (pageNumber != null) {
            urlBuilder.addQueryParameter("p", String.valueOf(pageNumber));
        }
        //By default it's GET unless .method or .post aren't called
        return new Request.Builder()
                .url(urlBuilder.build())
                .headers(DEFAULT_HEADERS)
                .build();
    }

    @Override
    public void requestTransmission(final TransmissionRequestCallback callback) {
        client.newCall(createTransmissionRequest())
                .enqueue(new TransmissionCallbackWrapper(callback));
    }

    private Request createTransmissionRequest() {
        return new Request.Builder()
                .url(MCLAREN_RACE_LIFE_DATA_URL)
                .headers(DEFAULT_HEADERS)
                .build();
    }
}
