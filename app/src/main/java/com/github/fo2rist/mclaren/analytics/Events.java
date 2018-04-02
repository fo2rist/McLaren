package com.github.fo2rist.mclaren.analytics;

public enum Events {

    MENU_STORIES("Stories"),
    MENU_CIRCUITS("Circuits"),
    MENU_DRIVERS("Drivers"),
    MENU_FEED("Feed"),
    MENU_CAR("Car"),
    MENU_SITE("Official site"),

    VIEW_IMAGE("View Image"),
    VIEW_GALLERY("View Gallery"),
    VIEW_ARTICLE("View Article"),
    VIEW_EXTERNAL("View External Resource"),

    GALLERY_NEXT("Gallery Next"),
    GALLERY_PREV("Gallery Previous"),
    ;

    public String name;

    Events(String eventName) {
        this.name = eventName;
    }
}
