package com.github.fo2rist.mclaren.mvp;

public interface MainScreenContract {

    interface View extends BaseView {
        void openStories();
        void openCircuits();
        void openDrivers();
        void openNewsFeed();
        void openAboutScreen();

        void openTransmissionCenter();

        void navigateTo(String externalUrl);
    }

    interface Presenter extends BasePresenter<View> {
        void onStoriesClicked();
        void onCircuitsClicked();
        void onDriversClicked();
        void onNewsFeedClicked();
        void onCarClicked();
        void onOfficialSiteClicked();
        void onAboutClicked();
        void onTransmissionCenterClicked();
    }
}
