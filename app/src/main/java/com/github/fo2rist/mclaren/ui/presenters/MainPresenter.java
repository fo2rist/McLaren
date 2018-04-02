package com.github.fo2rist.mclaren.ui.presenters;

import com.github.fo2rist.mclaren.analytics.EventsLogger;
import com.github.fo2rist.mclaren.mvp.MainScreenContract;
import javax.inject.Inject;

import static com.github.fo2rist.mclaren.utils.LinkUtils.getMcLarenCarLink;
import static com.github.fo2rist.mclaren.utils.LinkUtils.getMcLarenFormula1Link;

public class MainPresenter implements MainScreenContract.Presenter {
    private MainScreenContract.View view;
    private EventsLogger eventsLogger;

    @Inject
    MainPresenter(EventsLogger eventsLogger) {
        this.eventsLogger = eventsLogger;
    }

    @Override
    public void onStart(MainScreenContract.View view) {
        this.view = view;
        view.openStories();
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onStoriesClicked() {
        view.openStories();
    }

    @Override
    public void onCircuitsClicked() {
        view.openCircuits();
    }

    @Override
    public void onDriversClicked() {
        view.openDrivers();
    }

    @Override
    public void onNewsFeedClicked() {
        view.openNewsFeed();
    }

    @Override
    public void onCarClicked() {
        view.navigateTo(
                getMcLarenCarLink());
    }

    @Override
    public void onOfficialSiteClicked() {
        view.navigateTo(
                getMcLarenFormula1Link());
    }

    @Override
    public void onAboutClicked() {
        view.openAboutScreen();
    }

    @Override
    public void onTransmissionCenterClicked() {
        view.openTransmissionCenter();
    }
}
