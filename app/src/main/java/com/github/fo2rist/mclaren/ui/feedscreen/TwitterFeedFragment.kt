package com.github.fo2rist.mclaren.ui.feedscreen

import com.github.fo2rist.mclaren.mvp.FeedContract
import com.github.fo2rist.mclaren.repository.feed.FeedRepositoryEventBus
import javax.inject.Inject

/**
 * Shows Twitter timeline as feed.
 */
class TwitterFeedFragment : BaseFeedFragment(), FeedContract.View {

    companion object {
        @JvmStatic
        fun newInstance() = TwitterFeedFragment()
    }

    @Inject
    protected lateinit var repositoryEventBus: FeedRepositoryEventBus
}
