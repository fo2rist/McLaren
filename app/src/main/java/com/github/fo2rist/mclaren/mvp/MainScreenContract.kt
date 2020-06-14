package com.github.fo2rist.mclaren.mvp

import org.joda.time.DateTime

/**
 * Contract for Main Screen with navigation.
 */
interface MainScreenContract {

    interface View : BaseView {
        fun openStories()
        fun openTweets()
        fun openCircuits()
        fun openDrivers()

        fun openTransmissionCenter()

        fun navigateToCircuitScreen(eventNumber: Int)
        fun navigateToAboutScreen()
        fun navigateTo(externalUrl: String)

        fun showTransmissionButton()
        fun showUpcomingEventButton(grandPrixName: String, beginningTime: DateTime)
        fun hideFloatingButtons()
    }

    interface Presenter : BasePresenter<View> {
        fun onStart()

        fun onStoriesClicked()
        fun onTeamTwitterClicked()
        fun onSeasonCalendarClicked()
        fun onDriversClicked()
        fun onCarClicked()
        fun onOfficialSiteClicked()
        fun onAboutClicked()
        fun onTransmissionCenterClicked()
        fun onUpcomingEventClicked()
    }
}
