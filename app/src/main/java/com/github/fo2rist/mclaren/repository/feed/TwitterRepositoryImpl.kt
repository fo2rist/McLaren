package com.github.fo2rist.mclaren.repository.feed

import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.models.ImageUrl
import com.github.fo2rist.mclaren.models.Size
import com.github.fo2rist.mclaren.repository.feed.FeedRepositoryEventBus.LoadingEvent
import com.github.fo2rist.mclaren.repository.feed.FeedRepository
import com.github.fo2rist.mclaren.repository.feed.FeedRepositoryEventBus
import com.github.fo2rist.mclaren.utils.toDescendingList
import kotlinx.coroutines.launch
import twitter4j.Paging
import javax.inject.Inject

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder
import java.util.TreeSet


class TwitterRepositoryImpl @Inject constructor(
    private val repositoryEventBus: FeedRepositoryEventBus
) : FeedRepository {
    override fun prepareForHistoryLoading() {
    }

    override fun loadNextPage() {
    }

    override fun loadLatestPage() {
    }


}
