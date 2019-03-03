package com.github.fo2rist.mclaren.ui.models

/**
 * Content to be displayed in Preview Screen.
 */
sealed class PreviewContent {

    data class Url(val url: String) : PreviewContent()

    data class Html(val html: String) : PreviewContent()

    data class FeedItem(val feedItem: com.github.fo2rist.mclaren.models.FeedItem) : PreviewContent()
}
