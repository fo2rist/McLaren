package com.github.fo2rist.mclaren.ui.previewscreen

import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.models.FeedItem.Type
import com.github.fo2rist.mclaren.models.ImageUrl
import com.github.fo2rist.mclaren.mvp.PreviewContract
import com.github.fo2rist.mclaren.ui.models.Orientation
import com.github.fo2rist.mclaren.ui.models.PreviewContent
import javax.inject.Inject

/**
 * Presenter for Preview wrapper activity.
 * Controls activity specific parameters such as toolbar, doesn't affect preview content.
 */
class PreviewPresenter @Inject constructor(
    override val view: PreviewContract.View
) : PreviewContract.Presenter {

    private lateinit var content: PreviewContent

    override fun onStartWith(url: String) {
        content = PreviewContent.Url(url)

        view.displayFragment(content)
    }

    override fun onStartWith(orientation: Orientation, feedItem: FeedItem) {
        when (feedItem.type) {
            Type.Video, Type.Message -> {
                view.finish()
            }

            Type.Article -> {
                if (feedItem.content == null) {
                    view.finish()
                    return
                }

                startWithHtml(view, orientation, feedItem.text, feedItem.imageUrls, feedItem.content)
            }

            Type.Image, Type.Gallery -> {
                startWithImageGallery(view, orientation, feedItem)
            }
        }
    }

    private fun startWithHtml(
        view: PreviewContract.View,
        orientation: Orientation,
        title: String,
        titleImageUrl: List<ImageUrl>,
        contentHtml: String
    ) {
        if (orientation == Orientation.PORTRAIT) {
            view.setToolBarImage(titleImageUrl)
        }
        //TODO known bug in landscape mode title has no effect. 2019-03-02
        view.setTitle(title)

        content = PreviewContent.Html(contentHtml)
        view.displayFragment(content)
    }

    private fun startWithImageGallery(
        view: PreviewContract.View,
        orientation: Orientation,
        feedItem: FeedItem
    ) {
        if (orientation == Orientation.PORTRAIT) {
            view.lockToolBar()
        } else {
            view.hideToolBar()
        }
        view.enterFullScreen()

        content = PreviewContent.FeedItem(feedItem)
        view.displayFragment(content)
    }
}
