package com.github.fo2rist.mclaren.ui.previewscreen

import com.github.fo2rist.mclaren.mvp.PreviewContract
import com.github.fo2rist.mclaren.testdata.FeedItems.ARTICLE_ITEM_WITH_LINKS
import com.github.fo2rist.mclaren.testdata.FeedItems.MEDIA_LINK
import com.github.fo2rist.mclaren.testdata.FeedItems.MESSAGE_ITEM
import com.github.fo2rist.mclaren.testdata.FeedItems.TWITTER_GALLERY_ITEM
import com.github.fo2rist.mclaren.testdata.FeedItems.VIDEO_ITEM
import com.github.fo2rist.mclaren.ui.models.Orientation
import com.github.fo2rist.mclaren.ui.models.PreviewContent
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class PreviewPresenterTest{

    private val viewMock: PreviewContract.View = mock()

    private val presenter = PreviewPresenter(viewMock)

    @Test
    fun `test onStartWith unsupported types finishes view`() {
        for (item in listOf(VIDEO_ITEM, MESSAGE_ITEM)) {
            reset(viewMock)
            presenter.onStartWith(Orientation.PORTRAIT, item)

            verify(viewMock).finish()
        }
    }

    @Test
    fun `test onStartWith URL launches url without toolbar image`() {
        presenter.onStartWith(MEDIA_LINK)

        verify(viewMock).displayFragment(any<PreviewContent.Url>())
    }

    @Test
    fun `test onStartWith ArticleItem in Portrait mode launches Html with title and image`() {
        presenter.onStartWith(Orientation.PORTRAIT, ARTICLE_ITEM_WITH_LINKS)

        verify(viewMock).displayFragment(any<PreviewContent.Html>())
        verify(viewMock).setTitle(ARTICLE_ITEM_WITH_LINKS.text)
        verify(viewMock).setToolBarImage(ARTICLE_ITEM_WITH_LINKS.imageUrls)
    }

    @Test
    fun `test onStartWith ArticleItem in Landscape mode launches Html with title and image`() {
        presenter.onStartWith(Orientation.LANDSCAPE, ARTICLE_ITEM_WITH_LINKS)

        verify(viewMock).displayFragment(any<PreviewContent.Html>())
        verify(viewMock).setTitle(ARTICLE_ITEM_WITH_LINKS.text)
        verify(viewMock, never()).setToolBarImage(anyOrNull())
    }

    @Test
    fun `test onStartWith empty ArticleItem finishes view`() {
        presenter.onStartWith(Orientation.PORTRAIT, ARTICLE_ITEM_WITH_LINKS.copy(content = null))

        verify(viewMock).finish()
    }

    @Test
    fun `test onStartWith GalleryItem in Portrait mode launches image with locked in fullscreen`() {
        presenter.onStartWith(Orientation.PORTRAIT, TWITTER_GALLERY_ITEM)

        verify(viewMock).displayFragment(any<PreviewContent.FeedItem>())
        verify(viewMock).enterFullScreen()
        verify(viewMock).lockToolBar()
        verify(viewMock, never()).setToolBarImage(anyOrNull())
    }

    @Test
    fun `test onStartWith GalleryItem in Landscape mode launches image without toolbar in fullscreen`() {
        presenter.onStartWith(Orientation.LANDSCAPE, TWITTER_GALLERY_ITEM)

        verify(viewMock).displayFragment(any<PreviewContent.FeedItem>())
        verify(viewMock).enterFullScreen()
        verify(viewMock).hideToolBar()
        verify(viewMock, never()).setToolBarImage(anyOrNull())
    }
}
