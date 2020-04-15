package com.github.fo2rist.mclaren.ui.feedscreen

import android.os.Bundle
import com.github.fo2rist.mclaren.mvp.FeedContract
import com.github.fo2rist.mclaren.repository.feed.FeedRepositoryEventBus
import javax.inject.Inject

/**
 * Shows Twitter timeline as feed.
 */
class TwitterFeedFragment : BaseFeedFragment(), FeedContract.View {

    companion object {
        @JvmStatic
        fun newInstance(account: String) = TwitterFeedFragment().apply {
            arguments = Bundle().apply { putString("account", account) }
        }
    }

    @Inject
    protected lateinit var repositoryEventBus: FeedRepositoryEventBus
}
