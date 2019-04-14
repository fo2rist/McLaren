package com.github.fo2rist.mclaren.ui.previewscreen;

import com.github.fo2rist.mclaren.analytics.Events;
import com.github.fo2rist.mclaren.analytics.EventsLogger;
import com.github.fo2rist.mclaren.mvp.ImagePreviewContract;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.github.fo2rist.mclaren.testdata.FeedItems.TWITTER_GALLERY_ITEM;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class ImagePreviewPresenterTest {

    private ImagePreviewPresenter presenter;
    private EventsLogger mockEventsLogger;
    private ImagePreviewContract.View mockView;

    @Before
    public void setUp() {
        mockView = mock(ImagePreviewContract.View.class);
        mockEventsLogger = mock(EventsLogger.class);
        presenter = new ImagePreviewPresenter(mockView, mockEventsLogger);
    }

    @Test
    public void test_onStartWith_galleryItem_showsImages() {
        presenter.onStartWith(TWITTER_GALLERY_ITEM);

        verify(mockView).showImages(TWITTER_GALLERY_ITEM.getImageUrls());
    }

    @Test
    public void test_onStartWith_null_showsNoting() {
        presenter.onStartWith(null);

        verify(mockView, never()).showImages(any(List.class));
    }

    @Test
    public void test_onScroll_emitsNextPrevieousEvents() {
        presenter.onStartWith(TWITTER_GALLERY_ITEM);

        presenter.onScrolledTo(1);
        presenter.onScrolledTo(0);

        verify(mockEventsLogger).logViewEvent(Events.GALLERY_NEXT);
        verify(mockEventsLogger).logViewEvent(Events.GALLERY_PREV);
    }
}
