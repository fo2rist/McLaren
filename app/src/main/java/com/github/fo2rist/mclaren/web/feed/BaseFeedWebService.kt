package com.github.fo2rist.mclaren.web.feed

import android.support.annotation.VisibleForTesting
import com.github.fo2rist.mclaren.web.utils.executeSuspend
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Base implementation of [FeedWebService] that leaves requests specifics to be implemented but handles delivery.
 */
abstract class BaseFeedWebService(
    private val client: OkHttpClient
) : FeedWebService {

    override suspend fun requestLatestFeed(): String? {
        return execute(createLatestFeedRequest())
    }

    override suspend fun requestFeedPage(pageNumber: Int): String? {
        return execute(createFeedPageRequest(pageNumber))
    }

    protected suspend fun execute(request: Request): String? {
        return client.newCall(request).executeSuspend()
    }

    private fun createLatestFeedRequest(): Request = createFeedPageRequest()

    /**
     * @param pageNumber specific number or null to get the latest page
     */
    @VisibleForTesting
    abstract fun createFeedPageRequest(pageNumber: Int? = null): Request
}
