package com.github.fo2rist.mclaren.ui

import com.github.fo2rist.mclaren.analytics.Events
import com.github.fo2rist.mclaren.analytics.Analytics
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
    private val analytics: Analytics,
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
        analytics.overrideScreenName(Events.Screen.STORIES)

        val activeEvent = raceCalendar.getActiveEvent()
        val upcomingEvent = raceCalendar.getNextEvent()
        if (activeEvent != null) {
            view.showTransmissionButton()
        } else if (upcomingEvent != null) {
            view.showUpcomingEventButton(upcomingEvent.grandPrixName, upcomingEvent.practice1DateTime!!)
        }
    }

    override fun onTeamTwitterClicked() {
        view.openTweetsMcLaren()
        analytics.overrideScreenName(Events.Screen.TEAM_TWITTER)

        view.hideFloatingButtons();
    }

    override fun onLandoTwitterClicked() {
        view.openTweetsLando()
    }

    override fun onDanielTwitterClicked() {
        view.openTweetsDaniel()
    }

    override fun onSeasonCalendarClicked() {
        view.openCircuits()
        analytics.overrideScreenName(Events.Screen.SEASON_CALENDAR)

        view.hideFloatingButtons();
    }

    override fun onDriversClicked() {
        view.openDrivers()
        analytics.overrideScreenName(Events.Screen.DRIVERS)

        view.hideFloatingButtons();
    }

    override fun onCarClicked() {
        val mcLarenCarPageLink = getMcLarenCarLink()
        view.navigateTo(mcLarenCarPageLink)
        analytics.logExternalNavigation(Events.Screen.CAR_WEB_PAGE, mcLarenCarPageLink)
    }

    override fun onOfficialSiteClicked() {
        val mcLarenF1PageLink = getMcLarenFormula1Link()
        view.navigateTo(mcLarenF1PageLink)
        analytics.logExternalNavigation(Events.Screen.TEAM_WEB_PAGE, mcLarenF1PageLink)
    }

    override fun onAboutClicked() {
        view.navigateToAboutScreen()
    }

    override fun onTransmissionCenterClicked() {
        view.openTransmissionCenter()
        analytics.overrideScreenName(Events.Screen.TRANSMISSION_CENTER)
    }

    override fun onUpcomingEventClicked() {
        val eventToOpen = raceCalendar.getActiveEvent()
                ?: raceCalendar.getNextEvent()
                ?: return
        view.navigateToCircuitScreen(raceCalendar.indexOf(eventToOpen)) //the index is never out of bounds here
    }
}
