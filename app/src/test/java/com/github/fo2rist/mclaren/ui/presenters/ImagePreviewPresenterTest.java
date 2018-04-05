package com.github.fo2rist.mclaren.ui.presenters;

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
    public void setUp() throws Exception {
        mockView = mock(ImagePreviewContract.View.class);
        mockEventsLogger = mock(EventsLogger.class);
        presenter = new ImagePreviewPresenter(mockEventsLogger);
    }

    @Test
    public void test_onStartWith_galleryItem_showsImages() {
        presenter.onStartWith(mockView, TWITTER_GALLERY_ITEM);

        verify(mockView).showImages(TWITTER_GALLERY_ITEM.imageUrls);
    }

    @Test
    public void test_onStartWith_null_showsNoting() {
        presenter.onStartWith(mockView, null);

        verify(mockView, never()).showImages(any(List.class));
    }
}
