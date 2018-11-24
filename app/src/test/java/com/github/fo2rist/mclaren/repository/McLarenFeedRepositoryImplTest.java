package com.github.fo2rist.mclaren.repository;

import com.github.fo2rist.mclaren.web.FeedHistoryPredictor;
import com.github.fo2rist.mclaren.web.FeedWebService.FeedRequestCallback;
import com.github.fo2rist.mclaren.web.McLarenFeedWebService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class McLarenFeedRepositoryImplTest {

    private McLarenFeedRepositoryImpl feedRepository;
    private McLarenFeedWebService mockWebservice;
    private FeedHistoryPredictor mockHistoryPredictor;

    @Before
    public void setUp() throws Exception {
        mockWebservice = mock(McLarenFeedWebService.class);
        mockHistoryPredictor = mock(FeedHistoryPredictor.class);
        FeedRepositoryEventBus mockEventBus = mock(FeedRepositoryEventBus.class);

        feedRepository = new McLarenFeedRepositoryImpl(mockWebservice, mockEventBus, mockHistoryPredictor);
    }

    @Test
    public void testLoadingCallsWebApi() throws Exception {
        feedRepository.loadLatest();

        verify(mockWebservice).requestLatestFeed(any(FeedRequestCallback.class));
    }

    @Test
    public void testHistoryLoadingCallPredictorFirst() throws Exception {
        when(mockHistoryPredictor.isFirstHistoryPageKnown()).thenReturn(false);
        when(mockHistoryPredictor.getFirstHistoryPage()).thenReturn(-1);

        feedRepository.loadNextHistory();

        verify(mockHistoryPredictor).startPrediction();
        verify(mockWebservice, never()).requestFeedPage(anyInt(), any(FeedRequestCallback.class));
    }

    @Test
    public void testHistoryLoadingCallPredictedPage() throws Exception {
        when(mockHistoryPredictor.isFirstHistoryPageKnown()).thenReturn(true);
        when(mockHistoryPredictor.getFirstHistoryPage()).thenReturn(1000);

        feedRepository.loadNextHistory();

        verify(mockHistoryPredictor, never()).startPrediction();
        verify(mockHistoryPredictor).getFirstHistoryPage();
        verify(mockWebservice).requestFeedPage(anyInt(), any(FeedRequestCallback.class));
    }
}
