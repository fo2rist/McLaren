package com.github.fo2rist.mclaren.ui;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.mvp.NewsfeedContract;
import java.util.List;

public class StoriesFragment extends NewsfeedFragment
        implements NewsfeedContract.View {

    public static StoriesFragment newInstance() {
        return new StoriesFragment();
    }

    public StoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void setFeed(List<FeedItem> feedItems) {

    }

    @Override
    public void hideProgress() {

    }
}
