package com.github.fo2rist.mclaren.ui.feedscreen;

import android.os.Bundle;

import static com.github.fo2rist.mclaren.models.FeedAccounts.MCLAREN_FEED_ACCOUNT_NAME;

/**
 * Shows feed from McLaren old feed API.
 * @see BaseFeedFragment
 * @deprecated not supported by McLaren anymore
 */
public class McLarenFeedFragment extends BaseFeedFragment {

    public static McLarenFeedFragment newInstance() {
        McLarenFeedFragment fragment = new McLarenFeedFragment();
        Bundle arguments = new Bundle();
        arguments.putString("account", MCLAREN_FEED_ACCOUNT_NAME);
        fragment.setArguments(arguments);
        return fragment;
    }
}
