package com.github.fo2rist.mclaren.ui.feedscreen

import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.mvp.FeedContract
import com.github.fo2rist.mclaren.repository.feed.FeedRepository
import com.github.fo2rist.mclaren.repository.feed.FeedRepositoryEventBus
import com.github.fo2rist.mclaren.repository.feed.FeedRepositoryEventBus.LoadingEvent
import com.github.fo2rist.mclaren.utils.LinkUtils.getFeedMentionLink
import com.github.fo2rist.mclaren.utils.LinkUtils.getMediaLink
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

/**
 * Presenter for social "Feed" view.
 */
class FeedPresenter @Inject constructor(
    override val view: FeedContract.View,
    private val feedRepository: FeedRepository,
    private val repositoryEventBus: FeedRepositoryEventBus
) : FeedContract.Presenter {
    /**
     * Account withing a service the presenter is displaying.
     * Can be empty if the service doesn't have notion of accounts, but must be initialized for services with multiple
     * accounts, e.g Twitter.
     * TODO move it to the interface when interface is converted to kotlin.
     */
    private var account: String = ""

    override fun setAccount(account: String) {
        this.account = account
    }

    override fun onStart() {
        repositoryEventBus.subscribe(this)
        feedRepository.loadLatestPage(account)
    }

    override fun onStop() {
        repositoryEventBus.unsubscribe(this)
    }

    override fun onItemClicked(item: FeedItem) {
        when (item.type) {
            FeedItem.Type.Video ->
                getMediaLink(item)?.let { navigateViewToVideoPreviewScreen(it) }
            FeedItem.Type.Image,
            FeedItem.Type.Gallery ->
                navigateViewToPreviewScreen(item)
            FeedItem.Type.Article ->
                navigateViewToPreviewScreen(item)
            FeedItem.Type.Message -> Unit // no need to do anything, for click on text message
        }
    }

    override fun onItemSourceClicked(item: FeedItem) {
        getFeedMentionLink(item, item.sourceName)?.let {
            navigateViewToBrowser(it)
        }
    }

    override fun onLinkClicked(item: FeedItem, link: String) {
        navigateViewToBrowser(link)
    }

    private fun navigateViewToPreviewScreen(item: FeedItem) {
        view.navigateToPreview(item)
    }

    private fun navigateViewToVideoPreviewScreen(link: String) {
        view.navigateToPreview(link)
    }

    private fun navigateViewToBrowser(link: String) {
        view.navigateToBrowser(link)
    }

    override fun onRefreshRequested() {
        feedRepository.loadLatestPage(account)
    }

    override fun onScrolledToSecondThird() {
        feedRepository.prepareForHistoryLoading()
    }

    override fun onScrolledToBottom() {
        feedRepository.loadNextPage(account)
    }

    /** EventBus message receiver for Loading start event. */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoadingStarted(@Suppress("UNUSED_PARAMETER") event: LoadingEvent.LoadingStarted) {
        view.showProgress()
    }

    /** EventBus message receiver for data update event. */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFeedUpdateReceived(event: LoadingEvent.FeedUpdateReady) {
        view.displayFeed(event.feed)
    }

    /** EventBus message receiver for Loading start event. */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoadingFinished(@Suppress("UNUSED_PARAMETER") event: LoadingEvent.LoadingFinished) {
        view.hideProgress()
    }
}
