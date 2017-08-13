package com.github.fo2rist.mclaren.ui;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.mvp.NewsfeedContract;
import com.github.fo2rist.mclaren.repository.FeedRepository;
import com.github.fo2rist.mclaren.repository.McLarenFeedRepository;
import java.util.List;
import javax.inject.Inject;


public class NewsfeedPresenter implements NewsfeedContract.Presenter, FeedRepository.Listener {
    private NewsfeedContract.View view;
    @Inject
    FeedRepository feedRepository;

    @Override
    public void onStart(NewsfeedContract.View view) {
        feedRepository = new McLarenFeedRepository(); //TODO inject
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
