package com.github.fo2rist.mclaren.ui

import com.github.fo2rist.mclaren.analytics.Events
import com.github.fo2rist.mclaren.analytics.EventsLogger
import com.github.fo2rist.mclaren.mvp.MainScreenContract
import com.github.fo2rist.mclaren.repository.remoteconfig.RaceCalendarRepository
import com.github.fo2rist.mclaren.ui.models.RaceCalendar
import com.github.fo2rist.mclaren.utils.LinkUtils.getMcLarenCarLink
import com.github.fo2rist.mclaren.utils.LinkUtils.getMcLarenFormula1Link
import javax.inject.Inject

/**
 * Presenter for [MainActivity].
 * Controls high-level navigation to other components of the screen.
 */
class MainPresenter @Inject constructor(
    override var view: MainScreenContract.View,
    private val eventsLogger: EventsLogger,
    private val raceCalendarRepository: RaceCalendarRepository
) : MainScreenContract.Presenter {

    private val raceCalendar: RaceCalendar by lazy { raceCalendarRepository.loadCalendar() }

    override fun onStart() {
        openStories()
    }

    override fun onStoriesClicked() {
        openStories()
    }

    private fun openStories() {
        view.openStories()

        val activeEvent = raceCalendar.getActiveEvent()
        val upcomingEvent = raceCalendar.getNextEvent()
        if (activeEvent != null) {
            // can notify users about the race in progress
            // via view.showTransmissionButton()
            // do nothing for now (used to launch transmission screen)
        } else if (upcomingEvent != null) {
            view.showUpcomingEventButton(upcomingEvent.grandPrixName, upcomingEvent.practice1DateTime!!)
        }

        eventsLogger.logViewEvent(Events.MENU_STORIES)
    }

    override fun onTeeamTwitterClicked() {
        view.openTweets()
        view.hideFloatingButtons();
        eventsLogger.logViewEvent(Events.MENU_TEAM_TWITTER)
    }

    override fun onCircuitsClicked() {
        view.openCircuits()
        view.hideFloatingButtons();
        eventsLogger.logViewEvent(Events.MENU_CALENDAR)
    }

    override fun onDriversClicked() {
        view.openDrivers()
        view.hideFloatingButtons();
        eventsLogger.logViewEvent(Events.MENU_DRIVERS)
    }

    override fun onCarClicked() {
        view.navigateTo(
                getMcLarenCarLink())

        eventsLogger.logViewEvent(Events.MENU_CAR)
    }

    override fun onOfficialSiteClicked() {
        view.navigateTo(
                getMcLarenFormula1Link())
        eventsLogger.logViewEvent(Events.MENU_SITE)
    }

    override fun onAboutClicked() {
        view.openAboutScreen()
        eventsLogger.logViewEvent(Events.MENU_ABOUT)
    }

    override fun onTransmissionCenterClicked() {
        view.openTransmissionCenter()
        eventsLogger.logViewEvent(Events.TRANSMISSION_CENTER)
    }

    override fun onUpcomingEventClicked() {
        val eventToOpen = raceCalendar.getActiveEvent()
                ?: raceCalendar.getNextEvent()
                ?: return
        view.openCircuitScreen(raceCalendar.indexOf(eventToOpen)) //the index is never out of bounds here
        eventsLogger.logViewEvent(Events.DETAILS_CIRCUIT)
    }
}
