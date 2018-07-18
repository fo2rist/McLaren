package com.github.fo2rist.mclaren.ui.transmissionscreen

import com.github.fo2rist.mclaren.mvp.TransmissionContract
import com.github.fo2rist.mclaren.repository.TransmissionRepository
import com.github.fo2rist.mclaren.repository.TransmissionRepositoryPubSub
import com.github.fo2rist.mclaren.repository.TransmissionRepositoryPubSub.PubSubEvent
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.timer

private const val REFRESH_INTERVAL_MS = 30_000L

class TransmissionPresenter
@Inject constructor(
        var repository: TransmissionRepository,
        var repositoryPubSub: TransmissionRepositoryPubSub
): TransmissionContract.Presenter {
    private lateinit var view: TransmissionContract.View
    private var pollTimer: Timer? = null

    override fun onStart(view: TransmissionContract.View) {
        this.view = view
        startTransmissionPolling()
    }

    override fun onStop() {
        stopTransmissionPolling()
    }

    private fun startTransmissionPolling() {
        repositoryPubSub.subscribe(this)
        repository.loadTransmission()

        if (pollTimer == null) {
            pollTimer = timer(name = "", initialDelay = REFRESH_INTERVAL_MS, period = REFRESH_INTERVAL_MS) {
                repository.refreshTransmission()
            }
        }
    }

    private fun stopTransmissionPolling() {
        pollTimer?.cancel()
        pollTimer = null
        repositoryPubSub.unsubscribe(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoadingStarted(event: PubSubEvent.LoadingStarted) {
        view.showProgress()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFeedUpdateReceived(event: PubSubEvent.TransmissionUpdateReady) {
        val transmissionInfo = event.data

        view.displayTransmission(transmissionInfo.messages)
        view.setNoTransmissionStubVisible(transmissionInfo.messages.isEmpty())
        view.displayCurrentSession(transmissionInfo.raceName)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoadingFinished(event: PubSubEvent.LoadingFinished) {
        view.hideProgress()
    }
}
