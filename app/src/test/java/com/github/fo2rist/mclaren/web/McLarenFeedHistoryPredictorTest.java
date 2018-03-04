package com.github.fo2rist.mclaren.web;

import com.github.fo2rist.mclaren.BuildConfig;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.stubbing.VoidAnswer2;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.github.fo2rist.mclaren.web.McLarenFeedHistoryPredictor.LATEST_KNOWN_PAGE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class McLarenFeedHistoryPredictorTest {

    private McLarenFeedHistoryPredictor predictor;
    private McLarenFeedWebService mockWebservice;

    @Before
    public void setUp() throws Exception {
        mockWebservice = mock(McLarenFeedWebService.class);

        predictor = new McLarenFeedHistoryPredictor(mockWebservice);
    }

    @Test
    public void testInitialPageUnknown() throws Exception {
        assertEquals(McLarenFeedHistoryPredictor.UNKNOWN_PAGE, predictor.getFirstHistoryPage());
    }

    @Test
    public void testDoubleStartIsNotAllowed() throws Exception {
        predictor.startPrediction();
        verify(mockWebservice, only()).requestFeedPage(anyInt(), any(FeedWebServiceCallback.class));

        reset(mockWebservice);

        predictor.startPrediction();
        verify(mockWebservice, never()).requestFeedPage(anyInt(), any(FeedWebServiceCallback.class));
    }

    @Test
    public void testPageResolvedWhenResponsesCorrect() throws Exception {
        //a page between predicted one and last existing one
        int lastExistingPage = (predictor.guessClosestNotExistingPage() + LATEST_KNOWN_PAGE) / 2;
        testPageDetectedProperly(lastExistingPage);
    }

    @Test
    public void testPageResolvedWhenOutOfInitialRange() throws Exception {
        int lastExistingPage = predictor.guessClosestNotExistingPage() + 100;
        testPageDetectedProperly(lastExistingPage);
    }

    private void testPageDetectedProperly(int lastExistingPage) {
        setupMockServerToReturnExistingPage(lastExistingPage);

        predictor.startPrediction();

        verify(mockWebservice, atLeastOnce()).requestFeedPage(anyInt(), any(FeedWebServiceCallback.class));
        assertEquals(lastExistingPage - 1, predictor.getFirstHistoryPage());
        assertFalse(predictor.isActive());
    }

    @Test
    public void testPageUnknonwIfThereIsNoResponse() throws Exception {
        setupMockServerToAlwaysFail();

        predictor.startPrediction();

        verify(mockWebservice, atLeastOnce()).requestFeedPage(anyInt(), any(FeedWebServiceCallback.class));
        assertFalse(predictor.isActive());
    }

    private URL createUrlForPage(int page) throws MalformedURLException {
        return new URL(BuildConfig.MCLAREN_FEED_URL + "?p=" + page);
    }

    private void setupMockServerToReturnExistingPage(final int lastExistingPage) {
        doAnswer(AdditionalAnswers.answerVoid(new VoidAnswer2<Integer, FeedWebServiceCallback>() {
            @Override
            public void answer(Integer pageNumber, FeedWebServiceCallback callback) throws Throwable {
                if (pageNumber > lastExistingPage) {
                    callback.onFailure(createUrlForPage(pageNumber), pageNumber, 404, null);
                } else {
                    callback.onSuccess(createUrlForPage(pageNumber), pageNumber, 200, "");
                }
            }
        })).when(mockWebservice).requestFeedPage(anyInt(), any(FeedWebServiceCallback.class));
    }

    private void setupMockServerToAlwaysFail() {
        doAnswer(AdditionalAnswers.answerVoid(new VoidAnswer2<Integer, FeedWebServiceCallback>() {
            @Override
            public void answer(Integer pageNumber, FeedWebServiceCallback callback) throws Throwable {
                callback.onFailure(createUrlForPage(pageNumber), pageNumber, 500, null);
            }
        })).when(mockWebservice).requestFeedPage(anyInt(), any(FeedWebServiceCallback.class));
    }
}
