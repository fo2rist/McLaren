package com.github.fo2rist.mclaren.web

import android.support.annotation.VisibleForTesting
import com.github.fo2rist.mclaren.web.utils.executeAsync
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Base implementation of [FeedWebService] that leaves requests specifics to be implemented but handles delivery.
 */
abstract class BaseFeedWebService(
    private val client: OkHttpClient
) : FeedWebService {

    override suspend fun requestLatestFeed(): String? {
        return executeAsync(createLatestFeedRequest())
    }

    override suspend fun requestFeedPage(pageNumber: Int): String? {
        return executeAsync(createFeedPageRequest(pageNumber))
    }

    protected suspend fun executeAsync(request: Request): String? {
        return client.newCall(request).executeAsync()
    }

    private fun createLatestFeedRequest(): Request = createFeedPageRequest()

    /**
     * @param pageNumber specific number or null to get the latest page
     */
    @VisibleForTesting
    abstract fun createFeedPageRequest(pageNumber: Int? = null): Request
}
