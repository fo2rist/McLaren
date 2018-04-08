package com.github.fo2rist.mclaren.ui.feedscreen;

import com.github.fo2rist.mclaren.analytics.Events;
import com.github.fo2rist.mclaren.analytics.EventsLogger;
import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.mvp.FeedContract;
import com.github.fo2rist.mclaren.repository.FeedRepository;
import com.github.fo2rist.mclaren.repository.FeedRepositoryPubSub;
import com.github.fo2rist.mclaren.repository.FeedRepositoryPubSub.PubSubEvent;
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
    private FeedRepositoryPubSub mockPubSub;
    private EventsLogger mockEventsLogger;

    @Before
    public void setUp() throws Exception {
        mockView = mock(FeedContract.View.class);
        mockRepository = mock(FeedRepository.class);
        mockPubSub = mock(FeedRepositoryPubSub.class);
        mockEventsLogger = mock(EventsLogger.class);
        presenter = new FeedPresenter(mockRepository, mockPubSub, mockEventsLogger);
    }

    @Test
    public void test_onStart_loadFeed_and_subscribeOnEvents() throws Exception {
        presenter.onStart(mockView);

        verify(mockPubSub).subscribe(any());
        verify(mockRepository).loadLatest();
    }

    @Test
    public void test_onStop_unsubscribeFromEvents() throws Exception {
        setUpPresenter();

        presenter.onStop();

        verify(mockPubSub).unsubscribe(any());
    }

    private void setUpPresenter() {
        presenter.onStart(mockView);
        reset(mockView);
        reset(mockPubSub);
        reset(mockRepository);
        reset(mockEventsLogger);
    }

    @Test
    public void test_onFeedUpdateReceived_setFeedToView() throws Exception {
        setUpPresenter();

        presenter.onFeedUpdateReceived(new PubSubEvent.FeedUpdateReady(new ArrayList<FeedItem>()));

        verify(mockView).displayFeed(any(List.class));
    }

    @Test
    public void test_onLoadingStarted_showProgress() throws Exception {
        setUpPresenter();

        presenter.onLoadingStarted(new PubSubEvent.LoadingStarted());

        verify(mockView).showProgress();
    }

    @Test
    public void test_onLoadingFinished_hideProgress() throws Exception {
        setUpPresenter();

        presenter.onLoadingFinished(new PubSubEvent.LoadingFinished());

        verify(mockView).hideProgress();
    }

    @Test
    public void test_onItemClicked_forVideo_openBrowser() {
        setUpPresenter();

        presenter.onItemClicked(FeedItems.VIDEO_ITEM);

        verifyNavigateToBrowser();
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

        presenter.onLinkClicked(FeedItems.INSTAGRAM_GALLERY_ITEM, FeedItems.INSTAGRAM_GALLERY_ITEM.embeddedMediaLink);

        verifyNavigateToBrowser();
    }

    private void verifyNavigatedToPreview(FeedItem p) {
        verify(mockView).navigateToPreview(p);
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }

    private void verifyNavigateToBrowser() {
        verify(mockView).navigateToBrowser(anyString());
        verify(mockEventsLogger).logViewEvent(any(Events.class), anyString());
    }
}
