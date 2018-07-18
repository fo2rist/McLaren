package com.github.fo2rist.mclaren.mvp

import com.github.fo2rist.mclaren.models.TransmissionItem

interface TransmissionContract {
    interface View : BaseView {
        fun displayTransmission(transmissionMessages: List<TransmissionItem>)
        fun displayCurrentSession(sessionName: String)
        fun setNoTransmissionStubVisible(visible: Boolean)
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter : BasePresenter<View>
}
