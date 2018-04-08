package com.github.fo2rist.mclaren.ui.transmissionscreen

import com.github.fo2rist.mclaren.mvp.TransmissionContract
import com.github.fo2rist.mclaren.repository.TransmissionRepository
import javax.inject.Inject

class TransmissionPresenter
@Inject constructor(
        var repository: TransmissionRepository
): TransmissionContract.Presenter {
    override fun onStart(view: TransmissionContract.View?) {
        repository.loadTransmission() // TODO fetch once in 30 sec unless stopped
    }

    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
