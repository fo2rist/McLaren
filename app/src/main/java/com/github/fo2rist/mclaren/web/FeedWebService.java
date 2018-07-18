package com.github.fo2rist.mclaren.web;

import android.support.annotation.Nullable;

import com.github.fo2rist.mclaren.web.utils.OkHttpCallbackWrapper;
import java.io.IOException;
import java.net.URL;
import org.jetbrains.annotations.NotNull;

/**
 * Base interface for any feed provider web-service.
 */
public interface FeedWebService {
    /**
     * Callback for requests to {@link FeedWebService} methods.
     */
    interface FeedRequestCallback {

        /**
         * Called when response received.
         * @param url original request Url
         * @param requestedPage number of page if specific page was requested, or {@value FeedWebService#DEFAULT_PAGE}
         * @param responseCode Http response code
         * @param data Http response body as string.
         */
        void onSuccess(URL url, int requestedPage, int responseCode, @Nullable String data);

        /**
         * Called on any failure, networking or server side.
         * @param url original request Url
         * @param requestedPage number of page if specific page was requested, or {@value FeedWebService#DEFAULT_PAGE}
         * @param responseCode Http response code, non zero if server returned the code
         * @param connectionError Error in case of any exception, null of server returned the code
         */
        void onFailure(URL url, int requestedPage, int responseCode, @Nullable IOException connectionError);
    }

    /**
     * OkHttp callback wrapper for Feed Services.
     * Forwards both network error and bad responses( 4XX, 5XX codes)
     * {@link FeedRequestCallback#onFailure(URL, int, int, IOException)}
     * and calls {@link FeedRequestCallback#onSuccess(URL, int, int, String)} only for good responses (2XX codes).
     */
    class FeedCallbackWrapper extends OkHttpCallbackWrapper {
        private int pageNumber;
        private FeedRequestCallback callback;

        FeedCallbackWrapper(int pageNumber, FeedRequestCallback callback) {
            this.pageNumber = pageNumber;
            this.callback = callback;
        }

        @Override
        public void onOkHttpFailure(@NotNull URL url, int responseCode, @Nullable IOException connectionError) {
            callback.onFailure(url, pageNumber, responseCode, connectionError);
        }

        @Override
        public void onOkHttpSuccess(@NotNull URL url, int responseCode, @Nullable String responseBody) {
            callback.onSuccess(url, pageNumber, responseCode, responseBody);
        }
    }

    int DEFAULT_PAGE = -1;

    /** Request latest posts. */
    void requestLatestFeed(FeedRequestCallback callback);

    /** Request specific page from history, which may be latest. */
    void requestFeedPage(int pageNumber, FeedRequestCallback callback);
}
