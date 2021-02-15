package com.github.fo2rist.mclaren.ui.feedscreen;

import android.os.Bundle;

import static com.github.fo2rist.mclaren.models.FeedAccounts.STORIES_FEED_ACCOUNT_NAME;

/**
 * Shows feed from StoryStream API.
 * @see BaseFeedFragment
 */
public class StoriesFeedFragment extends BaseFeedFragment {

    public static StoriesFeedFragment newInstance() {
        StoriesFeedFragment fragment = new StoriesFeedFragment();
        Bundle arguments = new Bundle();
        arguments.putString("account", STORIES_FEED_ACCOUNT_NAME);
        fragment.setArguments(arguments);
        return fragment;
    }
}
