package com.github.fo2rist.mclaren.ui

import com.github.fo2rist.mclaren.analytics.Events
import com.github.fo2rist.mclaren.analytics.EventsLogger
import com.github.fo2rist.mclaren.mvp.MainScreenContract
import com.github.fo2rist.mclaren.repository.RaceCalendarRepository
import com.github.fo2rist.mclaren.ui.models.RaceCalendar
import com.github.fo2rist.mclaren.utils.LinkUtils.getMcLarenCarLink
import com.github.fo2rist.mclaren.utils.LinkUtils.getMcLarenFormula1Link
import javax.inject.Inject

class MainPresenter @Inject constructor(
    override var view: MainScreenContract.View,
    private val eventsLogger: EventsLogger,
    private val raceCalendarRepository: RaceCalendarRepository
) : MainScreenContract.Presenter {

    private val raceCalendar: RaceCalendar by lazy { raceCalendarRepository.loadCalendar() }

    override fun onStart() {
        view.openStories()
        if (isRaceActive()) {
            // can notify users about the race in progress
            // do nothing for now (used to launch transmission screen)
        }
    }

    private fun isRaceActive(): Boolean {
        return raceCalendar.getActiveEvent() != null
    }

    override fun onStoriesClicked() {
        view.openStories()
        eventsLogger.logViewEvent(Events.MENU_STORIES)
    }

    override fun onCircuitsClicked() {
        view.openCircuits()
        eventsLogger.logViewEvent(Events.MENU_CALENDAR)
    }

    override fun onDriversClicked() {
        view.openDrivers()
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
        eventsLogger.logViewEvent(Events.MENU_TRANSMISSION_CENTER)
    }
}
