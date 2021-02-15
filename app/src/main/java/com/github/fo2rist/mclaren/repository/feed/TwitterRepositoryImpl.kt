package com.github.fo2rist.mclaren.repository.feed

import androidx.annotation.VisibleForTesting
import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.repository.converters.FeedConverter
import com.github.fo2rist.mclaren.repository.feed.FeedRepositoryEventBus.LoadingEvent
import com.github.fo2rist.mclaren.utils.toDescendingList
import com.github.fo2rist.mclaren.web.feed.TwitterWebServiceBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import twitter4j.Paging
import twitter4j.ResponseList
import twitter4j.Status
import twitter4j.Twitter
import twitter4j.TwitterException
import java.util.TreeSet
import javax.inject.Inject


private const val DEFAULT_PAGE_SIZE = 40

internal class TwitterRepositoryImpl @Inject constructor(
    private val repositoryEventBus: FeedRepositoryEventBus,
    private val twitterBuilder: TwitterWebServiceBuilder,
    private val twitterConverter: FeedConverter<ResponseList<Status>>
) : FeedRepository {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val twitterService: Twitter by lazy { twitterBuilder.getInstance() }

    private var lastLoadedPage = mutableMapOf<String, Int>()
    private var cachedTweets = mutableMapOf<String, TreeSet<FeedItem>>()

    override fun prepareForHistoryLoading() {
        //no preparation needed for Twitter
    }

    override fun loadNextPage(account: String) {
        ioScope.launch { loadAndNotify(account, lastLoadedPage.getOrCreate(account, 1) + 1) }
    }

    override fun loadLatestPage(account: String) {
        ioScope.launch { loadAndNotify(account, lastLoadedPage.getOrCreate(account, 1)) }
    }

    @VisibleForTesting
    suspend fun loadAndNotify(account: String, page: Int) {
        repositoryEventBus.publish(LoadingEvent.LoadingStarted(account))

        try {
            val newTweets = loadTweets(account, page)
            val accountTweets = cachedTweets.getOrCreate(account, TreeSet())
            accountTweets.addAll(newTweets)
            repositoryEventBus.publish(LoadingEvent.FeedUpdateReady(account, accountTweets.toDescendingList()))
        } catch (exc: TwitterException) {
            repositoryEventBus.publish(LoadingEvent.LoadingError(account))
        }

        repositoryEventBus.publish(LoadingEvent.LoadingFinished(account))
    }

    /**
     * @throws TwitterException
     */
    private suspend fun loadTweets(account: String, page: Int): List<FeedItem> {
        val rawTweets = twitterService.getUserTimeline(account, Paging(page, DEFAULT_PAGE_SIZE))
        if (page > lastLoadedPage.getOrCreate(account, 1)) {
            lastLoadedPage[account] = page
        }
        return twitterConverter.convertFeed(rawTweets)
    }
}

/**
 * Implementation of kotlin's new getOrDefault helper.
 * Can be removed once migrated Android API 24+.
 */
private fun <K, V> MutableMap<K, V>.getOrCreate(key: K, default: V): V {
    if (!this.containsKey(key)) {
        this[key] = default
    }
    return this[key]!!
}
