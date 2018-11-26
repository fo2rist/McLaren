package com.github.fo2rist.mclaren.ui.feedscreen

import com.github.fo2rist.mclaren.analytics.Events
import com.github.fo2rist.mclaren.analytics.EventsLogger
import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.mvp.FeedContract
import com.github.fo2rist.mclaren.repository.FeedRepository
import com.github.fo2rist.mclaren.repository.FeedRepositoryEventBus
import com.github.fo2rist.mclaren.repository.FeedRepositoryEventBus.LoadingEvent
import com.github.fo2rist.mclaren.utils.LinkUtils.getFeedMentionLink
import com.github.fo2rist.mclaren.utils.LinkUtils.getMediaLink
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

/**
 * Presenter for social "Feed" view.
 */
class FeedPresenter @Inject constructor(
        private val feedRepository: FeedRepository,
        private val repositoryEventBus: FeedRepositoryEventBus,
        private val eventsLogger: EventsLogger
) : FeedContract.Presenter {

    private lateinit var view: FeedContract.View

    override fun onStart(view: FeedContract.View) {
        this.view = view
        this.repositoryEventBus.subscribe(this)

        loadFeed()
    }

    override fun onStop() {
        this.repositoryEventBus.unsubscribe(this)
    }

    override fun onItemClicked(item: FeedItem) {
        when (item.type) {
            FeedItem.Type.Video ->
                getMediaLink(item)?.let { navigateViewToVideoPreviewScreen(it) }
            FeedItem.Type.Image,
            FeedItem.Type.Gallery ->
                navigateViewToPreviewScreen(item, Events.VIEW_IMAGES)
            FeedItem.Type.Article ->
                navigateViewToPreviewScreen(item, Events.VIEW_ARTICLE)
            FeedItem.Type.Message -> { }
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

    private fun navigateViewToPreviewScreen(item: FeedItem, previewEventType: Events) {
        view.navigateToPreview(item)
        eventsLogger.logViewEvent(previewEventType)
    }

    private fun navigateViewToVideoPreviewScreen(link: String) {
        view.navigateToPreview(link)
        eventsLogger.logViewEvent(Events.VIEW_VIDEO)
    }

    private fun navigateViewToBrowser(link: String) {
        view.navigateToBrowser(link)
        eventsLogger.logViewEvent(Events.VIEW_EXTERNAL, link)
    }

    override fun onRefreshRequested() {
        loadFeed()
    }

    private fun loadFeed() {
        this.feedRepository.loadLatest()
    }

    override fun onScrolledToSecondThird() {
        this.feedRepository.prepareForHistoryLoading()
    }

    override fun onScrolledToBottom() {
        this.feedRepository.loadNextHistory()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoadingStarted(event: LoadingEvent.LoadingStarted) {
        view.showProgress()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFeedUpdateReceived(event: LoadingEvent.FeedUpdateReady) {
        view.displayFeed(event.feed)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoadingFinished(event: LoadingEvent.LoadingFinished) {
        view.hideProgress()
    }
}
