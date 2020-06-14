package com.github.fo2rist.mclaren.analytics

interface Events {
    enum class Screen(var screenName: String) {
        STORIES("Stories"),
        TEAM_TWITTER("Team Twitter"),
        SEASON_CALENDAR("Season Calendar"),
        DRIVERS("Drivers"),
        TRANSMISSION_CENTER("Transmission Center"),
        CIRCUIT("Circuit"),
        PREVIEW_IMAGES("View Images"),
        PREVIEW_ARTICLE("View Article"),
        PREVIEW_URL("View URL"),
        CAR_WEB_PAGE("Car web page"),
        TEAM_WEB_PAGE("Official web page"),
        ;
    }

    enum class Action(var actionName: String) {
        GALLERY_NEXT("Gallery Next"),
        GALLERY_PREV("Gallery Previous"),
        ;
    }
}
