package com.github.fo2rist.mclaren.ui.presenters;

import com.github.fo2rist.mclaren.mvp.NewsfeedContract;
import com.github.fo2rist.mclaren.repository.FeedRepository;
import com.github.fo2rist.mclaren.repository.FeedRepositoryPubSub;
import com.github.fo2rist.mclaren.repository.PubSubEvents;
import javax.inject.Inject;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class NewsfeedPresenter implements NewsfeedContract.Presenter {
    private NewsfeedContract.View view;
    @Inject
    FeedRepository feedRepository;
    @Inject
    FeedRepositoryPubSub repositoryPubSub;

    @Inject
    NewsfeedPresenter() {
    }

    @Override
    public void onStart(NewsfeedContract.View view) {
        this.view = view;
        this.repositoryPubSub.subscribe(this);

        loadFeed();
    }

    @Override
    public void onStop() {
        this.repositoryPubSub.unsubscribe(this);
    }

    @Override
    public void onRefreshRequested() {
        loadFeed();
    }

    private void loadFeed() {
        this.feedRepository.loadLatest();
    }

    @Override
    public void onLoadMoreRequested() {
        this.feedRepository.loadPrevious();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFeedUpdateReceived(PubSubEvents.FeedUpdateReady event) {
        view.hideProgress();
        view.setFeed(event.feed);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadingFinished(PubSubEvents.LoadingFinished event) {
        view.hideProgress();
    }
}
