package com.github.fo2rist.mclaren.repository.feed

import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.repository.feed.FeedRepositoryEventBus.LoadingEvent
import com.github.fo2rist.mclaren.repository.converters.FeedConverter
import com.github.fo2rist.mclaren.utils.toDescendingList
import com.github.fo2rist.mclaren.web.feed.DEFAULT_PAGE
import com.github.fo2rist.mclaren.web.feed.FeedWebService
import com.github.fo2rist.mclaren.web.utils.SafeJsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.TreeSet

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
 * Descendants to implement [prepareForHistoryLoading], [getNextPageNumber] and [onPageLoaded]
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
) : FeedRepository {

    companion object {
        protected const val UNKNOWN_PAGE = -1
    }

    private val mainScope = CoroutineScope(Dispatchers.Main)

    /**
     * Save number of latest loaded page of the feed.
     * Will be called once loading of given page is done.
     */
    protected abstract fun onPageLoaded(page: Int)

    /**
     * Get number of the next page in history.
     * Will be called before every new page request.
     * @return next page number or [UNKNOWN_PAGE] if next page is unknown and should not be loaded.
     */
    protected abstract fun getNextPageNumber(): Int

    @JvmField
    protected var feedItems = TreeSet<FeedItem>()

    override fun loadLatestPage() {
        publishCachedFeed() // publish cached data to respond immediately and then load

        mainScope.launch {
            publishLoadingStarted()
            try {
                val feed = webService.requestLatestFeed()
                updateDataAndPublishLoadingSuccess(DEFAULT_PAGE, feed)
            } catch (exc: IOException) {
                publishLoadingFailure()
            }
            publishLoadingFinished()
        }
    }

    override fun loadNextPage() {
        val pageToLoad = getNextPageNumber()
        if (pageToLoad == UNKNOWN_PAGE) {
            return
        }

        mainScope.launch {
            publishLoadingStarted()
            try {
                val feed = webService.requestFeedPage(pageToLoad)
                updateDataAndPublishLoadingSuccess(pageToLoad, feed)
            } catch (exc: IOException) {
                publishLoadingFailure()
            }
            publishLoadingFinished()
        }
    }

    private fun publishLoadingStarted() {
        repositoryEventBus.publish(LoadingEvent.LoadingStarted())
    }

    private fun publishCachedFeed() {
        if (!feedItems.isEmpty()) {
            repositoryEventBus.publish(LoadingEvent.FeedUpdateReady(feedItems.toDescendingList()))
        }
    }

    private fun publishLoadingFinished() {
        repositoryEventBus.publish(LoadingEvent.LoadingFinished())
    }

    private fun publishLoadingFailure() {
        repositoryEventBus.publish(LoadingEvent.LoadingError())
    }

    private fun updateDataAndPublishLoadingSuccess(requestedPage: Int, data: String?) {
        if (requestedPage >= 0) {
            onPageLoaded(requestedPage)
        }

        val pageItems = parse(data)
        if (!pageItems.isEmpty()) {
            feedItems.addAll(pageItems)

            repositoryEventBus.publish(LoadingEvent.FeedUpdateReady(feedItems.toDescendingList()))
        }
    }

    private fun parse(response: String?): List<FeedItem> {
        val feedData = responseParser.parse(response)
        return feedConverter.convertFeed(feedData)
    }
}
