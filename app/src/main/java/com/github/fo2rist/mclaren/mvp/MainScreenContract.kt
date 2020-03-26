package com.github.fo2rist.mclaren.mvp

import android.support.annotation.NonNull
import com.github.fo2rist.mclaren.ui.models.CalendarEvent
import org.joda.time.DateTime

/**
 * Contract for Main Screen with navigation.
 */
interface MainScreenContract {

    interface View : BaseView {
        fun openStories()
        fun openCircuits()
        fun openDrivers()

        fun openCircuitScreen(event: CalendarEvent)
        fun openAboutScreen()
        fun openTransmissionCenter()

        fun navigateTo(externalUrl: String)

        fun showTransmissionButton()
        fun showUpcomingEventButton(grandPrixName: String, beginningTime: DateTime)
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
