package com.github.fo2rist.mclaren.ui.previewscreen

import com.github.fo2rist.mclaren.analytics.Events
import com.github.fo2rist.mclaren.analytics.EventsLogger
import com.github.fo2rist.mclaren.mvp.ImagePreviewContract
import com.github.fo2rist.mclaren.testdata.FeedItems.TWITTER_GALLERY_ITEM
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class ImagePreviewPresenterTest {

    private lateinit var mockEventsLogger: EventsLogger
    private lateinit var mockView: ImagePreviewContract.View
    private lateinit var presenter: ImagePreviewPresenter

    @Before
    fun setUp() {
        mockView = mock(ImagePreviewContract.View::class.java)
        mockEventsLogger = mock(EventsLogger::class.java)
        presenter = ImagePreviewPresenter(mockView, mockEventsLogger)
    }

    @Test
    fun test_onStartWith_galleryItem_showsImages() {
        presenter.onStartWith(TWITTER_GALLERY_ITEM)

        verify(mockView).showImages(TWITTER_GALLERY_ITEM.imageUrls)
    }

    @Test
    fun test_onStartWith_null_showsNoting() {
        presenter.onStartWith(null)

        verify(mockView, never()).showImages(anyList())
    }

    @Test
    fun test_onScroll_emitsNextPreviousEvents() {
        presenter.onStartWith(TWITTER_GALLERY_ITEM)

        presenter.onScrolledTo(1)
        presenter.onScrolledTo(0)

        verify(mockEventsLogger).logViewEvent(Events.GALLERY_NEXT)
        verify(mockEventsLogger).logViewEvent(Events.GALLERY_PREV)
    }
}
