package com.github.fo2rist.mclaren.ui.presenters;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.mvp.NewsfeedContract;
import com.github.fo2rist.mclaren.repository.FeedRepository;
import java.util.List;
import javax.inject.Inject;


public class NewsfeedPresenter implements NewsfeedContract.Presenter, FeedRepository.Listener {
    private NewsfeedContract.View view;
    @Inject
    FeedRepository feedRepository;

    @Inject
    NewsfeedPresenter() {
    }

    @Override
    public void onStart(NewsfeedContract.View view) {
        this.view = view;
        this.feedRepository.subscribe(this);

        loadFeed();
    }

    @Override
    public void onRefreshRequested() {
        loadFeed();
    }

    private void loadFeed() {
        this.feedRepository.load();
    }

    @Override
    public void onGetFeed(List<FeedItem> feed) {
        view.setFeed(feed);
    }
}
