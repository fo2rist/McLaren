package com.github.fo2rist.mclaren.ui.presenters;

import com.github.fo2rist.mclaren.mvp.FeedContract;
import com.github.fo2rist.mclaren.repository.FeedRepository;
import com.github.fo2rist.mclaren.repository.FeedRepositoryPubSub;
import com.github.fo2rist.mclaren.repository.PubSubEvents;
import javax.inject.Inject;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class FeedPresenter implements FeedContract.Presenter {
    private FeedContract.View view;
    private FeedRepository feedRepository;
    private FeedRepositoryPubSub repositoryPubSub;

    @Inject
    public FeedPresenter(FeedRepository feedRepository, FeedRepositoryPubSub repositoryPubSub) {
        this.feedRepository = feedRepository;
        this.repositoryPubSub = repositoryPubSub;
    }

    @Override
    public void onStart(FeedContract.View view) {
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
    public void onScrolledToSecondThird() {
        this.feedRepository.prepareForLoading();
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
