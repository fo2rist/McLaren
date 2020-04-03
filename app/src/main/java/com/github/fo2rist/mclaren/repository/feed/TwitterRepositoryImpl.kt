package com.github.fo2rist.mclaren.repository.feed

import android.support.annotation.VisibleForTesting
import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.models.TwitterAccounts.TWITTER_MCLAREN_F1
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
import javax.inject.Inject

import twitter4j.Twitter;
import twitter4j.TwitterException
import java.util.TreeSet


private const val DEFAULT_PAGE_SIZE = 40

class TwitterRepositoryImpl @Inject constructor(
    private val repositoryEventBus: FeedRepositoryEventBus,
    private val twitterBuilder: TwitterWebServiceBuilder,
    private val twitterConverter: FeedConverter<ResponseList<Status>>
) : FeedRepository {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val twitterService: Twitter by lazy { twitterBuilder.getInstance() }

    private var lastLoadedPage = 1
    private var cachedTweets = TreeSet<FeedItem>()

    override fun prepareForHistoryLoading() {
        //no preparation needed for Twitter
    }

    override fun loadNextPage() {
        ioScope.launch{ loadAndNotify(lastLoadedPage + 1) }
    }

    override fun loadLatestPage() {
        ioScope.launch { loadAndNotify(1) }
    }

    @VisibleForTesting
    suspend fun loadAndNotify(page: Int) {
        repositoryEventBus.publish(LoadingEvent.LoadingStarted())

        try {
            val newTweets =loadTweets(page)
            cachedTweets.addAll(newTweets)
            repositoryEventBus.publish(LoadingEvent.FeedUpdateReady(cachedTweets.toDescendingList()))
        } catch (exc: TwitterException) {
            repositoryEventBus.publish(LoadingEvent.LoadingError())
        }

        repositoryEventBus.publish(LoadingEvent.LoadingFinished())
    }

    /**
     * @throws TwitterException
     */
    private suspend fun loadTweets(page: Int): List<FeedItem> {
        val rawTweets = twitterService.getUserTimeline(TWITTER_MCLAREN_F1, Paging(page, DEFAULT_PAGE_SIZE))
        if (page > lastLoadedPage) {
            lastLoadedPage = page
        }
        return twitterConverter.convertFeed(rawTweets)
    }
}
