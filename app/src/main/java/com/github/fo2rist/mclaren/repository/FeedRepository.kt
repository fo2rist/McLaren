package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.repository.FeedRepositoryEventBus.LoadingEvent
import com.github.fo2rist.mclaren.repository.converters.FeedConverter
import com.github.fo2rist.mclaren.web.FeedWebService
import com.github.fo2rist.mclaren.web.SafeJsonParser
import java.io.IOException
import java.net.URL
import java.util.*

/**
 * Supplier of the news feed.
 */
interface FeedRepository {
    /** Warm up repository to load the next page from history.  */
    fun prepareForHistoryLoading()

    /** Requests the next portion of not loaded yet items in feed.  */
    fun loadNextPage()

    /** Request the latest entries from feed.  */
    fun loadLatestPage()
}

/**
 * Base implementation of news feed repo.
 * Takes care about storing feed, caching it and requesting feed from web-server.
 * Descendants to implement [prepareForHistoryLoading], [loadNextPage] and [onPageLoaded]
 * to support paging algorithm specific for particular repo.
 */
abstract class BaseFeedRepository<T>(
    @JvmField
    protected val webService: FeedWebService,
    @JvmField
    protected val feedConverter: FeedConverter<T>,
    @JvmField
    protected val repositoryEventBus: FeedRepositoryEventBus,
    @JvmField
    protected val responseParser: SafeJsonParser<T>
): FeedRepository {

    /** Save number of latest loaded page of the feed. */
    protected abstract fun onPageLoaded(page: Int)

    @JvmField
    protected var feedItems = TreeSet<FeedItem>()

    override fun loadLatestPage() {
        publishCachedFeed() // publish cached data to respond immediately and then load
        repositoryEventBus.publish(LoadingEvent.LoadingStarted())
        webService.requestLatestFeed(webResponseHandler)
    }

    private fun publishCachedFeed() {
        if (!feedItems.isEmpty()) {
            repositoryEventBus.publish(LoadingEvent.FeedUpdateReady(getFeedItemsAsList()))
        }
    }

    private fun getFeedItemsAsList(): List<FeedItem> {
        return ArrayList(feedItems.descendingSet())
    }

    protected val webResponseHandler: FeedWebService.FeedRequestCallback = object : FeedWebService.FeedRequestCallback {

        override fun onFailure(url: URL, requestedPage: Int, responseCode: Int, connectionError: IOException?) {
            repositoryEventBus.publish(LoadingEvent.LoadingError())
            repositoryEventBus.publish(LoadingEvent.LoadingFinished())
        }

        override fun onSuccess(url: URL, requestedPage: Int, responseCode: Int, data: String?) {
            if (requestedPage >= 0) {
                onPageLoaded(requestedPage)
            }

            val pageItems = parse(data)
            if (!pageItems.isEmpty()) {
                feedItems.addAll(pageItems)

                repositoryEventBus.publish(LoadingEvent.FeedUpdateReady(getFeedItemsAsList()))
            }
            repositoryEventBus.publish(LoadingEvent.LoadingFinished())
        }
    }

    private fun parse(response: String?): List<FeedItem> {
        val feedData = responseParser.parse(response)
        return feedConverter.convertFeed(feedData)
    }

}
