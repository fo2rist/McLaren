package com.github.fo2rist.mclaren.analytics;

public interface Events {

    enum Screen {
        MENU_STORIES("Stories"),
        MENU_TEAM_TWITTER("Team Twitter"),
        MENU_CALENDAR("Calendar"),
        MENU_DRIVERS("Drivers"),
        TRANSMISSION_CENTER("Transmission Center"),
        DETAILS_CIRCUIT("Circuit"),
        PREVIEW_IMAGES("View Images"),
        PREVIEW_ARTICLE("View Article"),
        PREVIEW_URL("View URL"),
        MENU_CAR("Car"),
        MENU_SITE("Official site"),
        ;
        public String name;

        Screen(String screenName) {
            this.name = screenName;
        }
    }

    enum Action {
        GALLERY_NEXT("Gallery Next"),
        GALLERY_PREV("Gallery Previous"),
        ;

        public String name;

        Action(String actionName) {
            this.name = actionName;
        }
    }
}
