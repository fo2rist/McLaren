package com.github.fo2rist.mclaren.repository.feed

import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.models.ImageUrl
import com.github.fo2rist.mclaren.models.Size
import com.github.fo2rist.mclaren.repository.converters.TwitterConverter
import com.github.fo2rist.mclaren.repository.feed.FeedRepositoryEventBus.LoadingEvent
import com.github.fo2rist.mclaren.utils.toDescendingList
import com.github.fo2rist.mclaren.web.feed.TwitterWebServiceBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import twitter4j.Paging
import twitter4j.ResponseList
import twitter4j.Status
import javax.inject.Inject

import twitter4j.Twitter;
import java.util.TreeSet


class TwitterRepositoryImpl @Inject constructor(
    private val repositoryEventBus: FeedRepositoryEventBus,
    private val twitterBuilder: TwitterWebServiceBuilder,
    private val twitterConverter: TwitterConverter
) : FeedRepository {

    override fun prepareForHistoryLoading() {
        //no preparation needed for Twitter
    }

    override fun loadNextPage() {
    }

    override fun loadLatestPage() {
    }


}
