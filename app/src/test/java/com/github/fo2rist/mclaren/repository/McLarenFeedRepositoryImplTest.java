package com.github.fo2rist.mclaren.repository;

import com.github.fo2rist.mclaren.repository.FeedRepositoryEventBus.LoadingEvent;
import com.github.fo2rist.mclaren.web.FeedHistoryPredictor;
import com.github.fo2rist.mclaren.web.FeedWebService.FeedRequestCallback;
import com.github.fo2rist.mclaren.web.McLarenFeedWebService;
import java.net.URL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.github.fo2rist.mclaren.testdata.McLarenFeedResponse.REAL_FEED_RESPONSE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21)
public class McLarenFeedRepositoryImplTest {
    //TODO remove tests duplications with StoryStream Repo test. 2018.11.25

    private McLarenFeedRepositoryImpl repository;
    private McLarenFeedWebService mockWebService;
    private FeedRepositoryEventBus mockEventBus;
    private FeedHistoryPredictor mockHistoryPredictor;

    @Before
    public void setUp() {
        mockWebService = mock(McLarenFeedWebService.class);
        mockEventBus = mock(FeedRepositoryEventBus.class);
        mockHistoryPredictor = mock(FeedHistoryPredictor.class);

        repository = new McLarenFeedRepositoryImpl(mockWebService, mockEventBus, mockHistoryPredictor);
    }

    @Test
    public void test_loadLatest_startLoading_and_firesLoadStartEvent() {
        repository.loadLatest();

        verify(mockEventBus).publish(any(LoadingEvent.LoadingStarted.class));
        verify(mockWebService).requestLatestFeed(any(FeedRequestCallback.class));
    }

    @Test
    public void testHistoryLoadingCallPredictorFirst() {
        when(mockHistoryPredictor.isFirstHistoryPageKnown()).thenReturn(false);
        when(mockHistoryPredictor.getFirstHistoryPage()).thenReturn(-1);

        repository.loadNextHistory();

        verify(mockHistoryPredictor).startPrediction();
        verify(mockWebService, never()).requestFeedPage(anyInt(), any(FeedRequestCallback.class));
    }

    @Test
    public void testHistoryLoadingCallPredictedPage() {
        when(mockHistoryPredictor.isFirstHistoryPageKnown()).thenReturn(true);
        when(mockHistoryPredictor.getFirstHistoryPage()).thenReturn(1000);

        repository.loadNextHistory();

        verify(mockHistoryPredictor, never()).startPrediction();
        verify(mockHistoryPredictor).getFirstHistoryPage();
        verify(mockWebService).requestFeedPage(anyInt(), any(FeedRequestCallback.class));
    }

    @Test
    public void test_PrepareForHistoryLoading_calledWhenHistoryIsUnknown() {
        when(mockHistoryPredictor.isFirstHistoryPageKnown()).thenReturn(false);

        repository.prepareForHistoryLoading();

        verify(mockHistoryPredictor).startPrediction();
    }

    @Test
    public void test_PrepareForHistoryLoading_notCalledWhenHistoryIsKnown() {
        when(mockHistoryPredictor.isFirstHistoryPageKnown()).thenReturn(true);

        repository.prepareForHistoryLoading();

        verify(mockHistoryPredictor, never()).startPrediction();
    }

    @Test
    public void test_onSuccess_firesLoadFinishEvents() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                FeedRequestCallback callback = invocationOnMock.getArgument(0);
                callback.onSuccess(new URL("http://empty.url"), 0, 200, REAL_FEED_RESPONSE);
                return null;
            }
        }).when(mockWebService).requestLatestFeed(any(FeedRequestCallback.class));

        repository.loadLatest();

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

        repository.loadLatest();

        verify(mockEventBus).publish(any(LoadingEvent.LoadingFinished.class));
        verify(mockEventBus).publish(any(LoadingEvent.LoadingError.class));
    }
}
