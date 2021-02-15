package com.github.fo2rist.mclaren.ui.feedscreen

import android.os.Bundle
import com.github.fo2rist.mclaren.mvp.FeedContract

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
}
