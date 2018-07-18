package com.github.fo2rist.mclaren.repository;

import com.github.fo2rist.mclaren.repository.FeedRepositoryPubSub.PubSubEvent;
import com.github.fo2rist.mclaren.web.FeedWebService.FeedRequestCallback;
import com.github.fo2rist.mclaren.web.StoryStreamWebService;
import java.net.URL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;

import static com.github.fo2rist.mclaren.testdata.StoryStreamResponse.REAL_FEED_RESPONSE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class StoryStreamRepositoryImplTest {

    private StoryStreamWebService mockWebService;
    private FeedRepositoryPubSub mockPubSub;
    private StoryStreamRepository repository;

    @Before
    public void setUp() throws Exception {
        mockWebService = mock(StoryStreamWebService.class);
        mockPubSub = mock(FeedRepositoryPubSub.class);
        repository = new StoryStreamRepositoryImpl(mockWebService, mockPubSub);
    }

    @Test
    public void test_loadLatest_startLoading_and_firesLoadStartEvent() throws Exception {
        repository.loadLatest();

        verify(mockPubSub).publish(any(PubSubEvent.LoadingStarted.class));
        verify(mockWebService).requestLatestFeed(any(FeedRequestCallback.class));
    }

    @Test
    public void test_loadNextHistory_startLoading_and_firesLoadStartEvent() throws Exception {
        repository.loadNextHistory();

        verify(mockPubSub).publish(any(PubSubEvent.LoadingStarted.class));
        verify(mockWebService).requestFeedPage(anyInt(), any(FeedRequestCallback.class));
    }

    @Test
    public void test_onSuccess_firesLoadFinishEvents() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                FeedRequestCallback callback = invocationOnMock.getArgument(0);
                callback.onSuccess(new URL("http://empty.url"), 0, 200, REAL_FEED_RESPONSE);
                return null;
            }
        }).when(mockWebService).requestLatestFeed(any(FeedRequestCallback.class));

        repository.loadLatest();

        verify(mockPubSub).publish(any(PubSubEvent.LoadingFinished.class));
        verify(mockPubSub).publish(any(PubSubEvent.FeedUpdateReady.class));
    }
}
