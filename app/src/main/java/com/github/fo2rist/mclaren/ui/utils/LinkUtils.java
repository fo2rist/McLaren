package com.github.fo2rist.mclaren.ui.utils;

public class LinkUtils {

    public static final String HTTP = "http://";
    private static final String HTTPS = "https://";
    public static final String WWW_MCLAREN_COM = "www.mclaren.com";
    public static final String TWITTER_COM = "twitter.com";

    private static final String BASE_MCLAREN_F1_PATH = HTTP + WWW_MCLAREN_COM + "/formula1/";
    private static final String BASE_TWITTER_PATH = HTTPS + TWITTER_COM + "/";

    private LinkUtils() {
    }

    public static String getMclarenCarLink() {
        return BASE_MCLAREN_F1_PATH + "car/";
    }

    public static String getTwitterPageLink(String id) {
        String idPath = id.startsWith("@") ? id.substring(1) : id;
        return BASE_TWITTER_PATH + idPath;
    }
}
