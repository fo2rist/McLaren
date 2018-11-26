package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.repository.FeedRepositoryEventBus.LoadingEvent
import com.github.fo2rist.mclaren.web.FeedWebService
import com.github.fo2rist.mclaren.web.SafeJsonParser
import java.io.IOException
import java.net.URL
import java.util.*

/**
 * Supplier of the news feed.
 */
interface FeedRepository {
    /** Request the latest entries from feed.  */
    fun loadLatest()

    /** Warm up repository to load the next page from history.  */
    fun prepareForHistoryLoading()

    /** Requests the next portion of not loaded yet items in feed.  */
    fun loadNextHistory()
}

/**
 * Base implementation of news feed repo.
 * Takes care about storing feed, caching it and requesting feed from web-server.
 */
abstract class BaseFeedRepository<T>(
    @JvmField
    protected val webService: FeedWebService,
    @JvmField
    protected val repositoryEventBus: FeedRepositoryEventBus,
    @JvmField
    protected val responseParser: SafeJsonParser<T>
): FeedRepository {

    /** Convert raw data into the Feed model. */
    protected abstract fun parse(data: String?): List<FeedItem>

    /** Save number of latest loaded page of the feed. */
    protected abstract fun setLastLoadedPage(page: Int)

    @JvmField
    protected var feedItems = TreeSet<FeedItem>()

    override fun loadLatest() {
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
                setLastLoadedPage(requestedPage)
            }

            val feedItems = parse(data)
            if (!feedItems.isEmpty()) {
                addNewItems(feedItems)

                repositoryEventBus.publish(LoadingEvent.FeedUpdateReady(getFeedItemsAsList()))
            }
            repositoryEventBus.publish(LoadingEvent.LoadingFinished())
        }
    }


    protected fun addNewItems(itemsPortion: List<FeedItem>) {
        feedItems.addAll(itemsPortion)
    }
}
