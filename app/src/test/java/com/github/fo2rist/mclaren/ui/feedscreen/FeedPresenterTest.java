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
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class FeedPresenterTest {
    private FeedPresenter presenter;
    private FeedContract.View mockView;
    private FeedRepository mockRepository;
    private FeedRepositoryEventBus mockEventBus;
    private EventsLogger mockEventsLogger;

    @Before
    public void setUp() throws Exception {
        mockView = mock(FeedContract.View.class);
        mockRepository = mock(FeedRepository.class);
        mockEventBus = mock(FeedRepositoryEventBus.class);
        mockEventsLogger = mock(EventsLogger.class);
        presenter = new FeedPresenter(mockRepository, mockEventBus, mockEventsLogger);
    }

    @Test
    public void test_onStart_loadFeed_and_subscribeOnEvents() throws Exception {
        presenter.onStart(mockView);

        verify(mockEventBus).subscribe(any());
        verify(mockRepository).loadLatest();
    }

    @Test
    public void test_onStop_unsubscribeFromEvents() throws Exception {
        setUpPresenter();

        presenter.onStop();

        verify(mockEventBus).unsubscribe(any());
    }

    private void setUpPresenter() {
        presenter.onStart(mockView);
        reset(mockView);
        reset(mockEventBus);
        reset(mockRepository);
        reset(mockEventsLogger);
    }

    @Test
    public void test_onFeedUpdateReceived_setFeedToView() throws Exception {
        setUpPresenter();

        presenter.onFeedUpdateReceived(new LoadingEvent.FeedUpdateReady(new ArrayList<FeedItem>()));

        verify(mockView).displayFeed(any(List.class));
    }

    @Test
    public void test_onLoadingStarted_showProgress() throws Exception {
        setUpPresenter();

        presenter.onLoadingStarted(new LoadingEvent.LoadingStarted());

        verify(mockView).showProgress();
    }

    @Test
    public void test_onLoadingFinished_hideProgress() throws Exception {
        setUpPresenter();

        presenter.onLoadingFinished(new LoadingEvent.LoadingFinished());

        verify(mockView).hideProgress();
    }

    @Test
    public void test_onItemClicked_forVideo_openBrowser() {
        setUpPresenter();

        presenter.onItemClicked(FeedItems.VIDEO_ITEM);

        verifyNavigatedToPreview(FeedItems.MEDIA_LINK);
    }

    @Test
    public void test_onItemClick_forArticle_openPreview() {
        setUpPresenter();

        presenter.onItemClicked(FeedItems.MCLAREN_ARTICLE_ITEM);

        verifyNavigatedToPreview(FeedItems.MCLAREN_ARTICLE_ITEM);
    }

    @Test
    public void test_onItemClick_forGallery_openPreview() {
        setUpPresenter();

        presenter.onItemClicked(FeedItems.INSTAGRAM_GALLERY_ITEM);

        verifyNavigatedToPreview(FeedItems.INSTAGRAM_GALLERY_ITEM);
    }

    @Test
    public void test_onItemSourceClicked_openBrowser() {
        setUpPresenter();

        presenter.onItemSourceClicked(FeedItems.INSTAGRAM_GALLERY_ITEM);

        verifyNavigateToBrowser();
    }

    @Test
    public void test_onLinkClicked_openBrowser() {
        setUpPresenter();

        presenter.onLinkClicked(FeedItems.INSTAGRAM_GALLERY_ITEM,
                FeedItems.INSTAGRAM_GALLERY_ITEM.getEmbeddedMediaLink());

        verifyNavigateToBrowser();
    }

    private void verifyNavigatedToPreview(String link) {
        verify(mockView).navigateToPreview(link);
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }

    private void verifyNavigatedToPreview(FeedItem feedItem) {
        verify(mockView).navigateToPreview(feedItem);
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }

    private void verifyNavigateToBrowser() {
        verify(mockView).navigateToBrowser(anyString());
        verify(mockEventsLogger).logViewEvent(any(Events.class), anyString());
    }
}
