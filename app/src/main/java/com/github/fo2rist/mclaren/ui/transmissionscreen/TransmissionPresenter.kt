package com.github.fo2rist.mclaren.ui.transmissionscreen

import com.github.fo2rist.mclaren.models.TransmissionItem
import com.github.fo2rist.mclaren.mvp.TransmissionContract
import com.github.fo2rist.mclaren.repository.TransmissionRepository
import com.github.fo2rist.mclaren.repository.TransmissionRepositoryPubSub
import com.github.fo2rist.mclaren.repository.TransmissionRepositoryPubSub.PubSubEvent
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class TransmissionPresenter
@Inject constructor(
        var repository: TransmissionRepository,
        var repositoryPubSub: TransmissionRepositoryPubSub
): TransmissionContract.Presenter {
    private lateinit var view: TransmissionContract.View

    override fun onStart(view: TransmissionContract.View) {
        this.view = view
        repositoryPubSub.subscribe(this)
        repository.loadTransmission() // TODO fetch once in 30 sec unless stopped
    }

    override fun onStop() {
        repositoryPubSub.unsubscribe(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoadingStarted(event: PubSubEvent.LoadingStarted) {
        view.showProgress()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFeedUpdateReceived(event: PubSubEvent.TransmissionUpdateReady) {
        view.displayTransmission(event.obj as List<TransmissionItem>)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoadingFinished(event: PubSubEvent.LoadingFinished) {
        view.hideProgress()
    }
}
