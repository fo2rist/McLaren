package com.github.fo2rist.mclaren.mvp

import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.models.ImageUrl
import com.github.fo2rist.mclaren.ui.models.Orientation
import com.github.fo2rist.mclaren.ui.models.PreviewContent

/**
 * Contract for preview parent activity.
 * @see [ImagePreviewContract] and [com.github.fo2rist.mclaren.ui.previewscreen.WebPreviewFragment]
 */
interface PreviewContract {
    interface View : BaseView{

        fun displayFragment(content: PreviewContent)

        fun setTitle(text: String)

        fun setToolBarImage(imageUrls: List<ImageUrl>)

        fun lockToolBar()

        fun hideToolBar()

        fun enterFullScreen()

        fun finish()
    }

    interface Presenter : BasePresenter<View> {

        fun onStartWith(url: String)

        fun onStartWith(orientation: Orientation, feedItem: FeedItem)
    }
}
