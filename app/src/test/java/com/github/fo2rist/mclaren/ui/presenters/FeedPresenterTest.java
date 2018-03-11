package com.github.fo2rist.mclaren.ui.presenters;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.mvp.FeedContract;
import com.github.fo2rist.mclaren.repository.FeedRepository;
import com.github.fo2rist.mclaren.repository.FeedRepositoryPubSub;
import com.github.fo2rist.mclaren.repository.PubSubEvents;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class FeedPresenterTest {
    private FeedPresenter presenter;
    private FeedRepository mockRepository = mock(FeedRepository.class);
    private FeedRepositoryPubSub mockPubSub = mock(FeedRepositoryPubSub.class);
    private FeedContract.View mockView = mock(FeedContract.View.class);

    @Before
    public void setUp() throws Exception {
        reset(mockPubSub);
        reset(mockRepository);
        presenter = new FeedPresenter(mockRepository, mockPubSub);
    }

    @Test
    public void test_onStart_loadFeed_and_subscribeOnEvents() throws Exception {
        presenter.onStart(mockView);

        verify(mockPubSub).subscribe(any());
        verify(mockRepository).loadLatest();
    }

    @Test
    public void test_onStop_unsubscribeFromEvents() throws Exception {
        presenter.onStart(mockView);

        presenter.onStop();

        verify(mockPubSub).unsubscribe(any());
    }

    @Test
    public void test_onFeedUpdateReceived_setFeedToView() throws Exception {
        presenter.onStart(mockView);
        reset(mockView);

        presenter.onFeedUpdateReceived(new PubSubEvents.FeedUpdateReady(new ArrayList<FeedItem>()));

        verify(mockView).setFeed(any(List.class));
    }

    @Test
    public void test_onLoadingStarted_showProgress() throws Exception {
        presenter.onStart(mockView);
        reset(mockView);

        presenter.onLoadingStarted(new PubSubEvents.LoadingStarted());

        verify(mockView).showProgress();
    }

    @Test
    public void test_onLoadingFinished_hideProgress() throws Exception {
        presenter.onStart(mockView);
        reset(mockView);

        presenter.onLoadingFinished(new PubSubEvents.LoadingFinished());

        verify(mockView).hideProgress();
    }
}
