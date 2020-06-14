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
        eventsLogger.overrideScreenName(Events.Screen.MENU_STORIES)

        val activeEvent = raceCalendar.getActiveEvent()
        val upcomingEvent = raceCalendar.getNextEvent()
        if (activeEvent != null) {
            // can notify users about the race in progress
            // via view.showTransmissionButton()
            // do nothing for now (used to launch transmission screen)
        } else if (upcomingEvent != null) {
            view.showUpcomingEventButton(upcomingEvent.grandPrixName, upcomingEvent.practice1DateTime!!)
        }

    }

    override fun onTeamTwitterClicked() {
        view.openTweets()
        eventsLogger.overrideScreenName(Events.Screen.MENU_TEAM_TWITTER)

        view.hideFloatingButtons();
    }

    override fun onSeasonCalendarClicked() {
        view.openCircuits()
        eventsLogger.overrideScreenName(Events.Screen.MENU_CALENDAR)

        view.hideFloatingButtons();
    }

    override fun onDriversClicked() {
        view.openDrivers()
        eventsLogger.overrideScreenName(Events.Screen.MENU_DRIVERS)

        view.hideFloatingButtons();
    }

    override fun onCarClicked() {
        val mcLarenCarPageLink = getMcLarenCarLink()
        view.navigateTo(mcLarenCarPageLink)
        eventsLogger.logExternalNavigation(Events.Screen.MENU_CAR, mcLarenCarPageLink)
    }

    override fun onOfficialSiteClicked() {
        val mcLarenF1PageLink = getMcLarenFormula1Link()
        view.navigateTo(mcLarenF1PageLink)
        eventsLogger.logExternalNavigation(Events.Screen.MENU_SITE, mcLarenF1PageLink)
    }

    override fun onAboutClicked() {
        view.navigateToAboutScreen()
    }

    override fun onTransmissionCenterClicked() {
        view.openTransmissionCenter()
        eventsLogger.overrideScreenName(Events.Screen.TRANSMISSION_CENTER)
    }

    override fun onUpcomingEventClicked() {
        val eventToOpen = raceCalendar.getActiveEvent()
                ?: raceCalendar.getNextEvent()
                ?: return
        view.navigateToCircuitScreen(raceCalendar.indexOf(eventToOpen)) //the index is never out of bounds here
    }
}
