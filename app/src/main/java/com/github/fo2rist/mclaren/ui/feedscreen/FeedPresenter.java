package com.github.fo2rist.mclaren.ui.feedscreen;

import android.support.annotation.NonNull;

import com.github.fo2rist.mclaren.analytics.Events;
import com.github.fo2rist.mclaren.analytics.EventsLogger;
import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.mvp.FeedContract;
import com.github.fo2rist.mclaren.repository.FeedRepository;
import com.github.fo2rist.mclaren.repository.FeedRepositoryEventBus;
import com.github.fo2rist.mclaren.repository.FeedRepositoryEventBus.LoadingEvent;
import javax.inject.Inject;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.github.fo2rist.mclaren.utils.LinkUtils.getFeedMentionLink;
import static com.github.fo2rist.mclaren.utils.LinkUtils.getMediaLink;


public class FeedPresenter implements FeedContract.Presenter {
    private FeedContract.View view;
    private FeedRepository feedRepository;
    private FeedRepositoryEventBus repositoryEventBus;
    private EventsLogger eventsLogger;

    @Inject
    public FeedPresenter(FeedRepository feedRepository, FeedRepositoryEventBus repositoryEventBus, EventsLogger logger) {
        this.feedRepository = feedRepository;
        this.repositoryEventBus = repositoryEventBus;
        this.eventsLogger = logger;
    }

    @Override
    public void onStart(@NonNull FeedContract.View view) {
        this.view = view;
        this.repositoryEventBus.subscribe(this);

        loadFeed();
    }

    @Override
    public void onStop() {
        this.repositoryEventBus.unsubscribe(this);
    }

    @Override
    public void onItemClicked(FeedItem item) {
        switch(item.getType()) {
            case Video:
                navigateViewToVideoPreviewScreen(getMediaLink(item));
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
            default:
                throw new IllegalArgumentException("Unsupported item type: " + item.getType());
        }
    }

    @Override
    public void onItemSourceClicked(FeedItem item) {
        navigateViewToBrowser(getFeedMentionLink(item, item.getSourceName()));
    }

    @Override
    public void onLinkClicked(FeedItem item, String link) {
        navigateViewToBrowser(link);
    }

    private void navigateViewToPreviewScreen(FeedItem item, Events previewEventType) {
        view.navigateToPreview(item);
        eventsLogger.logViewEvent(previewEventType);
    }

    private void navigateViewToVideoPreviewScreen(String link) {
        view.navigateToPreview(link);
        eventsLogger.logViewEvent(Events.VIEW_VIDEO);
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
    public void onLoadingStarted(LoadingEvent.LoadingStarted event) {
        view.showProgress();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFeedUpdateReceived(LoadingEvent.FeedUpdateReady event) {
        view.displayFeed(event.getFeed());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadingFinished(LoadingEvent.LoadingFinished event) {
        view.hideProgress();
    }
}
