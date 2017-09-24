package com.github.fo2rist.mclaren.web;

import android.support.annotation.Nullable;

import java.io.IOException;
import java.net.URL;

public interface WebCallback {
    /**
     * Called when response received.
     * @param url original request Url
     * @param requestedPage number of page if specific page was requested, or -1
     * @param responseCode Http response code
     * @param data Http response body as string.
     */
    void onSuccess(URL url, int requestedPage, int responseCode, @Nullable String data);

    /**
     * Called on any failure, networking or serverside.
     * @param url original request Url
     * @param requestedPage number of page if specific page was requested, or -1
     * @param responseCode Http response code, non zero if server returned the code
     * @param connectionError Error in case of any exception, null of server returned the code
     */
    void onFailure(URL url, int requestedPage, int responseCode, @Nullable IOException connectionError);
}
