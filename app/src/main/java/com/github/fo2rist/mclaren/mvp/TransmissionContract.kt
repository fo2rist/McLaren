package com.github.fo2rist.mclaren.mvp

import com.github.fo2rist.mclaren.models.TransmissionItem

/**
 * MVP contract for life transmission screen.
 */
interface TransmissionContract {
    interface View : BaseView {
        fun displayTransmission(transmissionMessages: List<TransmissionItem>)
        fun displayCurrentSession(session: TransmissionItem.Session)
        fun setNoTransmissionStubVisible(visible: Boolean)
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter : BasePresenter<View>, Stoppable {
        fun onStart()
    }
}
