package com.github.fo2rist.mclaren.web;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.net.URL;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Base interface for any feed provider web-service.
 */
public interface FeedWebService {
    /**
     * OkHttp callback wrapper for Feed Services.
     * Forwards both network error and bad responses( 4XX, 5XX codes)
     * {@link FeedWebServiceCallback#onFailure(URL, int, int, IOException)}
     * and calls {@link FeedWebServiceCallback#onSuccess(URL, int, int, String)} only for good responses (2XX codes).
     */
    class CallbackWrapper implements Callback {
        private int pageNumber;
        private FeedWebServiceCallback callback;

        CallbackWrapper(int pageNumber, FeedWebServiceCallback callback) {
            this.pageNumber = pageNumber;
            this.callback = callback;
        }

        @Override
        public void onFailure(Call call, IOException exc) {
            URL url = getUrl(call);
            callback.onFailure(url, pageNumber, 0, exc);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            URL url = getUrl(call);
            if (response.isSuccessful()) {
                callback.onSuccess(url, pageNumber, response.code(), response.body().string());
            } else {
                callback.onFailure(url, pageNumber, response.code(), null);
            }
        }

        @NonNull
        private URL getUrl(Call call) {
            return call.request().url().url();
        }
    }

    int DEFAULT_PAGE = -1;

    /** Request latest posts. */
    void requestLatestFeed(FeedWebServiceCallback callback);

    /** Request specific page from history, which may be latest. */
    void requestFeedPage(int pageNumber, FeedWebServiceCallback callback);

}
