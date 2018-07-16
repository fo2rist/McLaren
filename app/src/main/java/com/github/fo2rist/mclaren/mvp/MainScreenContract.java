package com.github.fo2rist.mclaren.mvp;

import android.support.annotation.NonNull;

public interface MainScreenContract {

    interface View extends BaseView {
        void openStories();
        void openCircuits();
        void openDrivers();
        void openNewsFeed();
        void openAboutScreen();
        void openTransmissionCenter();

        void navigateTo(String externalUrl);

        void showTransmissionButton();
    }

    interface Presenter extends BasePresenter<View> {
        /** Should be called when activity re-created with state restored. */
        void onRestart(@NonNull View view);
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
