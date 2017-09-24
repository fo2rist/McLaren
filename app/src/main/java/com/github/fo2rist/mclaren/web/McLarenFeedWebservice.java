package com.github.fo2rist.mclaren.web;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.github.fo2rist.mclaren.BuildConfig;
import java.io.IOException;
import java.net.URL;
import javax.inject.Inject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class McLarenFeedWebservice implements FeedWebsevice {

    private static class CallbackWrapper implements Callback {
        private WebCallback callback;

        CallbackWrapper(WebCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onFailure(Call call, IOException exc) {
            URL url = getUrl(call);
            int requestedPage = fetchPageFromUrl(url);
            callback.onFailure(url, requestedPage, 0, exc);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            URL url = getUrl(call);
            int requestedPage = fetchPageFromUrl(url);
            if (response.isSuccessful()) {
                callback.onSuccess(url, requestedPage, response.code(), response.body().string());
            } else {
                callback.onFailure(url, requestedPage, response.code(), null);
            }
        }

        @NonNull
        private URL getUrl(Call call) {
            return call.request().url().url();
        }
    }
    private static final String MCLAREN_FEED_URL = BuildConfig.MCLAREN_FEED_URL;
    private static final String MCLAREN_RACE_INFO_URL = BuildConfig.MCLAREN_RACE_INFO_URL;
    private static final String MCLAREN_RACE_LIFE_DATA_URL = BuildConfig.MCLAREN_RACE_LIFE_DATA_URL;
    private static final Headers DEFAULT_HEADERS = Headers.of(
            "Content-Type",     "application/json; charset=utf-8",
            "Authorization",    BuildConfig.MCLAREN_CDN_AUTH
    );

    private static final String GET = "GET";
    private static final String HEAD = "HEAD";


    //TODO use cache here like described in https://github.com/square/okhttp/wiki/Recipes
    private OkHttpClient client = new OkHttpClient();

    @Inject
    McLarenFeedWebservice() {
        //empty constructor for injection
    }

    @Override
    public void requestLatestFeed(WebCallback callback) {
        client.newCall(createLatestFeedRequest())
                .enqueue(new CallbackWrapper(callback));
    }

    @Override
    public void requestFeedPage(int pageNumber, WebCallback callback) {
        client.newCall(createFeedPageRequest(GET, pageNumber))
                .enqueue(new CallbackWrapper(callback));
    }

    @Override
    public void requestFeedPageHead(int pageNumber, WebCallback callback) {
        client.newCall(createFeedPageRequest(HEAD, pageNumber))
                .enqueue(new CallbackWrapper(callback));
    }

    private Request createLatestFeedRequest() {
        return createFeedPageRequest(GET, null);
    }

    private Request createFeedPageRequest(String method, @Nullable Integer pageNumber) {
        String finalUrl;
        if (pageNumber == null) {
            finalUrl = MCLAREN_FEED_URL;
        } else {
            finalUrl = MCLAREN_FEED_URL + "?p=" + pageNumber; //Yep it's dirty
        }
        return new Request.Builder()
                .method(method, null)
                .url(finalUrl)
                .headers(DEFAULT_HEADERS)
                .build();
    }

    /**
     * @return page number if found -1 otherwise.
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    static int fetchPageFromUrl(URL url) {
        String query = url.getQuery();
        if (query == null || query.isEmpty()) {
            return -1;
        }

        try {
            String queryWithParamNameStripped = query.replaceAll("^.?=", "");
            return Integer.valueOf(queryWithParamNameStripped);
        } catch (NumberFormatException exc) {
            return -1;
        }
    }
}
