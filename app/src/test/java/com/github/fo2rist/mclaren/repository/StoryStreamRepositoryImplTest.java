package com.github.fo2rist.mclaren.repository;

import com.github.fo2rist.mclaren.repository.FeedRepositoryEventBus.LoadingEvent;
import com.github.fo2rist.mclaren.web.FeedWebService.FeedRequestCallback;
import com.github.fo2rist.mclaren.web.StoryStreamWebService;
import java.net.URL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.github.fo2rist.mclaren.testdata.StoryStreamResponse.REAL_FEED_RESPONSE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21)
public class StoryStreamRepositoryImplTest {
    //TODO remove tests duplications with MCL Repo test. 2018.11.25

    private FeedRepository repository;
    private StoryStreamWebService mockWebService;
    private FeedRepositoryEventBus mockEventBus;

    @Before
    public void setUp() {
        mockWebService = mock(StoryStreamWebService.class);
        mockEventBus = mock(FeedRepositoryEventBus.class);

        repository = new StoryStreamRepositoryImpl(mockWebService, mockEventBus);
    }

    @Test
    public void test_loadLatest_startLoading_and_firesLoadStartEvent() {
        repository.loadLatestPage();

        verify(mockEventBus).publish(any(LoadingEvent.LoadingStarted.class));
        verify(mockWebService).requestLatestFeed(any(FeedRequestCallback.class));
    }

    @Test
    public void test_loadNextHistory_startLoading_and_firesLoadStartEvent() {
        repository.loadNextPage();

        verify(mockEventBus).publish(any(LoadingEvent.LoadingStarted.class));
        verify(mockWebService).requestFeedPage(anyInt(), any(FeedRequestCallback.class));
    }

    @Test
    public void test_onSuccess_firesLoadFinishSuccessfullyEvents() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                FeedRequestCallback callback = invocationOnMock.getArgument(0);
                callback.onSuccess(new URL("http://empty.url"), 0, 200, REAL_FEED_RESPONSE);
                return null;
            }
        }).when(mockWebService).requestLatestFeed(any(FeedRequestCallback.class));

        repository.loadLatestPage();

        verify(mockEventBus).publish(any(LoadingEvent.LoadingFinished.class));
        verify(mockEventBus).publish(any(LoadingEvent.FeedUpdateReady.class));
    }

    @Test
    public void test_onFailure_firesLoadFinishWithErrorEvents() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                FeedRequestCallback callback = invocationOnMock.getArgument(0);
                callback.onFailure(new URL("http://empty.url"), 0, 400, null);
                return null;
            }
        }).when(mockWebService).requestLatestFeed(any(FeedRequestCallback.class));

        repository.loadLatestPage();

        verify(mockEventBus).publish(any(LoadingEvent.LoadingFinished.class));
        verify(mockEventBus).publish(any(LoadingEvent.LoadingError.class));
    }
}
