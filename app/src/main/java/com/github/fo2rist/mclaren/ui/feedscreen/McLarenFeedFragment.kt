package com.github.fo2rist.mclaren.ui.feedscreen

import android.os.Bundle
import com.github.fo2rist.mclaren.models.FeedAccounts.MCLAREN_FEED_ACCOUNT_NAME

/**
 * Shows feed from McLaren old feed API.
 * @see BaseFeedFragment
 */
@Deprecated("not supported by McLaren anymore")
class McLarenFeedFragment : BaseFeedFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = McLarenFeedFragment().apply {
            arguments = Bundle().apply { putString("account", MCLAREN_FEED_ACCOUNT_NAME) }
        }
    }
}
