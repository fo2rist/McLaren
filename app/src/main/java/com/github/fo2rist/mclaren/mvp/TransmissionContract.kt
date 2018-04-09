package com.github.fo2rist.mclaren.mvp

import com.github.fo2rist.mclaren.models.TransmissionItem

interface TransmissionContract {
    interface View : BaseView {
        fun displayTransmission(transmissionItems: List<TransmissionItem>)
        fun displayCurrentSession(session: TransmissionItem.Session)
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter : BasePresenter<View>
}
