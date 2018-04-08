package com.github.fo2rist.mclaren.mvp

interface TransmissionContract {
    interface View : BaseView {
        fun displayTransmission()
    }

    interface Presenter : BasePresenter<View>
}
