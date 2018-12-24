package com.github.fo2rist.mclaren.web

import com.github.fo2rist.mclaren.web.utils.BadResponse
import com.github.fo2rist.mclaren.web.utils.OkHttpCallbackWrapper
import java.io.IOException
import java.net.ConnectException
import java.net.URL

/** Special page number to request the latest page via [FeedWebService]. */
const val DEFAULT_PAGE = -1

/**
 * Base interface for any feed provider web-service.
 */
interface FeedWebService {
    /**
     * Callback for requests to [FeedWebService] methods.
     */
    interface FeedRequestCallback {

        /**
         * Called when response received.
         * @param url original request Url
         * @param requestedPage number of page if specific page was requested, or [DEFAULT_PAGE]
         * @param responseCode Http response code
         * @param data Http response body as string.
         */
        fun onSuccess(url: URL, requestedPage: Int, responseCode: Int, data: String?)

        /**
         * Called on any failure, networking or server side.
         * @param url original request Url
         * @param requestedPage number of page if specific page was requested, or [DEFAULT_PAGE]
         * @param responseCode Http response code, non zero if server returned the code
         * @param connectionError Error in case of any exception, null of server returned the code
         */
        fun onFailure(url: URL, requestedPage: Int, responseCode: Int, connectionError: IOException?)
    }

    /**
     * OkHttp callback wrapper for Feed Services.
     * Forwards both network error and bad responses( 4XX, 5XX codes)
     * [FeedRequestCallback.onFailure]
     * and calls [FeedRequestCallback.onSuccess] only for good responses (2XX codes).
     */
    class FeedCallbackWrapper internal constructor(
        private val pageNumber: Int,
        private val callback: FeedRequestCallback
    ) : OkHttpCallbackWrapper() {

        override fun onOkHttpFailure(url: URL, responseCode: Int, connectionError: IOException?) {
            callback.onFailure(url, pageNumber, responseCode, connectionError)
        }

        override fun onOkHttpSuccess(url: URL, responseCode: Int, responseBody: String?) {
            callback.onSuccess(url, pageNumber, responseCode, responseBody)
        }
    }

    /**
     * Request latest posts.
     * @throws ConnectException if networking error occurs
     * @throws BadResponse if server responds with 4xx-5xx code
     */
    @Throws(BadResponse::class, ConnectException::class)
    suspend fun requestLatestFeed(): String?

    /** Request specific page from history, which may be latest.  */
    fun requestFeedPage(pageNumber: Int, callback: FeedRequestCallback)

}
