package com.github.fo2rist.mclaren.ui;

import com.github.fo2rist.mclaren.analytics.Events;
import com.github.fo2rist.mclaren.analytics.EventsLogger;
import com.github.fo2rist.mclaren.mvp.MainScreenContract;
import com.github.fo2rist.mclaren.ui.calendar.CalendarEventsLoader;
import com.github.fo2rist.mclaren.ui.calendar.RaceCalendar;
import javax.inject.Inject;

import static com.github.fo2rist.mclaren.utils.LinkUtils.getMcLarenCarLink;
import static com.github.fo2rist.mclaren.utils.LinkUtils.getMcLarenFormula1Link;

public class MainPresenter implements MainScreenContract.Presenter {
    private MainScreenContract.View view;
    private EventsLogger eventsLogger;
    private CalendarEventsLoader calendarEventsLoader;

    @Inject
    MainPresenter(EventsLogger eventsLogger, CalendarEventsLoader calendarEventsLoader) {
        this.eventsLogger = eventsLogger;
        this.calendarEventsLoader = calendarEventsLoader;
    }

    @Override
    public void onStart(MainScreenContract.View view) {
        this.view = view;
        view.openStories();
        if (isRaceActive()) {
            view.showTransmissionButton();
        }
    }

    private boolean isRaceActive() {
        RaceCalendar raceCalendar = calendarEventsLoader.loadCurrentCalendar();
        return raceCalendar.getActiveEvent() != null;
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onStoriesClicked() {
        view.openStories();
        eventsLogger.logViewEvent(Events.MENU_STORIES);
    }

    @Override
    public void onCircuitsClicked() {
        view.openCircuits();
        eventsLogger.logViewEvent(Events.MENU_CIRCUITS);
    }

    @Override
    public void onDriversClicked() {
        view.openDrivers();
        eventsLogger.logViewEvent(Events.MENU_DRIVERS);
    }

    @Override
    public void onNewsFeedClicked() {
        view.openNewsFeed();
        eventsLogger.logViewEvent(Events.MENU_FEED);
    }

    @Override
    public void onCarClicked() {
        view.navigateTo(
                getMcLarenCarLink());

        eventsLogger.logViewEvent(Events.MENU_CAR);
    }

    @Override
    public void onOfficialSiteClicked() {
        view.navigateTo(
                getMcLarenFormula1Link());
        eventsLogger.logViewEvent(Events.MENU_SITE);
    }

    @Override
    public void onAboutClicked() {
        view.openAboutScreen();
        eventsLogger.logViewEvent(Events.MENU_ABOUT);
    }

    @Override
    public void onTransmissionCenterClicked() {
        view.openTransmissionCenter();
        eventsLogger.logViewEvent(Events.MENU_TRANSMISSION_CENTER);
    }
}