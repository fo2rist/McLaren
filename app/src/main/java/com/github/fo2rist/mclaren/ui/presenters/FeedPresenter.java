package com.github.fo2rist.mclaren.ui.presenters;

import com.github.fo2rist.mclaren.analytics.Events;
import com.github.fo2rist.mclaren.analytics.EventsLogger;
import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.mvp.FeedContract;
import com.github.fo2rist.mclaren.repository.FeedRepository;
import com.github.fo2rist.mclaren.repository.FeedRepositoryPubSub;
import com.github.fo2rist.mclaren.repository.PubSubEvents;
import javax.inject.Inject;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.github.fo2rist.mclaren.utils.LinkUtils.getFeedMentionLink;
import static com.github.fo2rist.mclaren.utils.LinkUtils.getMediaLink;


public class FeedPresenter implements FeedContract.Presenter {
    private FeedContract.View view;
    private FeedRepository feedRepository;
    private FeedRepositoryPubSub repositoryPubSub;
    private EventsLogger eventsLogger;

    @Inject
    public FeedPresenter(FeedRepository feedRepository, FeedRepositoryPubSub repositoryPubSub, EventsLogger logger) {
        this.feedRepository = feedRepository;
        this.repositoryPubSub = repositoryPubSub;
        this.eventsLogger = logger;
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
    public void onItemClicked(FeedItem item) {
        switch(item.type) {
            case Video:
                //TODO just play video like all other media types
                navigateViewToBrowser(getMediaLink(item));
                break;
            case Image:
            case Gallery:
                navigateViewToPreviewScreen(item, Events.VIEW_IMAGES);
                break;
            case Article:
                navigateViewToPreviewScreen(item, Events.VIEW_ARTICLE);
                break;
            case Message:
                break;
        }
    }

    @Override
    public void onItemSourceClicked(FeedItem item) {
        navigateViewToBrowser(getFeedMentionLink(item, item.sourceName));
    }

    @Override
    public void onLinkClicked(FeedItem item, String link) {
        navigateViewToBrowser(link);
    }

    private void navigateViewToPreviewScreen(FeedItem item, Events previewEventType) {
        view.navigateToPreview(item);
        eventsLogger.logViewEvent(previewEventType);
    }

    private void navigateViewToBrowser(String link) {
        view.navigateToBrowser(link);
        eventsLogger.logViewEvent(Events.VIEW_EXTERNAL, link);
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
        this.feedRepository.prepareForHistoryLoading();
    }

    @Override
    public void onScrolledToBottom() {
        this.feedRepository.loadNextHistory();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadingStarted(PubSubEvents.LoadingStarted event) {
        view.showProgress();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFeedUpdateReceived(PubSubEvents.FeedUpdateReady event) {
        view.setFeed(event.feed);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadingFinished(PubSubEvents.LoadingFinished event) {
        view.hideProgress();
    }
}
