package com.github.fo2rist.mclaren.mvp

import org.joda.time.DateTime

/**
 * Contract for Main Screen with navigation.
 */
interface MainScreenContract {

    interface View : BaseView {
        fun openStories()
        fun openCircuits()
        fun openDrivers()

        fun openCircuitScreen(eventNumber: Int)
        fun openAboutScreen()
        fun openTransmissionCenter()

        fun navigateTo(externalUrl: String)

        fun showTransmissionButton()
        fun showUpcomingEventButton(grandPrixName: String, beginningTime: DateTime)
        fun hideFloatingButtons()
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
        fun onUpcomingEventClicked()
    }
}
