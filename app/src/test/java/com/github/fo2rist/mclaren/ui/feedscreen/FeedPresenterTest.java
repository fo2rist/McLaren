package com.github.fo2rist.mclaren.ui.feedscreen;

import com.github.fo2rist.mclaren.analytics.Events;
import com.github.fo2rist.mclaren.analytics.EventsLogger;
import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.mvp.FeedContract;
import com.github.fo2rist.mclaren.repository.FeedRepository;
import com.github.fo2rist.mclaren.repository.FeedRepositoryEventBus;
import com.github.fo2rist.mclaren.repository.FeedRepositoryEventBus.LoadingEvent;
import com.github.fo2rist.mclaren.testdata.FeedItems;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class FeedPresenterTest {
    private FeedPresenter presenter;
    private FeedContract.View mockView;
    private FeedRepository mockRepository;
    private FeedRepositoryEventBus mockEventBus;
    private EventsLogger mockEventsLogger;

    @Before
    public void setUp() {
        mockView = mock(FeedContract.View.class);
        mockRepository = mock(FeedRepository.class);
        mockEventBus = mock(FeedRepositoryEventBus.class);
        mockEventsLogger = mock(EventsLogger.class);
        presenter = new FeedPresenter(mockView, mockRepository, mockEventBus, mockEventsLogger);
    }

    @Test
    public void test_onStart_loadFeed_and_subscribeOnEvents() {
        presenter.onStart();

        verify(mockEventBus).subscribe(any());
        verify(mockRepository).loadLatestPage();
    }

    @Test
    public void test_onStop_unsubscribeFromEvents() {
        presenter.onStop();

        verify(mockEventBus).unsubscribe(any());
    }

    @Test
    public void test_onFeedUpdateReceived_setFeedToView() {
        presenter.onFeedUpdateReceived(new LoadingEvent.FeedUpdateReady(new ArrayList<FeedItem>()));

        verify(mockView).displayFeed(any(List.class));
    }

    @Test
    public void test_onLoadingStarted_showProgress() {
        presenter.onLoadingStarted(new LoadingEvent.LoadingStarted());

        verify(mockView).showProgress();
    }

    @Test
    public void test_onLoadingFinished_hideProgress() {
        presenter.onLoadingFinished(new LoadingEvent.LoadingFinished());

        verify(mockView).hideProgress();
    }

    @Test
    public void test_onItemClicked_forVideo_openBrowser() {
        presenter.onItemClicked(FeedItems.VIDEO_ITEM);

        verifyNavigatedToLinkPreview(FeedItems.VIDEO_ITEM.getEmbeddedMediaLink());
    }

    private void verifyNavigatedToLinkPreview(String link) {
        verify(mockView).navigateToPreview(link);
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }

    @Test
    public void test_onItemClick_forArticle_openPreview() {
        presenter.onItemClicked(FeedItems.ARTICLE_ITEM_WITH_LINKS);

        verifyNavigatedToItemPreview(FeedItems.ARTICLE_ITEM_WITH_LINKS);
    }

    @Test
    public void test_onItemClick_forGallery_openPreview() {
        presenter.onItemClicked(FeedItems.INSTAGRAM_GALLERY_ITEM);

        verifyNavigatedToItemPreview(FeedItems.INSTAGRAM_GALLERY_ITEM);
    }

    private void verifyNavigatedToItemPreview(FeedItem feedItem) {
        verify(mockView).navigateToPreview(feedItem);
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }

    @Test
    public void test_onItemSourceClicked_openBrowser() {
        presenter.onItemSourceClicked(FeedItems.INSTAGRAM_GALLERY_ITEM);

        verifyNavigatedToBrowser();
    }

    @Test
    public void test_onLinkClicked_openBrowser() {
        presenter.onLinkClicked(FeedItems.INSTAGRAM_GALLERY_ITEM,
                FeedItems.INSTAGRAM_GALLERY_ITEM.getEmbeddedMediaLink());

        verifyNavigatedToBrowser();
    }

    private void verifyNavigatedToBrowser() {
        verify(mockView).navigateToBrowser(anyString());
        verify(mockEventsLogger).logViewEvent(any(Events.class), anyString());
    }

    @Test
    public void test_onRefreshRequested_loadsFeed() {
        presenter.onRefreshRequested();

        verify(mockRepository).loadLatestPage();
    }

    @Test
    public void test_onScrolledToSecondThird_preFetchesHistory() {
        presenter.onScrolledToSecondThird();

        verify(mockRepository).prepareForHistoryLoading();
    }

    @Test
    public void test_onScrolledToBottom_loadsHistory() {
        presenter.onScrolledToBottom();

        verify(mockRepository).loadNextPage();
    }
}
