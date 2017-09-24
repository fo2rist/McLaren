package com.github.fo2rist.mclaren.repository;

import com.github.fo2rist.mclaren.web.FeedHistoryPredictor;
import com.github.fo2rist.mclaren.web.FeedWebsevice;
import com.github.fo2rist.mclaren.web.WebCallback;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class McLarenFeedRepositoryTest {

    private McLarenFeedRepository feedRepository;
    private FeedWebsevice mockWebservice;
    private FeedHistoryPredictor mockHistoryPredictor;

    @Before
    public void setUp() throws Exception {
        mockWebservice = mock(FeedWebsevice.class);
        mockHistoryPredictor = mock(FeedHistoryPredictor.class);
        FeedRepositoryPubSub mockPubSub = mock(FeedRepositoryPubSub.class);

        feedRepository = new McLarenFeedRepository(mockWebservice, mockPubSub, mockHistoryPredictor);
    }

    @Test
    public void testLoadingCallsWebApi() throws Exception {
        feedRepository.loadLatest();

        verify(mockWebservice).requestLatestFeed(any(WebCallback.class));
    }

    @Test
    public void testHistoryLoadingCallPredictorFirst() throws Exception {
        when(mockHistoryPredictor.isFirstHistoryPageKnown()).thenReturn(false);
        when(mockHistoryPredictor.getFirstHistoryPage()).thenReturn(-1);

        feedRepository.loadPrevious();

        verify(mockHistoryPredictor).startPrediction();
        verify(mockWebservice, never()).requestFeedPage(anyInt(), any(WebCallback.class));
    }

    @Test
    public void testHistoryLoadingCallPredictedPage() throws Exception {
        when(mockHistoryPredictor.isFirstHistoryPageKnown()).thenReturn(true);
        when(mockHistoryPredictor.getFirstHistoryPage()).thenReturn(1000);

        feedRepository.loadPrevious();

        verify(mockHistoryPredictor, never()).startPrediction();
        verify(mockHistoryPredictor).getFirstHistoryPage();
        verify(mockWebservice).requestFeedPage(anyInt(), any(WebCallback.class));
    }
}
