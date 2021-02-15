package com.github.fo2rist.mclaren.ui.feedscreen

import android.os.Bundle
import com.github.fo2rist.mclaren.models.FeedAccounts.STORIES_FEED_ACCOUNT_NAME

/**
 * Shows feed from StoryStream API.
 * @see BaseFeedFragment
 */
class StoriesFeedFragment : BaseFeedFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = StoriesFeedFragment().apply {
            arguments = Bundle().apply { putString("account", STORIES_FEED_ACCOUNT_NAME) }
        }
    }
}
