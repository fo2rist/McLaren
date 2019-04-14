package com.github.fo2rist.mclaren.mvp

interface MainScreenContract {

    interface View : BaseView {
        fun openStories()
        fun openCircuits()
        fun openDrivers()
        fun openAboutScreen()
        fun openTransmissionCenter()

        fun navigateTo(externalUrl: String)

        fun showTransmissionButton()
    }

    interface Presenter : BasePresenter<View> {
        fun onStart()

        fun onStoriesClicked()
        fun onCircuitsClicked()
        fun onDriversClicked()
        fun onCarClicked()
        fun onOfficialSiteClicked()
        fun onAboutClicked()
        fun onTransmissionCenterClicked()
    }
}
