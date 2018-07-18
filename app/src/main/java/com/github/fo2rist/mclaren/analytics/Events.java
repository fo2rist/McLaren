package com.github.fo2rist.mclaren.analytics;

public enum Events {

    MENU_STORIES("Stories"),
    MENU_CIRCUITS("Circuits"),
    MENU_DRIVERS("Drivers"),
    MENU_FEED("Feed"),
    MENU_CAR("Car"),
    MENU_SITE("Official site"),
    MENU_ABOUT("About"),
    MENU_TRANSMISSION_CENTER("Transmission Center"),

    VIEW_IMAGES("View Images"),
    VIEW_ARTICLE("View Article"),
    VIEW_VIDEO("View Video"),
    VIEW_EXTERNAL("View External Resource"),

    GALLERY_NEXT("Gallery Next"),
    GALLERY_PREV("Gallery Previous"),
    ;

    public String name;

    Events(String eventName) {
        this.name = eventName;
    }
}
