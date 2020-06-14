package com.github.fo2rist.mclaren.ui.previewscreen

import com.github.fo2rist.mclaren.analytics.Events
import com.github.fo2rist.mclaren.analytics.Analytics
import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.mvp.ImagePreviewContract
import javax.inject.Inject


internal class ImagePreviewPresenter @Inject constructor(
    override val view: ImagePreviewContract.View,
    private val analytics: Analytics
) : ImagePreviewContract.Presenter {

    private var lastKnownGalleryPosition = 0

    private var item: FeedItem? = null

    override fun onStartWith(item: FeedItem?) {
        this.item = item

        showItemContent()
    }

    override fun onScrolledTo(position: Int) {
        if (position > lastKnownGalleryPosition) {
            analytics.logInternalAction(Events.Action.GALLERY_NEXT)
        } else if (position < lastKnownGalleryPosition) {
            analytics.logInternalAction(Events.Action.GALLERY_PREV)
        }
        lastKnownGalleryPosition = position
    }

    private fun showItemContent() {
        item?.let {
            view.showImages(it.imageUrls)
        }
    }
}
